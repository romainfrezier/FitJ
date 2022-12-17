package com.fitj.controllers.users;

import com.fitj.exceptions.BadPageException;
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
     * @throws BadPageException si la vue n'existe pas
     * @see ControllerUser#goToRegister(Control)
     */
    @FXML
    private void goToRegister() throws BadPageException {
        super.goToRegister(registerButton);
    }

    /**
     * @throws BadPageException si la vue n'existe pas
     * @see ControllerUser#goToLogin(Control)
     */
    @FXML
    private void goToLogin() throws BadPageException {
        super.goToLogin(loginButton);
    }
}
