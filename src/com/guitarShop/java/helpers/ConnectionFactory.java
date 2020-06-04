package com.guitarShop.java.helpers;

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

        DatabaseCredentials dbcred = null;
        try {
            Gson gson = new Gson();
            try (Scanner scanner = new Scanner(new File("src/com/guitarShop/resources/misc/dbcredidentials.json"))) {
                while (scanner.hasNext()) {
                    String scanned = scanner.nextLine();
                        dbcred = gson.fromJson(scanned, DatabaseCredentials.class);
                }
            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            }
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(dbcred.getUrl() + "=" + dbcred.getDatabase(), dbcred.getUsername(), dbcred.getPassword());
    }
}
