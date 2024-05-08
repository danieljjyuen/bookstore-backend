package org.example.controllers;

import org.example.model.Book;
import org.example.model.BorrowRequest;
import org.example.model.LoginRequest;
import org.example.model.Customer;
import org.example.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/api/customers")
@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer newCustomer =  customerService.createCustomer(customer);
        return ResponseEntity.ok(newCustomer);
    }

    @PostMapping("/login")
    public ResponseEntity<Customer> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        Customer loggedCustomer = customerService.login(username, password);

        if(loggedCustomer != null) {
            return ResponseEntity.ok(loggedCustomer);
        }else {
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/addlibrary")
    public ResponseEntity<String> borrowBook(@RequestBody BorrowRequest borrowRequest) {
        String username = borrowRequest.getUsername();
        String bookId = borrowRequest.getId();

        try {
            customerService.borrowBook(username, bookId);
            return ResponseEntity.ok("added to library");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getlibrary")
    public ResponseEntity<Set<Book>> getAllBooksInLibrary(@RequestParam("id") Long customerId) {
        Set<Book> library = customerService.getAllBooksInLibrary(customerId);
        return ResponseEntity.ok(library);
    }

}
