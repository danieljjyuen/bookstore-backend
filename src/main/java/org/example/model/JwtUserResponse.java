package org.example.model;

public class JwtUserResponse {
    private final String token;
    private final String username;

    public JwtUserResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

//    public String getName() {
//        return name;
//    }
}
