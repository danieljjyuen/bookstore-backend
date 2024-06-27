package org.example.services;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import org.example.services.SqsService;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.BookSearchResponseDTO;
import org.example.dto.ItemDTO;
import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Category;
import org.example.proxy.BooksProxy;
import org.example.repositories.AuthorRepository;
import org.example.repositories.BookRepository;
import org.example.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class BooksService {

    private final String googleBooksApiKey;
    private final BooksProxy booksProxy;
    private final ObjectMapper objectMapper;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final AmazonSQS amazonSQS;
    private final SqsService sqsService;

    public BooksService(AmazonSQS customAmazonSQS, SqsService sqsService, String googleBooksApiKey, BooksProxy booksProxy,
                        ObjectMapper objectMapper, BookRepository bookRepository,
                        AuthorRepository authorRepository, CategoryRepository categoryRepository
                        ){
        this.amazonSQS = customAmazonSQS;
        this.sqsService = sqsService;
        this.googleBooksApiKey = googleBooksApiKey;
        this.booksProxy = booksProxy;
        this.objectMapper = objectMapper;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public CompletableFuture<String> searchBooks(String query) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("Search books -----| 1");
                String queueUrl = sqsService.getQueueUrl();
                SendMessageRequest sendMessageRequest = new SendMessageRequest()
                        .withQueueUrl("http://localhost:4566/000000000000/google-books-queue")
                        .withMessageBody(query);
                System.out.println("Queue URL: " + queueUrl + " SendMessageRequest: " + sendMessageRequest);
                amazonSQS.sendMessage(sendMessageRequest);
                System.out.println("Search books -----| 2");
                return "Search request queued";
            } catch (AmazonSQSException e) {
                e.printStackTrace();
                return "Failed to queue search request";
            }
        });
    }


    @Transactional
    public String processSearch(String query) {
        System.out.println("process search -----|");
        String modifiedQuery = query.replace(" ", "+");
        int start = 0;
        String jsonResponse = booksProxy.searchBooks(modifiedQuery,"paid-ebooks", "ebooks", "full", String.valueOf(start), "40", googleBooksApiKey);

        try{
            BookSearchResponseDTO response;
            do {
                //parse into object
                response = objectMapper.readValue(jsonResponse, BookSearchResponseDTO.class);
                //System.out.println("TOTAL ITEMS------" + response.getTotalItems());


                //System.out.println(response.toString());
                for (ItemDTO item : response.getItems()) {
                    if (item.getSaleInfo().getListPrice() == null ||
                            item.getSaleInfo().getRetailPrice() == null ||
                            item.getVolumeInfo().getAuthors() == null) {
                        continue;
                    }
                    Book newBook = convertToEntity(item);
                    //System.out.println(newBook);
                    saveBookWithAuthorsCategories(
                            newBook, item.getVolumeInfo().getAuthors(), item.getVolumeInfo().getCategories());
                    //System.out.println(count);

                }
                start+=40;
                System.out.println("------------"+ start);
            } while(start < response.getTotalItems());

        } catch(IOException e){
            e.printStackTrace();
            return "Error Occured";
        }
        System.out.println("SEARCH COMPLETE -----|");
        //return jsonResponse;
        return "Search Completed";
    }

    private void saveBookWithAuthorsCategories(Book book, List<String> authors, List<String> categories) {
        //System.out.println(book);
        if(bookRepository.findById(book.getId()).isEmpty()) {
            // Save authors or retrieve existing ones

            for (String author : authors) {
                Optional<Author> existingAuthorOptional = authorRepository.findByName(author);

                if (existingAuthorOptional.isPresent()) {
                    Author existingAuthor = existingAuthorOptional.get();
                    book.addAuthor(existingAuthor);
                } else {
                    Author savedAuthor = new Author(author);
                    authorRepository.save(savedAuthor);
                    book.addAuthor(savedAuthor);
                }
            }
            //add categories if exists

            for(String category : categories) {
                Optional<Category> existingCategoryOptional = categoryRepository.findByName(category);
                if(existingCategoryOptional.isPresent() ){
                    Category existingCategory = existingCategoryOptional.get();
                    book.addCategory(existingCategory);
                } else {
                    Category savedCategory = new Category(category);
                    categoryRepository.save(savedCategory);
                    book.addCategory(savedCategory);
                }
            }
            // Save the book
            Book savedBook = bookRepository.save(book);
            System.out.println(savedBook.getTitle());
        }
    }

    private Book convertToEntity(ItemDTO itemDTO) {
        BigDecimal listPrice = itemDTO.getSaleInfo().getListPrice().getAmount() != null ?
                itemDTO.getSaleInfo().getListPrice().getAmount() :
                new BigDecimal(0.01);

        BigDecimal retailPrice = itemDTO.getSaleInfo().getRetailPrice().getAmount() != null ?
                itemDTO.getSaleInfo().getRetailPrice().getAmount() :
                new BigDecimal(0.01);

        Book book = new Book(
                itemDTO.getKind(),
                itemDTO.getId(),
                itemDTO.getVolumeInfo().getTitle(),
                itemDTO.getVolumeInfo().getSubtitle(),
                itemDTO.getVolumeInfo().getPublisher(),
                itemDTO.getVolumeInfo().getPublishedDate(),
                itemDTO.getVolumeInfo().getDescription(),
                itemDTO.getVolumeInfo().getPageCount(),
                itemDTO.getVolumeInfo().getImageLinks().getSmallThumbnail(),
                itemDTO.getVolumeInfo().getImageLinks().getThumbnail(),
                listPrice,
                itemDTO.getSaleInfo().getListPrice().getCurrencyCode(),
                retailPrice,
                itemDTO.getSaleInfo().getRetailPrice().getCurrencyCode(),
                itemDTO.getSaleInfo().getBuyLink(),
                itemDTO.getVolumeInfo().getAverageRating(),
                itemDTO.getVolumeInfo().getRatingsCount(),
                itemDTO.getVolumeInfo().getLanguage()
        );
        return book;
    }

    private Set<Author> convertAuthor(List<String> authors){
        Set<Author> list = new HashSet<Author>();
        for(String author : authors) {
            Author newAuthor = new Author(author);
            list.add(newAuthor);
        }
        return list;
    }
}
