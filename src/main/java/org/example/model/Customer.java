package org.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.HashSet;
import java.util.Set;

public class Customer {
    @Id
    private long id;

    private String name;

    @MappedCollection(idColumn = "customer_id", keyColumn = "book_id")
    private Set<Book> library;

    public Customer(String name, Set<Book> library, long id) {
        this.library = library;
        this.id = id;
        this.name = name;
    }

    public Customer (){

    }

    public Customer(String name, Set<Book> library){
        this.library = library;
    }
    public Customer(String name) {
        this.name = name;
        this.library = new HashSet<Book>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Book> getLibrary() {
        return library;
    }

    public void setLibrary(Set<Book> library) {
        this.library = library;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
