package com.guitarShop.java.helpers;

public class DatabaseCredentials {

    String url;
    String database;
    String username;
    String password;

    public DatabaseCredentials(String url, String database, String username, String password) {
        this.url = url;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}
