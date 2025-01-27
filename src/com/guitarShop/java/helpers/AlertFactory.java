package com.guitarShop.java.helpers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class AlertFactory {
    public AlertFactory() {}

    public static void makeAlertDialog(StackPane stackPane, String heading, String alertText, String buttonText) {
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton closeButton = new JFXButton(buttonText);
        dialogLayout.setHeading(new Text(heading));
        dialogLayout.setBody(new Text(alertText));
        dialogLayout.setActions(closeButton);
        JFXDialog alertDialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                alertDialog.close();
            }
        });
        alertDialog.show();
    }

    public static void makeItemNotChoosenDialog(StackPane stackPane) {
        makeAlertDialog(stackPane, "Error", "Choose an item.", "Close");
    }

    public static void makeRefreshTableError(StackPane stackPane) {
        makeAlertDialog(stackPane, "Error", "Error refreshing tables, possibly lost connection or server down.", "Close");
    }

    public static void makeNullTableError(StackPane stackPane) {
        makeAlertDialog(stackPane, "Error", "No data to download.", "Close");
    }

    public static void makeDatabaseConnectionError(StackPane stackPane) {
        makeAlertDialog(stackPane, "Error", "Connection error or incorrect data.", "Close");
    }

    public static void makeNotEnoughItemsInStock(StackPane stackPane) {
        makeAlertDialog(stackPane, "Error", "Not enough items in stock, choose less.", "Close");
    }

    public static void makeFillAllFieldsError(StackPane stackPane) {
        makeAlertDialog(stackPane, "Error", "You need to fill every field in the dialog.", "Close");
    }

    public static void makeNotNumberError(StackPane stackPane) {
        makeAlertDialog(stackPane, "Error", "Provide a number.", "Close");
    }

    private static void regexChecker(TextField textField, String regex) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(regex)) {
                textField.setText(newValue.replaceAll("[^" + regex + "]", ""));
            }
        });
    }

    public static void restrictToNumbers(TextField textField) {
        regexChecker(textField, "\\d*");
    }

    public static void preventInjection(TextField textField) {
        regexChecker(textField, "[a-zA-Z0-9 ]*");
    }

    public static void preventSpecial(TextField textField) {
        regexChecker(textField, "[^'^\"^[-]]");
    }

    public static void preventAp(TextField textField) {
        regexChecker(textField, "[0-9-]");
    }
}
