package com.fitj.controllers.programmes.programmesNutritions;

import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.FacadeProgrammeNutrition;
import javafx.scene.control.Control;

/**
 * Controller pour les ves vues des programmes nutrition
 * @author Etienne Tillier
 */
public abstract class ControllerProgrammeNutrition extends Controller {
    /**
     * Facade pour les programmes nutritions
     */
    final FacadeProgrammeNutrition facadeProgrammeNutrition = FacadeProgrammeNutrition.getInstance();

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages accessibles aux admins
     */
    private final String admin = "admins/";

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages relatives aux admins
     */
    private final String programmeNutrition = "programmes/programmesNutritions/";

    /**
     * Methode permettant de se rendre sur la page mon espace
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToMonEspace(Control controlEl) throws BadPageException {
        goToPage(controlEl, admin + "monEspace-admin.fxml", "MonEspace");
    }

    /**
     * Methode permettant de se rendre sur la page d'ajout d'un programme nutrition
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToAddProgrammeNutrition(Control controlEl) throws BadPageException {
        goToPage(controlEl, programmeNutrition + "create-programmeNutrition.fxml", "Création d'un programme nutrition");
    }


    /**
     * Methode permettant de se rendre sur la page de modification d'un programme nutrition
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToUpdateProgrammeNutrition(Control controlEl) throws BadPageException {
        goToPage(controlEl, programmeNutrition + "update-programmeNutrition.fxml", "Modification d'un programme nutrition");
    }

    /**
     * Methode permettant de se rendre sur la page de détail d'un programme nutrition
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToDetailProgrammeNutrition(Control controlEl) throws BadPageException {
        goToPage(controlEl, programmeNutrition + "detail-programmeNutrition.fxml", "Détail d'un programme nutrition");
    }

    /**
     * Methode permettant de se rendre sur la page de détail d'une recette
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToDetailRecette(Control controlEl) throws BadPageException {
        goToPage(controlEl, "recettes/" + "detail-recette.fxml", "Détail d'une recette");
    }

}
