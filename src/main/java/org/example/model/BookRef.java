package org.example.model;

import org.springframework.data.relational.core.mapping.Table;

@Table("customer_book")
public class BookRef {

    private long book;

    public BookRef(long book) {
        this.book = book;
    }

    public void setBook(long book) {
        this.book = book;
    }

    public long getBook(long book) {
        return book;
    }
}
