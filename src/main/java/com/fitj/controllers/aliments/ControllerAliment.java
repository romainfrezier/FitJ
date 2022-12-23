package com.fitj.controllers.aliments;

import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.FacadeAliment;
import javafx.scene.control.Control;

/**
 * Controller générique des pages aliment
 */
public abstract class ControllerAliment extends Controller {

    /**
     * Facade pour les aliments
     */
    final FacadeAliment alimentFacade = FacadeAliment.getInstance();

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages accessibles aux admins
     */
    private final String admin = "admins/";

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages relatives aux admins
     */
    private final String aliment = "aliments/";

    /**
     * Methode permettant de se rendre sur la page mon espace
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToMonEspace(Control controlEl) throws BadPageException {
        goToPage(controlEl, admin + "monEspace-admin.fxml", "MonEspace");
    }

    /**
     * Methode permettant de se rendre sur la page d'ajout d'un aliment
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToAddAliment(Control controlEl) throws BadPageException {
        goToPage(controlEl, aliment + "create-aliment.fxml", "Création d'un aliment");
    }

    /**
     * Methode permettant de se rendre sur la page de modification d'un aliment
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToUpdateAliment(Control controlEl) throws BadPageException {
        goToPage(controlEl, aliment + "update-aliment.fxml", "Modification d'un aliment");
    }
}
