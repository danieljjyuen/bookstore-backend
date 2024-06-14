package org.example.controllers;

import org.example.model.Book;
import org.example.model.BookOutput;
import org.example.services.BookDBService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/books")
public class BookDBController {
    private final BookDBService bookDBService;

    public BookDBController(BookDBService bookDBService) {
        this.bookDBService = bookDBService;
    }

    @GetMapping("/search/title")
    public ResponseEntity<Set<BookOutput>> findByTitleContaining(@RequestParam("title") String title) {
        Set<BookOutput> books = bookDBService.findByTitleContaining(title);
        System.out.println(books);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search/author")
    public ResponseEntity<Set<BookOutput>> findByAuthorsName(@RequestParam("author") String author) {
        Set<BookOutput> books = bookDBService.findByAuthorsName(author);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search/title-author")
    public ResponseEntity<Set<BookOutput>> findByTitleContainingAndAuthorsName(
            @RequestParam("title") String title, @RequestParam("author") String author) {
        Set<BookOutput> books = bookDBService.findByTitleContainingAndAuthorsName(title, author);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search/bookid")
    public ResponseEntity<Book> findById(@RequestParam("id") String id) {
        Optional<Book> book = bookDBService.findById(id);
        if(book.isPresent()){
            Book bookPresent = book.get();
            return ResponseEntity.ok(bookPresent);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/bookpk")
    public ResponseEntity<Book> findByPk(@RequestParam("pk") long pk) {
        Optional<Book> book = bookDBService.findByPk(pk);
        if(book.isPresent()){
            Book bookPresent = book.get();
            return ResponseEntity.ok(bookPresent);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/customerid")
    public ResponseEntity<Set<BookOutput>> findByCustomerId(@RequestParam("id") long id) {
        Set<BookOutput> books = bookDBService.findByCustomerId(id);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search/mylibrary")
    public ResponseEntity<Set<BookOutput>> findbyCustomer() {
        Set<BookOutput> books = bookDBService.findByCustomerUsername();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/unifiedsearch")
    public ResponseEntity<Map<String, Object>> searchBooks(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Map<String, Object> response;

        if (title != null && author == null) {
            response = bookDBService.findBooksByTitleContainingWithPagination(title, page, size);
        } else if (title == null && author != null) {
            response = bookDBService.findBooksByAuthorNameWithPagination(author, page, size);
        } else if (title != null && author != null) {
            response = bookDBService.findBooksByTitleAndAuthorWithPagination(title, author, page, size);
        } else {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(response);
    }

}


