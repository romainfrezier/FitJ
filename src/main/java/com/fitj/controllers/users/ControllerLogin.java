package com.fitj.controllers.users;

import com.fitj.classes.Admin;
import com.fitj.classes.Client;
import com.fitj.classes.Coach;
import com.fitj.exceptions.BadPageException;
import com.fitj.exceptions.UncompletedFormException;
import com.fitj.facades.Facade;
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
    private TextField username;
    @FXML
    private TextField password;

    @FXML
    private Text errorText;

    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button visitorButton;
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
    }

    /**
     * Methode appelée lors du clic sur le bouton "Se connecter"
     */
    @FXML
    private void handleButtonConnect() {
        try {
            checkForm();
            Client client = super.userFacade.connexion(username.getText(), password.getText());
            if (client != null) {
                super.hideError(errorText);
                String scope;
                if (client instanceof Admin) {
                    scope = "admin";
                } else if (client instanceof Coach) {
                    scope = "coach";
                } else {
                    scope = "client";
                }
                System.out.println(client.getId());
                Facade.currentClient = client;
                goToHome(scope);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode appelée pour rediriger vers l'intérieur de l'application
     * @see ControllerUser#goToHome(Control, String)
     */
    private void goToHome(String scope) {
        try {
            super.hideError(errorText);
            super.goToHome(loginButton, scope);
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode appelée lors du clic sur le bouton redirigeant vers la page d'accueil visiteur
     * @see ControllerUser#goToVisitor(Control)
     */
    @FXML
    private void goToVisitor() {
        try {
            super.hideError(errorText);
            super.goToVisitor(visitorButton);
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode appelée lors du clic sur le bouton redirigeant vers la page d'inscription
     */
    @FXML
    private void goToRegister() {
        try {
            super.hideError(errorText);
            super.goToPage(registerButton, "users/register-view.fxml", "Inscription");
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Vérifie que le formulaire est complet
     * @throws UncompletedFormException si le formulaire n'est pas complet
     */
    private void checkForm() throws UncompletedFormException {
        if (username.getText().equals("") || password.getText().equals("")) {
            throw new UncompletedFormException("Le formulaire n'est pas complet");
        }
    }
}