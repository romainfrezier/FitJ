package com.fitj.controllers.sports;

import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.FacadeSport;
import javafx.scene.control.Control;

/**
 * Controller générique des pages sport
 */
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
     * Methode permettant de se rendre sur la page mon espace
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToMonEspace(Control controlEl) throws BadPageException {
        goToPage(controlEl, admin + "monEspace-admin.fxml", "MonEspace");
    }

    /**
     * Methode permettant de se rendre sur la page d'ajout d'un sport
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToAddSport(Control controlEl) throws BadPageException {
        goToPage(controlEl, sport + "create-sport.fxml", "Création d'un sport");
    }

    /**
     * Methode permettant de se rendre sur la page d'ajout d'un sport dans la liste des sports s'un client
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToAddMySport(Control controlEl) throws BadPageException {
        goToPage(controlEl, sport + "add-mysport.fxml", "Ajout d'un sport");
    }



    /**
     * Methode permettant de se rendre sur la page de modification d'un sport
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToUpdateSport(Control controlEl) throws BadPageException {
        goToPage(controlEl, sport + "update-sport.fxml", "Modification d'un sport");
    }
}
