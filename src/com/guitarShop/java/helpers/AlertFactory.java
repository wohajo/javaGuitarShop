package com.guitarShop.java.helpers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.concurrent.atomic.AtomicReference;

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

    public static void makeItemNotChoosenDialog(StackPane partsStackPane) {
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton closeButton = new JFXButton("Close");
        dialogLayout.setHeading(new Text("Error"));
        dialogLayout.setBody(new Text("Choose an item to delete."));
        dialogLayout.setActions(closeButton);
        JFXDialog alertDialog = new JFXDialog(partsStackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                alertDialog.close();
            }
        });
        alertDialog.show();
    }

    public static void makeRefreshTableError(StackPane stackPane) {
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton closeButton = new JFXButton("Close");
        dialogLayout.setHeading(new Text("Error"));
        dialogLayout.setBody(new Text("Error refreshing tables, possibly lost connection or server down."));
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

    public static void makeNullTableError(StackPane stackPane) {
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton closeButton = new JFXButton("Close");
        dialogLayout.setHeading(new Text("Error"));
        dialogLayout.setBody(new Text("No data to download."));
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

    public static void makeDatabaseConnectionError(StackPane stackPane) {
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton closeButton = new JFXButton("Close");
        dialogLayout.setHeading(new Text("Database error"));
        dialogLayout.setBody(new Text("Error downloading data, possibly lost connection or server down."));
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
}
