package com.guitarShop.java.helpers;

import com.jfoenix.controls.JFXDialog;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Stack;

public class PasswordManager {

    ConnectionFactory connectionFactory = new ConnectionFactory();

    public PasswordManager() {
    }

    public String makeHash(String password) {

        String hashedPassword = "";
        String salt = "";
        byte[] byteString = null;
        StringBuilder sb = null;

        try (Scanner scanner = new Scanner(new File("src/com/guitarShop/resources/misc/salt.txt"))) {
            while (scanner.hasNext()) {
                salt = scanner.nextLine();
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byteString = salt.getBytes();
            md.update(byteString);
            byte[] bytes = md.digest(password.getBytes());
            sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        hashedPassword = sb.toString();

        return hashedPassword;
    }

    public boolean checkCredentials(String login, String password, StackPane stackPane) throws IOException {

        Boolean isCorrect = false;

        try(Connection connection = connectionFactory.getConnection()) {
            String email = "";
            String downloadedPassword = "";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Email, PasswordHash FROM Sellers WHERE Email = '" + login + "'");

            while (resultSet.next()) {
                email = resultSet.getString("Email");
                downloadedPassword = resultSet.getString("PasswordHash");
            }
            if (email.equals(""))
                AlertFactory.makeAlertDialog(stackPane, "Error", "Wrong email.", "Close");
            else if(!makeHash(password).equals(downloadedPassword)) {
                AlertFactory.makeAlertDialog(stackPane, "Error", "Wrong password.", "Close");
            } else
                isCorrect = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isCorrect;
    }

    public void changePassword(JFXDialog viewDialog, StackPane stackPane, String pesel, String email, String password1, String password2) {
        try(Connection connection = connectionFactory.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Pesel FROM Sellers WHERE Email = '" + email + "'");

            String downloadedPesel = null;

            while (resultSet.next()) {
                downloadedPesel = resultSet.getString("Pesel");
            }
            if (pesel.equals(downloadedPesel)) {
              if(password1.equals(password2)) {
                  String newPassword = makeHash(password1);
                  try {
                      statement.executeUpdate("UPDATE Sellers SET PasswordHash = '" + newPassword + "' WHERE Pesel = '" + pesel + "'");
                      viewDialog.close();
                  } catch (SQLException e) {
                      AlertFactory.makeRefreshTableError(stackPane);
                  }
              } else {
                  AlertFactory.makeAlertDialog(stackPane, "Error", "Passwords do not match.", "Close");
              }
            } else {
                AlertFactory.makeAlertDialog(stackPane, "Error", "Wrong pesel.", "Close");
            }
        } catch (SQLException e) {

        }
    }
}
