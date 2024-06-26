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
    private String password;
    private Set<AuthorityRef> authorities = new HashSet<>();
    private int enabled;

    public Customer(String username, String name, String password, long id) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.enabled = 1;
    }

    public Customer(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.enabled = 1;
    }

    public Customer (){

    }

    public void addBook(Book book) {
        this.library.add(new BookRef(book.getPk()));
    }

    public Set<AuthorityRef> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthorityRef> authorities) {
        this.authorities = authorities;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public void addAuthority(Authority authority) {
        this.authorities.add(new AuthorityRef(authority.getId()));
    }

    public Customer(String username){
        this.username = username;
        this.enabled = 1;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

}
