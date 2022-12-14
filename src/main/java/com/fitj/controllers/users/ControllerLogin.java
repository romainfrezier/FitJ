package com.fitj.controllers.users;

import com.fitj.Constante;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class ControllerLogin extends ControllerUser {
    @FXML
    private Button loginHeaderButton;
    @FXML
    private Button registerHeaderButton;

    @FXML
    private TextField username;
    @FXML
    private TextField password;

    @FXML
    private Text errorMessage;

    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button visitorButton;

    @FXML
    private void handleButtonConnect(ActionEvent event) {
        if (checkForm()) {
            try {
                String result = super.userFacade.connexion(username.getText(), password.getText());
                if (result.equals(Constante.CONNECTED)) {
                    hideError();
                    goToHome();
                } else {
                    displayError(result);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            displayError("Le formulaire n'est pas complet");
        }

    }

    @FXML
    private void goToRegister(ActionEvent actionEvent) throws IOException {
        super.goToRegister(registerButton);
    }

    @FXML
    private void goToHome() throws IOException {
        super.goToHome(loginButton);
    }

    @FXML
    private void goToVisitor(ActionEvent actionEvent) throws IOException {
        super.goToVisitor(visitorButton);
    }

    private boolean checkForm() {
        return !username.getText().isEmpty() && !password.getText().isEmpty();
    }

    private void displayError(String message) {
        errorMessage.setText(message);
    }

    private void hideError() {
        errorMessage.setText("");
    }
}