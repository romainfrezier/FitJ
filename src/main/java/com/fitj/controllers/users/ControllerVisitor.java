package com.fitj.controllers.users;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ControllerVisitor extends ControllerUser {
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;

    @FXML
    private void goToRegister(ActionEvent actionEvent) throws IOException {
        super.goToRegister(registerButton);
    }

    @FXML
    private void goToLogin(ActionEvent actionEvent) throws IOException {
        super.goToLogin(loginButton);
    }
}
