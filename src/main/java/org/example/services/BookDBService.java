package org.example.services;

import org.example.repositories.BookRepository;

public class BookDBService {
    private final BookRepository bookRepository;

    BookDBService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}
