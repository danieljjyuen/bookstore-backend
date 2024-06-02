package org.example.controllers;

import org.example.model.Book;
import org.example.services.BookDBService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Set<Book>> findByTitleContaining(@RequestParam("title") String title) {
        Set<Book> books = bookDBService.findByTitleContaining(title);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search/author")
    public ResponseEntity<Set<Book>> findByAuthorsName(@RequestParam("author") String author) {
        Set<Book> books = bookDBService.findByAuthorsName(author);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search/title-author")
    public ResponseEntity<Set<Book>> findByTitleContainingAndAuthorsName(
            @RequestParam("title") String title, @RequestParam("author") String author) {
        Set<Book> books = bookDBService.findByTitleContainingAndAuthorsName(title, author);
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
    public ResponseEntity<Set<Book>> findByCustomerId(@RequestParam("id") long id) {
        Set<Book> books = bookDBService.findByCustomerId(id);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search/mylibrary")
    public ResponseEntity<Set<Book>> findbyCustomer() {
        Set<Book> books = bookDBService.findByCustomerUsername();
        return ResponseEntity.ok(books);
    }
}


