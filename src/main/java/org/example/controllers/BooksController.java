package org.example.controllers;

import org.example.services.BooksService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

//controller to fetch from google books api
@RestController
public class BooksController {

    private final BooksService booksService;

    public BooksController (BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping("/books")
    public CompletableFuture<String> searchBooks(@RequestParam("q") String query) throws ExecutionException, InterruptedException {
        System.out.println("books controller -----|");
        CompletableFuture<String> responseFuture = booksService.searchBooks(query);

        return responseFuture;
    }

}
