package com.fitj.controllers;

import com.fitj.ViewSetter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerHome {
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;

    @FXML
    public void goToRegister(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        Scene scene = ViewSetter.getScene("register-view.fxml");
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void goToLogin(ActionEvent actionEvent) throws IOException {
        //ouvrir la fenêtre login
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Scene scene = ViewSetter.getScene("login-view.fxml");
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
}
