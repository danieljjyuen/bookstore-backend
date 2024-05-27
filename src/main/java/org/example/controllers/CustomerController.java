package org.example.controllers;

import org.example.model.*;
import org.example.services.CustomerService;
import org.example.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/api/customers")
@RestController
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer newCustomer =  customerService.createCustomer(customer);
        return ResponseEntity.ok(newCustomer);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try{
            JwtResponse jwtResponse = customerService.login(loginRequest);
            System.out.println(jwtResponse);
            return ResponseEntity.ok(jwtResponse);

        }catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username not found");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during login");
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
