package org.example.services;

import org.example.model.Book;
import org.example.model.BookOutput;
import org.example.model.Customer;
import org.example.repositories.BookRepository;
import org.example.repositories.CustomerRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class BookDBService {
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;

    BookDBService(BookRepository bookRepository, CustomerRepository customerRepository) {
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
    }

    public Set<BookOutput> findByTitleContaining(String keyword) {
        return bookRepository.findByTitleContaining(keyword);
    }

    public Set<BookOutput> findByAuthorsName(String name) {
        return bookRepository.findByAuthorsName(name);
    }

    public Set<BookOutput> findByTitleContainingAndAuthorsName(String title, String name) {
        return bookRepository.findByTitleContainingAndAuthorsName(title, name);
    }

    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }

    public Optional<Book> findByPk(long id) {
        return bookRepository.findByPk(id);
    }

    public Set<BookOutput> findByCustomerId(long id) {
        return bookRepository.findByCustomerId(id);
    }

    public Set<BookOutput> findByCustomerUsername() {
        try {
            //extract username from jwt token/security context
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<Customer> foundCustomerOptional = customerRepository.findByUsername(userDetails.getUsername());

            if(foundCustomerOptional.isPresent()) {
                return bookRepository.findByCustomerId(foundCustomerOptional.get().getId());
            }else {
                throw new RuntimeException("customer not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("authority does not exist");
        }
    }

}
