package com.fitj.controllers.users;

import com.fitj.controllers.Controller;
import com.fitj.facades.FacadeUser;
import javafx.scene.control.Control;
import java.io.IOException;

/**
 * Controller générique pour les pages accessibles aux visiteurs
 * @see Controller
 * @author Romain Frezier
 */
public abstract class ControllerUser extends Controller {

    /**
     * Facade pour les visiteurs
     */
    final FacadeUser userFacade = FacadeUser.getInstance();

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages accessibles aux utilisateurs
     */
    private final String path = "users/";

    /**
     * Methode permettant de se rendre sur la page de création de compte
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'est pas trouvée
     */
    void goToRegister(Control controlEl) throws IOException {
        goToPage(controlEl, path + "register-view.fxml", "Register");
    }

    /**
     * Methode permettant de se rendre sur la page de connexion
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToLogin(Control controlEl) throws IOException {
        goToPage(controlEl, path + "login-view.fxml", "Login");
    }

    /**
     * Methode permettant de se rendre sur la page d'accueil visiteur
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToVisitor(Control controlEl) throws IOException {
        goToPage(controlEl, path + "visitor-view.fxml", "Welcome to FitJ");
    }

    /**
     * Methode permettant de se rendre sur la page d'accueil
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToHome(Control controlEl) throws IOException {
        goToPage(controlEl, path + "monCompte-client.fxml", "Mon Compte");
    }
}
