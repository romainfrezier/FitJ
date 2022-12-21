package com.fitj.controllers.headers;

import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du clique sur le bouton redirigeant vers la page d'incription
     * @throws BadPageException si la vue n'existe pas
     */
    @FXML
    private void goToRegister() throws BadPageException {
        super.goToPage(loginButton, "users/register-view.fxml", "Inscription");
    }

    /**
     * Méthode appelée lors du clique sur le bouton redirigeant vers la page de connexion
     * @throws BadPageException si la vue n'existe pas
     */
    @FXML
    private void goToLogin() throws BadPageException {
        super.goToPage(registerButton, "users/login-view.fxml", "Connexion");
    }

}
