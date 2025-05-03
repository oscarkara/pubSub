package com.oscarkara.pubSub.security;

public class AuthResponse {
    private String token;
    private String token_type;
    private String token_expiration;

    public AuthResponse(String token, String token_type, String token_expiration) {
        this.token = token;
        this.token_type = token_type;
        this.token_expiration = token_expiration;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getToken_expiration() {
        return token_expiration;
    }

    public void setToken_expiration(String token_expiration) {
        this.token_expiration = token_expiration;
    }
}
