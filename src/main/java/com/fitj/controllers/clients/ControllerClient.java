package com.fitj.controllers.clients;

import com.fitj.controllers.Controller;
import com.fitj.facades.FacadeClient;
import com.fitj.facades.factory.FactoryFacade;
import javafx.scene.control.Control;
import java.io.IOException;

/**
 * Controller générique pour les pages accessibles aux visiteurs
 * @see Controller
 * @author Paco Munnariz
 */
public abstract class ControllerClient extends Controller {

    /**
     * Facade pour les clients
     */
    final FacadeClient clientFacade = FactoryFacade.getInstance().getFacadeClient();

    /**
     * Methode permettant de se rendre sur la page de création de compte
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'est pas trouvée
     */
    void goToMonCompte(Control controlEl) throws IOException {
        goToPage(controlEl, "register-view.fxml", "Register");
    }

    /**
     * Methode permettant de se rendre sur la page de connexion
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToLogin(Control controlEl) throws IOException {
        goToPage(controlEl, "login-view.fxml", "Login");
    }

    /**
     * Methode permettant de se rendre sur la page d'accueil visiteur
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToVisitor(Control controlEl) throws IOException {
        goToPage(controlEl, "visitor-view.fxml", "Welcome to FitJ");
    }

    /**
     * Methode permettant de se rendre sur la page d'accueil
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToHome(Control controlEl) throws IOException {
        goToPage(controlEl, "mon-compte.fxml", "Home");
    }
}
