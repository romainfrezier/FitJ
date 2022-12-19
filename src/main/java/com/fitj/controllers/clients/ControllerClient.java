package com.fitj.controllers.clients;

import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.FacadeClient;
import javafx.scene.control.Control;

/**
 * Controller générique pour les pages accessibles aux clients
 * @see Controller
 * @author Paco Munnariz
 */
public abstract class ControllerClient extends Controller {

    /**
     * Facade pour les clients
     */
    final FacadeClient clientFacade = FacadeClient.getInstance();

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages accessibles aux clients
     */
    private final String path = "clients/";

    /**
     * Methode permettant de se rendre sur la page mon compte
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToMonCompte(Control controlEl) throws BadPageException {
        goToPage(controlEl, path + "monCompte-client.fxml", "Mon Compte");
    }

    /**
     * Methode permettant de se rendre sur la page coachs
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToCoachs(Control controlEl) throws BadPageException {
        goToPage(controlEl, path + "coachs-client.fxml", "Coachs");
    }

    /**
     * Methode permettant de se rendre sur la page mon espace
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToMonEspace(Control controlEl) throws BadPageException {
        goToPage(controlEl, path + "monEspace-client.fxml", "MonEspace");
    }

    /**
     * Methode permettant de se rendre sur la page shop
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToShop(Control controlEl) throws BadPageException {
        goToPage(controlEl, path + "shop-client.fxml", "Shop");
    }
}
