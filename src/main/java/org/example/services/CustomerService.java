package org.example.services;

import org.example.model.Customer;
import org.example.model.Book;
import org.example.repositories.BookRepository;
import org.example.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository ;
    private final BookRepository bookRepository;

    public CustomerService(CustomerRepository customerRepository,
                           BookRepository bookRepository) {
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
    }
    public Customer createCustomer(Customer customer) {
        //check if customer exist
        Optional<Customer> existingCustomerOptional = customerRepository.findByUsername(customer.getUsername());
        if(existingCustomerOptional.isPresent()) {
            throw new RuntimeException("Customer already exists");
        }
            Customer newCustomer = customerRepository.save(customer);
            return newCustomer;
        }

    public Customer login(String username, String password) {
        Optional<Customer> customerOptional = customerRepository.findByUsername(username);
        if(customerOptional.isEmpty()) {
            throw new RuntimeException("customer by username does not exist");
        }
        //check for password match in the future when it is implemented
        Customer customer = customerOptional.get();
        return customer;
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
