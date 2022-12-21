package com.fitj.controllers.headers;

import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * Controller pour le header de la page admin
 * @see Controller
 * @author Romain Frezier
 */
public class ControllerHeaderVisitor extends Controller {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Text errorText;
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement du header
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
    }

    /**
     * Méthode appelée lors du clique sur le bouton redirigeant vers la page d'incription
     */
    @FXML
    private void goToRegister() {
        try {
            super.hideError(errorText);
            super.goToPage(loginButton, "users/register-view.fxml", "Inscription");
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode appelée lors du clique sur le bouton redirigeant vers la page de connexion
     */
    @FXML
    private void goToLogin() {
        try {
            super.hideError(errorText);
            super.goToPage(registerButton, "users/login-view.fxml", "Connexion");
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

}
