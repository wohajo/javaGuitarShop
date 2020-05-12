package com.guitarShop.java.controllers;

public class DatabaseCredidentials {

    String url;
    String username;
    String password;

    public DatabaseCredidentials(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
