package com.gidi.library.response;

public class AuthenticationResponse {
    private String status;
    private final String jwt;

    public AuthenticationResponse(String jwt, String status) {
        this.jwt = jwt;
        this.status = status;
    }

    public String getJwt() {
        return jwt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
