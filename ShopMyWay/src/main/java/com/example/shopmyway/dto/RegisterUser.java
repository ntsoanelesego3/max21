package com.example.shopmyway.dto;

public class RegisterUser {
    private String username;
    private String password;

    public RegisterUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public RegisterUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
