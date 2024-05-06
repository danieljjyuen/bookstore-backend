package org.example.model;

import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

public class Customer {
    @Id
    private long id;

    private String username;
    private String name;
    private Set<BookRef> library = new HashSet<>();

    public Customer(String username, String name,  long id) {
        this.id = id;
        this.username = username;
        this.name = name;
    }

    public Customer(String username, String name) {
        this.username = username;
        this.name = name;
    }

    public Customer (){

    }

    public void addBook(Book book) {
        this.library.add(new BookRef(book.getPk()));
    }

    public Customer(String username){
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<BookRef> getLibrary() {
        return library;
    }

    public void setLibrary(Set<BookRef> library) {
        this.library = library;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

}
