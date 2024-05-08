package org.example.model;

public class BorrowRequest {
    private String username;
    private String id;


    public String getUsername() {
        return username;
    }

    public BorrowRequest(String username, String id) {
        this.username = username;
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
