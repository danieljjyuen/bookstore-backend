package org.example.controllers;

import org.example.model.*;
import org.example.services.CustomerService;
import org.example.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
            JwtUserResponse jwtUserResponse = customerService.login(loginRequest);
            System.out.println(jwtUserResponse);
            return ResponseEntity.ok(jwtUserResponse);

        }catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username not found");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during login");
        }
    }

    @PostMapping("/addlibrary")
    public ResponseEntity<String> borrowBook(@RequestParam("bookid") String bookId) {
           try {
            customerService.borrowBook(bookId);
            return ResponseEntity.ok("added to library");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getlibrary")
    public ResponseEntity<Set<BookOutput>> getAllBooksInLibrary() {
        Set<BookOutput> library = customerService.getAllBooksInLibrary();
        return ResponseEntity.ok(library);
    }

}
