package com.fitj.controllers.exercices;

import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.FacadeExercice;
import javafx.scene.control.Control;

/**
 * Controller générique des pages exercices
 * @see Controller
 * @author Paul Merceur
 */
public class ControllerExercice extends Controller {

    /**
     * Facade pour les exercices
     */
    final FacadeExercice exerciceFacade = FacadeExercice.getInstance();

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages relatives aux admins
     */
    private final String admin = "admins/";

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages relatives aux coachs
     */
    private final String coach = "coachs/";

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages relatives aux exercices
     */
    private final String exercice = "exercices/";

    /**
     * Methode permettant de se rendre sur la page mon espace
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToMonEspace(Control controlEl) throws BadPageException {
        if (exerciceFacade.currentClient.getClass().getName() == "Admin") {
            goToPage(controlEl, admin + "monEspace-admin.fxml", "MonEspace");
        } else {
            goToPage(controlEl, coach + "monEspace-coach.fxml", "MonEspace");
        }
    }

    /**
     * Methode permettant de se rendre sur la page d'ajout d'un exercice
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToAddExercice(Control controlEl) throws BadPageException {
        goToPage(controlEl, exercice + "create-exercice.fxml", "Création d'un exercice");
    }

    /**
     * Methode permettant de se rendre sur la page de modification d'un exercice
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToUpdateExercice(Control controlEl) throws BadPageException {
        goToPage(controlEl, exercice + "update-exercice.fxml", "Modification d'un exercice");
    }

}
