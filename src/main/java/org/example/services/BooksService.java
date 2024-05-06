package org.example.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.BookSearchResponseDTO;
import org.example.dto.ItemDTO;
import org.example.model.Author;
import org.example.model.Book;
//import org.example.model.BookAuthor;
import org.example.model.Category;
import org.example.proxy.BooksProxy;
import org.example.repositories.AuthorRepository;
//import org.example.repositories.BookAuthorRepository;
import org.example.repositories.BookRepository;
import org.example.repositories.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    public BooksService(String googleBooksApiKey, BooksProxy booksProxy,
                        ObjectMapper objectMapper, BookRepository bookRepository,
                        AuthorRepository authorRepository, CategoryRepository categoryRepository
                        ){
        this.googleBooksApiKey = googleBooksApiKey;
        this.booksProxy = booksProxy;
        this.objectMapper = objectMapper;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public String searchBooks(String query)  {
        String modifiedQuery = query.replace(" ", "+");
        String jsonResponse = booksProxy.searchBooks(modifiedQuery,"paid-ebooks", "ebooks", "full", googleBooksApiKey);

        try{
            //parse into object
            BookSearchResponseDTO response = objectMapper.readValue(jsonResponse, BookSearchResponseDTO.class);
            //System.out.println(response.toString());
            for(ItemDTO item : response.getItems()){
                if(item.getSaleInfo().getListPrice() == null &&
                    item.getSaleInfo().getRetailPrice() == null){
                    continue;
                }
                Book newBook = convertToEntity(item);
                //System.out.println(newBook);
                saveBookWithAuthorsCategories(
                        newBook, item.getVolumeInfo().getAuthors(), item.getVolumeInfo().getCategories());
                //bookRepository.save(newBook);
            }


        } catch(IOException e){
            e.printStackTrace();
        }

        return jsonResponse;
    }

    private void saveBookWithAuthorsCategories(Book book, List<String> authors, List<String> categories) {

        if(bookRepository.findById(book.getId()).isEmpty()) {
            // Save authors or retrieve existing ones
            for (String author : authors) {
                Author existingAuthor = authorRepository.findByName(author);
                if (existingAuthor != null) {
                    book.addAuthor(existingAuthor);
                } else {
                    Author savedAuthor = new Author(author);
                    authorRepository.save(savedAuthor);
                    book.addAuthor(savedAuthor);
                }
            }
            //add categories if exists

            for(String category : categories) {
                Category existingCategory = categoryRepository.findByName(category);
                if(existingCategory != null ){
                    book.addCategory(existingCategory);
                } else {
                    Category savedCategory = new Category(category);
                    categoryRepository.save(savedCategory);
                    book.addCategory(savedCategory);
                }
            }

            // Save the book
            Book savedBook = bookRepository.save(book);
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
