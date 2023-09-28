package com.example.jvshealth.request;
public class LoginRequest {
    private String emailAddress;
    private String password;

    // Constructors, getters, and setters

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
