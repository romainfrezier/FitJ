package com.fitj.controllers.users;

import com.fitj.Constante;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Controller pour la page de connexion
 * @see ControllerUser
 * @author Romain Frezier, Paco Munnariz
 */
public class ControllerLogin extends ControllerUser {

    // Composants FXML -----------------------------------------------------------------------------------------------
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
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Methode appelée lors du clic sur le bouton "Se connecter"
     */
    @FXML
    private void handleButtonConnect() {
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

    /**
     * @see ControllerUser#goToRegister(Control)
     * @throws IOException si la page n'existe pas
     */
    @FXML
    private void goToRegister() throws IOException {
        super.goToRegister(registerButton);
    }

    /**
     * @see ControllerUser#goToHome(Control)
     * @throws IOException si la page n'existe pas
     */
    @FXML
    private void goToHome() throws IOException {
        super.goToHome(loginButton);
    }

    /**
     * @see ControllerUser#goToVisitor(Control)
     * @throws IOException si la page n'existe pas
     */
    @FXML
    private void goToVisitor() throws IOException {
        super.goToVisitor(visitorButton);
    }

    /**
     * Vérifie que le formulaire est complet
     * @return true si le formulaire est complet, false sinon
     */
    private boolean checkForm() {
        return !username.getText().isEmpty() && !password.getText().isEmpty();
    }

    /**
     * Affiche un message d'erreur
     * @param message String, le message d'erreur à afficher
     */
    private void displayError(String message) {
        errorMessage.setText(message);
    }

    /**
     * Cache le message d'erreur
     */
    private void hideError() {
        errorMessage.setText("");
    }
}