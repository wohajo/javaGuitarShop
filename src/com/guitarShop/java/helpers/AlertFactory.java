package com.guitarShop.java.helpers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class AlertFactory {
    public AlertFactory() {}

    public static void makeAlertDialog(StackPane stackPane, String heading, String alertText, String buttonText) {
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton closeButton = new JFXButton(buttonText);
        dialogLayout.setHeading(new Text(heading));
        dialogLayout.setBody(new Text(alertText));
        JFXDialog alertDialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
        alertDialog.show(stackPane);
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                alertDialog.close();
            }
        });
        alertDialog.show();
    }
}
