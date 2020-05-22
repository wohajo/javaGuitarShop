package com.guitarShop.java.controllers.tabControllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.text.Text;

import java.io.IOException;

public class TestDialogController {

    @FXML private JFXButton testButton;
    @FXML private Text testText;

    @FXML
    public void setTestButton(String text) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/guitarShop/resources/tabs/TestTab.fxml"));
        Parent root = loader.load();
        TestTabController testTabController = loader.<TestTabController>getController();
        testTabController.dialogTest(text);
        testText.setText(testTabController.getName());
    }
}
