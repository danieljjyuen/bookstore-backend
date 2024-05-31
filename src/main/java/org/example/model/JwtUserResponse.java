package org.example.model;

public class JwtUserResponse {
    private final String token;
    private final String name;

    public JwtUserResponse(String token, String name) {
        this.token = token;
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }
}
