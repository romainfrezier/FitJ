package com.fitj.controllers.users;

import com.fitj.classes.Admin;
import com.fitj.classes.Client;
import com.fitj.classes.Coach;
import com.fitj.exceptions.BadLoginException;
import com.fitj.exceptions.BadPageException;
import com.fitj.exceptions.BadPasswordException;
import com.fitj.exceptions.UncompletedFormException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
        try {
            checkForm();
            Client client = super.userFacade.connexion(username.getText(), password.getText());
            if (client != null) {

                hideError();
                try {
                    String scope;
                    if (client instanceof Admin) {
                        scope = "admin";
                    } else if (client instanceof Coach) {
                        scope = "coach";
                    } else {
                        scope = "client";
                    }
                    goToHome(scope);
                } catch (BadPageException e) {
                    displayError(e.getMessage());
                }
            }
        } catch (Exception e) {
            displayError(e.getMessage());
        }
    }

    /**
     * @see ControllerUser#goToRegister(Control)
     * @throws BadPageException si la page n'existe pas
     */
    @FXML
    private void goToRegister() throws BadPageException {
        super.goToRegister(registerButton);
    }

    /**
     * @see ControllerUser#goToHome(Control, String)
     * @throws BadPageException si la page n'existe pas
     */
    @FXML
    private void goToHome(String scope) throws BadPageException {
        super.goToHome(loginButton, scope);
    }

    /**
     * @see ControllerUser#goToVisitor(Control)
     * @throws BadPageException si la page n'existe pas
     */
    @FXML
    private void goToVisitor() throws BadPageException {
        super.goToVisitor(visitorButton);
    }

    /**
     * Vérifie que le formulaire est complet
     * @return true si le formulaire est complet, false sinon
     */
    private void checkForm() throws UncompletedFormException {
        if (username.getText().equals("") || password.getText().equals("")) {
            throw new UncompletedFormException("Le formulaire n'est pas complet");
        }
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