package org.example.model;


import org.springframework.data.relational.core.mapping.Table;

@Table("book_author")
public class AuthorRef {
    public long getAuthor() {
        return author;
    }

    public void setAuthor(long author) {
        this.author = author;
    }

    private long author;
    public AuthorRef(long author) {
        this.author = author;
    }
}
