package org.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.Set;

public class Customer {
    @Id
    private long id;

    @MappedCollection(idColumn = "customer_id", keyColumn = "book_id")
    private Set<Book> library;

    public Customer(Set<Book> library, long id) {
        this.library = library;
        this.id = id;
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

}
