package com.fitj.controllers.seances;

import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.Facade;
import com.fitj.facades.FacadeSeance;
import javafx.scene.control.Control;

/**
 * Controller générique des pages seances
 * @see Controller
 * @author Paul Merceur
 */
public abstract class ControllerSeance extends Controller {

    /**
     * Facade pour les seances
     */
    final FacadeSeance seanceFacade = FacadeSeance.getInstance();

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages relatives aux admins
     */
    private final String admin = "admins/";

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages relatives aux coachs
     */
    private final String coach = "coachs/";

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages relatives aux clients
     */
    private final String client = "clients/";

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages relatives aux seances
     */
    private final String seance = "seances/";

    /**
     * Methode permettant de se rendre sur la page mon espace
     *
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToMonEspace(Control controlEl) throws BadPageException {
        String clientType = Facade.currentClient.getClass().getSimpleName();
        if (clientType.equals("Admin")) {
            goToPage(controlEl, admin + "monEspace-admin.fxml", "MonEspace");
        } else if (clientType.equals("Coach")) {
            goToPage(controlEl, coach + "monEspace-coach.fxml", "MonEspace");
        } else {
            goToPage(controlEl, client + "monEspace-client.fxml", "MonEspace");
        }
    }

    /**
     * Methode permettant de se rendre sur la page d'ajout d'une seance
     *
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToAddSeance(Control controlEl) throws BadPageException {
        String path = "";
        String clientType = Facade.currentClient.getClass().getSimpleName();
        if (clientType.equals("Admin")) {
            path = seance + admin;
        } else if (clientType.equals("Coach")) {
            path = seance + coach;
        }
        goToPage(controlEl, path + "create-seance.fxml", "Ajouter une séance");
    }

    /**
     * Methode permettant de se rendre sur la page de modification d'une seance
     *
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToUpdateSeance(Control controlEl) throws BadPageException {
        String path = "";
        String clientType = Facade.currentClient.getClass().getSimpleName();
        if (clientType.equals("Admin")) {
            path = seance + admin;
        } else if (clientType.equals("Coach")) {
            path = seance + coach;
        }
        goToPage(controlEl, path + "update-seance.fxml", "Modifier une séance");
    }


}
