package org.example.services;

import org.example.model.Book;
import org.example.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class BookDBService {
    private final BookRepository bookRepository;

    BookDBService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Set<Book> findByTitleContaining(String keyword) {
        return bookRepository.findByTitleContaining(keyword);
    }

    public Set<Book> findByAuthorsName(String name) {
        return bookRepository.findByAuthorsName(name);
    }

    public Set<Book> findByTitleContainingAndAuthorsName(String title, String name) {
        return bookRepository.findByTitleContainingAndAuthorsName(title, name);
    }

    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }

    public Optional<Book> findByPk(long id) {
        return bookRepository.findByPk(id);
    }

    public Set<Book> findByCustomerId(long id) {
        return bookRepository.findByCustomerId(id);
    }

}
