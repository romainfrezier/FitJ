package com.fitj.controllers.recettes;

import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.FacadeRecette;
import javafx.scene.control.Control;

/**
 * Controller générique des pages recettes
 *
 * @author Etienne Tillier
 */
public abstract class ControllerRecette extends Controller {

    /**
     * Facade pour les recettes
     */
    final FacadeRecette recetteFacade = FacadeRecette.getInstance();

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages accessibles aux admins
     */
    private final String admin = "admins/";

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages relatives aux admins
     */
    private final String recette = "recettes/";

    /**
     * Methode permettant de se rendre sur la page mon espace
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToMonEspace(Control controlEl) throws BadPageException {
        goToPage(controlEl, admin + "monEspace-admin.fxml", "MonEspace");
    }

    /**
     * Methode permettant de se rendre sur la page d'ajout d'une recette
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToAddRecette(Control controlEl) throws BadPageException {
        goToPage(controlEl, recette + "create-recette.fxml", "Création d'une recette");
    }

    /**
     * Methode permettant de se rendre sur la page de modification d'une recette
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToUpdateRecette(Control controlEl) throws BadPageException {
        goToPage(controlEl, recette + "update-recette.fxml", "Modification d'une recette");
    }

    /**
     * Methode permettant de se rendre sur la page de détail d'une recette
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToDetailRecette(Control controlEl) throws BadPageException {
        goToPage(controlEl, recette + "detail-recette.fxml", "Détail d'une recette");
    }


}
