package com.fitj.controllers.monCompte;

import com.fitj.classes.Admin;
import com.fitj.classes.Coach;
import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.Facade;
import com.fitj.facades.FacadeClient;
import javafx.scene.control.Control;

/**
 * Controller générique des pages materiel
 */
public abstract class ControllerMonCompte extends Controller {

    final FacadeClient clientFacade = FacadeClient.getInstance();


    /**
     * Chemin du dossier dans lequel se trouve les pages ou l'on peut modifier les informations du compte
     */
    private final String monCompte = "monCompte/";

    /**
     * Methode permettant de se rendre sur la page upload
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToUpdate(Control controlEl) throws BadPageException {
        goToPage(controlEl, monCompte + "update-monCompte.fxml", "Update");
    }

    void goToUpdatePassword(Control controlEl) throws BadPageException {
        goToPage(controlEl, monCompte + "update-password.fxml", "UpdatePassword");
    }

    protected void goToMonCompte(Control controlEl) throws BadPageException {
        if (Facade.currentClient instanceof Admin) {
            goToPage(controlEl, "admins/monCompte-admin.fxml", "Mon Compte");
        } else if (Facade.currentClient instanceof Coach) {
            goToPage(controlEl, "coachs/monCompte-coach.fxml", "Mon Compte");
        } else {
            goToPage(controlEl, "clients/monCompte-client.fxml", "Mon Compte");
        }
    }

    protected void goToMakePayment(Control controlEl) throws BadPageException {
        goToPage(controlEl, "paiements/paiement.fxml", "Retirer");
    }
}


