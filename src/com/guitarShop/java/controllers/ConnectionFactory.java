package com.guitarShop.java.controllers;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class ConnectionFactory {

    // use with try(connection) to auto close

    ConnectionFactory() {}

    public static Connection getConnection() throws SQLException {
        String connectionString = "";
        try {
            Gson gson = new Gson();
            try (Scanner scanner = new Scanner(new File("src/com/guitarShop/resources/dbcredidentials.json"))) {
                while (scanner.hasNext()) {
                    String scanned = scanner.nextLine();
                    DatabaseCredentials dbcred = gson.fromJson(scanned, DatabaseCredentials.class);
                    connectionString = dbcred.url + "=" + dbcred.database + ";user=" +dbcred.username + ";password=" + dbcred.password;
                }
            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            }
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(connectionString);
    }
}
