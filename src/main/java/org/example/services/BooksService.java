package org.example.services;

import org.example.config.VaultConfig;
import org.example.proxy.BooksProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BooksService {

    private final String googleBooksApiKey;
    private final BooksProxy booksProxy;

    public BooksService(String googleBooksApiKey, BooksProxy booksProxy){
        this.googleBooksApiKey = googleBooksApiKey;
        this.booksProxy = booksProxy;
    }

    public String searchBooks(String query) {
        String modifiedQuery = query.replace(" ", "+");
        return booksProxy.searchBooks(modifiedQuery, googleBooksApiKey);
    }



}
