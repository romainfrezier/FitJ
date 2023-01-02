package com.fitj.controllers.seances;

import com.fitj.classes.Admin;
import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.Facade;
import com.fitj.facades.FacadeRecette;
import com.fitj.facades.FacadeSeance;
import javafx.scene.control.Control;

/**
 * Controller générique des pages séances
 *
 * @author Etienne Tillier
 */
public abstract class ControllerSeance extends Controller {


    /**
     * Facade pour les recettes
     */
    final FacadeSeance facadeSeance = FacadeSeance.getInstance();

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages accessibles aux admins
     */
    private final String admin = "admins/";

    private final String coach = "coachs/";

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages relatives aux admins
     */
    private final String recette = "seances/";

    /**
     * Methode permettant de se rendre sur la page mon espace
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToMonEspace(Control controlEl) throws BadPageException {
        if (Facade.currentClient instanceof Admin) {
            goToPage(controlEl, admin + "monEspace-admin.fxml", "Mon Compte");
        } else {
            goToPage(controlEl,  coach + "monEspace-coach.fxml", "Mon Compte");
        }
    }

    /**
     * Methode permettant de se rendre sur la page d'ajout d'une séance
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToAddSeance(Control controlEl) throws BadPageException {
        goToPage(controlEl, recette + "create-seance.fxml", "Création d'une séance");
    }

    /**
     * Methode permettant de se rendre sur la page de modification d'une séance
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToUpdateSeance(Control controlEl) throws BadPageException {
        goToPage(controlEl, recette + "update-seance.fxml", "Modification d'une séance");
    }

    /**
     * Methode permettant de se rendre sur la page de détail d'une séance
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToDetailSeance(Control controlEl) throws BadPageException {
        goToPage(controlEl, recette + "detail-seance.fxml", "Détail d'une séance");
    }
}
