package com.fitj.controllers.users;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;

import java.io.IOException;

/**
 * Controller pour la page d'accueil visiteur
 * @see ControllerUser
 * @author Romain Frezier
 */
public class ControllerVisitor extends ControllerUser {
    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * @throws IOException si la vue n'existe pas
     * @see ControllerUser#goToRegister(Control)
     */
    @FXML
    private void goToRegister() throws IOException {
        super.goToRegister(registerButton);
    }

    /**
     * @throws IOException si la vue n'existe pas
     * @see ControllerUser#goToLogin(Control)
     */
    @FXML
    private void goToLogin() throws IOException {
        super.goToLogin(loginButton);
    }
}
