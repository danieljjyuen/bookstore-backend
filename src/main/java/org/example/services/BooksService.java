package org.example.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.BookSearchResponseDTO;
import org.example.dto.ItemDTO;
import org.example.model.Author;
import org.example.model.Book;
//import org.example.model.BookAuthor;
import org.example.proxy.BooksProxy;
import org.example.repositories.AuthorRepository;
//import org.example.repositories.BookAuthorRepository;
import org.example.repositories.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BooksService {

    private final String googleBooksApiKey;
    private final BooksProxy booksProxy;
    private final ObjectMapper objectMapper;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    //private final BookAuthorRepository bookAuthorRepository;

    public BooksService(String googleBooksApiKey, BooksProxy booksProxy,
                        ObjectMapper objectMapper, BookRepository bookRepository,
                        AuthorRepository authorRepository
                        ){
        this.googleBooksApiKey = googleBooksApiKey;
        this.booksProxy = booksProxy;
        this.objectMapper = objectMapper;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        //this.bookAuthorRepository = bookAuthorRepository;
    }

    @Transactional
    public String searchBooks(String query)  {
        String modifiedQuery = query.replace(" ", "+");
        String jsonResponse = booksProxy.searchBooks(modifiedQuery,"paid-ebooks", googleBooksApiKey);

        try{
            //parse into object
            BookSearchResponseDTO response = objectMapper.readValue(jsonResponse, BookSearchResponseDTO.class);
            //System.out.println(response.toString());
            for(ItemDTO item : response.getItems()){
                Book newBook = convertToEntity(item);
                //System.out.println(newBook);
                saveBookWithAuthors(newBook, item.getVolumeInfo().getAuthors());
                //bookRepository.save(newBook);
            }


        } catch(IOException e){
            e.printStackTrace();
        }

        return jsonResponse;
    }

    private void saveBookWithAuthors(Book book, List<String> authors) {

        if(bookRepository.findById(book.getId()).isEmpty()) {
            // Save authors or retrieve existing ones
            //Set<Author> savedAuthors = new HashSet<>();
            //Set<Long> savedAuthorsId = new HashSet<>();
            for (String author : authors) {
                Author existingAuthor = authorRepository.findByName(author);
                if (existingAuthor != null) {
                    //savedAuthors.add(existingAuthor);
                    //savedAuthorsId.add(existingAuthor.getId());
                    book.addAuthor(existingAuthor);
                } else {
                    Author savedAuthor = new Author(author);
                    authorRepository.save(savedAuthor);
                    //savedAuthors.add(savedAuthor);
                    //savedAuthorsId.add(savedAuthor.getId());
                    book.addAuthor(savedAuthor);
                }
            }
            //book.setAuthors(savedAuthors);

            // Save the book
            Book savedBook = bookRepository.save(book);

            //for each author map it to book id for join table
//            for(Long authorId : savedAuthorsId) {
//                bookAuthorRepository.save(new BookAuthor(savedBook.getPk(), authorId));
//            }
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
