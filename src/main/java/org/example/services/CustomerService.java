package org.example.services;

import org.example.model.*;
import org.example.repositories.AuthorityRepository;
import org.example.repositories.BookRepository;
import org.example.repositories.CustomerRepository;
import org.example.util.JwtTokenUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository ;
    private final BookRepository bookRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    public CustomerService(CustomerRepository customerRepository,
                           BookRepository bookRepository,
                           AuthorityRepository authorityRepository,
                           PasswordEncoder passwordEncoder,
                           UserDetailsService userDetailsService,
                           JwtTokenUtil jwtTokenUtil) {
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
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

    public JwtUserResponse login(LoginRequest loginRequest) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

            if(passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())){
                //generate jwt token
                final String token = jwtTokenUtil.generateToken(userDetails);
                String name = "";
                Optional<Customer> customerOptional = customerRepository.findByUsername(loginRequest.getUsername());
                if (customerOptional.isPresent()) {
                    name = customerOptional.get().getName();
                } else {
                    throw new RuntimeException("customer not found");
                }
                System.out.println("token: " + token);
                return new JwtUserResponse(token, name);
            } else{
                throw new BadCredentialsException("Incorrect password");
            }
    }
    //will implement borrow book before "buying" books

    public void borrowBook(String bookId){
        //fetched the userdetails from the jwt token from auth
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Customer> foundCustomerOptional = customerRepository.findByUsername(userDetails.getUsername());

        System.out.println(foundCustomerOptional);
        System.out.println(foundCustomerOptional.isPresent());

        Optional<Book> foundBookOptional = bookRepository.findById(bookId);
        System.out.println(foundBookOptional);
        System.out.println(foundBookOptional.isPresent());

        //check for both customer/book exist
        if(foundCustomerOptional.isPresent() && foundBookOptional.isPresent()){
            Customer foundCustomer = foundCustomerOptional.get();
            Book foundBook = foundBookOptional.get();
            foundCustomer.addBook(foundBook);
            customerRepository.save(foundCustomer);
        }else{
            throw new RuntimeException("customer or book does not exist");
        }
    }

    public Set<BookOutput> getAllBooksInLibrary() {
        //fetched the userdetails from the jwt token from auth
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //extract username
        String username = userDetails.getUsername();
        Optional<Customer> customerOptional = customerRepository.findByUsername(username);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            Set<BookOutput> library = bookRepository.findByCustomerId(customer.getId());
            return library;
        } else {
            throw new RuntimeException("customer not found");
        }
    }


}
