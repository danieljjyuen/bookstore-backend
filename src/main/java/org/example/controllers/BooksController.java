package org.example.controllers;

import org.example.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BooksController {

    private final BooksService booksService;

    public BooksController (BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping("/books")
    public String searchBooks(@RequestParam("q") String query){
        return booksService.searchBooks(query);
    }
}
