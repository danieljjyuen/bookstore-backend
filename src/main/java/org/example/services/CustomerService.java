package org.example.services;

import org.example.model.Authority;
import org.example.model.Customer;
import org.example.model.Book;
import org.example.repositories.AuthorityRepository;
import org.example.repositories.BookRepository;
import org.example.repositories.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.sql.SQLOutput;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository ;
    private final BookRepository bookRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository,
                           BookRepository bookRepository,
                           AuthorityRepository authorityRepository,
                           PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public Customer createCustomer(Customer customer) {
        //check if customer exist
        Optional<Customer> existingCustomerOptional = customerRepository.findByUsername(customer.getUsername());
        if(existingCustomerOptional.isPresent()) {
            throw new RuntimeException("Customer already exists");
        }
            String encoded = passwordEncoder.encode(customer.getPassword());
            customer.setPassword(encoded);
            customer.setEnabled(1);
            Customer newCustomer = customerRepository.save(customer);


            Optional<Authority> authorityOptional = authorityRepository.findByName("USER");

            if(authorityOptional.isPresent()){
                Authority authority = authorityOptional.get();
                newCustomer.addAuthority(authority);
            }else {
                Authority userAuthority = new Authority("USER");
                authorityRepository.save(userAuthority);
                newCustomer.addAuthority(userAuthority);
            }

            Customer savedCustomer = customerRepository.save(newCustomer);
            return savedCustomer;
        }

    public Customer login(String username, String password) {
        Optional<Customer> customerOptional = customerRepository.findByUsername(username);
        if(customerOptional.isEmpty()) {
            throw new RuntimeException("customer by username does not exist");
        }
        //check if password match
        try{
            Customer customer = customerOptional.get();

            if(passwordEncoder.matches(password, customer.getPassword())){
                return customer;
            } else{
                throw new RuntimeException("Incorrect password");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error has occurred");
        }

    }
    //will implement borrow book before "buying" books

    public void borrowBook(String username, String bookId){
           //check for both customer/book exist
        Optional<Customer> foundCustomerOptional = customerRepository.findByUsername(username);
        Optional<Book> foundBookOptional = bookRepository.findById(bookId);
        if(foundBookOptional.isPresent() && foundBookOptional.isPresent()){
            Customer foundCustomer = foundCustomerOptional.get();
            Book foundBook = foundBookOptional.get();
            foundCustomer.addBook(foundBook);
            customerRepository.save(foundCustomer);
        }else{
            throw new RuntimeException("customer or book does not exist");
        }
    }

    public Set<Book> getAllBooksInLibrary(Long customerId) {
        Set<Book> library = bookRepository.findByCustomerId(customerId);
        return library;
    }
}
