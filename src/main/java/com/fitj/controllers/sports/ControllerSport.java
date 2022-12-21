package com.fitj.controllers.sports;

import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.FacadeSport;
import javafx.scene.control.Control;

public abstract class ControllerSport extends Controller {
    /**
     * Facade pour les sports
     */
    final FacadeSport sportFacade = FacadeSport.getInstance();

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages accessibles aux admins
     */
    private final String admin = "admins/";

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages relatives aux admins
     */
    private final String sport = "sports/";

    /**
     * Methode permettant de se rendre sur la page mon compte
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToMonCompte(Control controlEl) throws BadPageException {
        goToPage(controlEl, admin + "monCompte-admin.fxml", "MonCompte");
    }

    /**
     * Methode permettant de se rendre sur la page coachs
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToCoachs(Control controlEl) throws BadPageException {
        goToPage(controlEl, admin + "coachs-admin.fxml", "Coachs");
    }

    /**
     * Methode permettant de se rendre sur la page mon espace
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToMonEspace(Control controlEl) throws BadPageException {
        goToPage(controlEl, admin + "monEspace-admin.fxml", "MonEspace");
    }

    /**
     * Methode permettant de se rendre sur la page shop
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToShop(Control controlEl) throws BadPageException {
        goToPage(controlEl, admin + "shop-admin.fxml", "Shop");
    }

    /**
     * Methode permettant de se rendre sur la page clients
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToClients(Control controlEl) throws BadPageException {
        goToPage(controlEl, admin + "clients-admin.fxml", "Clients");
    }
}
