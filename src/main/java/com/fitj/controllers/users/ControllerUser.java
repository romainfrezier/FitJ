package com.fitj.controllers.users;

import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.FacadeUser;
import javafx.scene.control.Control;

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
     * Methode permettant de se rendre sur la page d'accueil visiteur
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToVisitor(Control controlEl) throws BadPageException {
        goToPage(controlEl, path + "visitor-view.fxml", "Welcome to FitJ");
    }

    /**
     * Methode permettant de se rendre sur la page d'accueil
     * @param controlEl Control, élément de contrôle de la page
     * @param scope String, niveau du client connecté (admin, coach, client)
     * @throws BadPageException si la vue n'existe pas
     */
    void goToHome(Control controlEl, String scope) throws BadPageException {
        String pathClient = scope + "s/";
        goToPage(controlEl, pathClient + "monEspace-" + scope + ".fxml", "Mon Compte");
    }
}
