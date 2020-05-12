package com.guitarShop.java.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    // use with try(connection) to auto close

    ConnectionFactory() {}

    public static Connection getConnection() throws SQLException {
        try {
            // json file reader
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return DriverManager.getConnection(url, user, password);
    }

}
