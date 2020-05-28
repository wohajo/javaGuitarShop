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

    public static void makeRefreshTableError(StackPane partsStackPane) {
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton closeButton = new JFXButton("Close");
        dialogLayout.setHeading(new Text("Error"));
        dialogLayout.setBody(new Text("Error refreshing tables. Possibly lost connection or server down."));
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

    /*public static Boolean makeConfirmDeleteDialog(StackPane stackPane, String itemName) throws InterruptedException {
        Boolean value = false;
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton yesButton = new JFXButton("Yes");
        JFXButton noButton = new JFXButton("No");
        dialogLayout.setHeading(new Text("Warning"));
        dialogLayout.setBody(new Text("Are You sure You want to delete '" + itemName + "'?"));
        dialogLayout.setActions(yesButton);
        JFXDialog alertDialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
        yesButton.setOnAction(e -> {
            value = true;
            alertDialog.close();
                });
        noButton.setOnAction(e -> {
            alertDialog.close();
                });
        alertDialog.show();
        alertDialog.wait();
        return value;
    }*/
}
