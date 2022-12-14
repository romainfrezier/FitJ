package com.fitj.controllers.users;

import com.fitj.Constante;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;

public class ControllerRegister extends ControllerUser {
    @FXML
    private Button loginHeaderButton;
    @FXML
    private Button registerHeaderButton;

    @FXML
    private TextField mail;
    @FXML
    private TextField pseudo;

    @FXML
    private Slider poidsSlider;
    @FXML
    private Slider tailleSlider;

    @FXML
    private ToggleGroup sex;
    @FXML
    private RadioButton hommeRadio;
    @FXML
    private RadioButton femmeRadio;
    @FXML
    private RadioButton autreRadio;

    @FXML
    private TextField password;
    @FXML
    private TextField passwordConfirm;

    @FXML
    private Text errorMessage;

    @FXML
    private Button registerButton;
    @FXML
    private Button loginButton;
    @FXML
    private Button visitorButton;

    @FXML
    private void handleButtonRegister(ActionEvent event) throws Exception {
        if (checkPassword() && checkForm()) {
            // TODO ajouter le sex et les valeur venant du form
            String result = super.userFacade.inscription(mail.getText(), pseudo.getText(), password.getText(), 80f, 180, "Femme");
            if (result.equals(Constante.REGISTERED)) {
                hideError();
                try {
                    goToHome();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                displayError(result);
            }
        }
        else {
            displayError("Le formulaire n'est pas complet");
        }
    }

    @FXML
    private void goToHome() throws IOException {
        super.goToHome(registerButton);
    }

    @FXML
    private void goToLogin(ActionEvent actionEvent) throws IOException {
        super.goToLogin(loginButton);
    }

    @FXML
    private void goToVisitor(ActionEvent actionEvent) throws IOException {
        super.goToVisitor(visitorButton);
    }

    private void displayError(String message){
        errorMessage.setText(message);
    }

    //Cache le message d'erreur
    private void hideError(){
        errorMessage.setText("");
    }

    private  boolean checkPassword() {
        if (!(password.getText().equals(""))) {
            return password.getText().equals(passwordConfirm.getText());
        }
        return false;
    }

    private boolean checkForm() {
        return !mail.getText().equals("") && !pseudo.getText().equals("") && !password.getText().equals("") && !passwordConfirm.getText().equals("");
    }
}
