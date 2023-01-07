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

    /**
     * Facade pour les clients
     */
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


    /**
     *Méthode permettant de naviguer vers la page de mise à jour du mot de passe
     *@param controlEl élément de contrôle à partir duquel la navigation est déclenchée
     *@throws BadPageException lorsque la page cible est introuvable
     */
    void goToUpdatePassword(Control controlEl) throws BadPageException {
        goToPage(controlEl, monCompte + "update-password.fxml", "UpdatePassword");
    }

    /**
     *Méthode permettant de naviguer vers la page de "Mon Compte" en fonction du type de compte de l'utilisateur actuel
     *@param controlEl élément de contrôle à partir duquel la navigation est déclenchée
     *@throws BadPageException lorsque la page cible est introuvable
     */
    protected void goToMonCompte(Control controlEl) throws BadPageException {
        if (Facade.currentClient instanceof Admin) {
            goToPage(controlEl, "admins/monCompte-admin.fxml", "Mon Compte");
        } else if (Facade.currentClient instanceof Coach) {
            goToPage(controlEl, "coachs/monCompte-coach.fxml", "Mon Compte");
        } else {
            goToPage(controlEl, "clients/monCompte-client.fxml", "Mon Compte");
        }
    }

    /**
     *Méthode permettant de naviguer vers la page de payement
     *@param controlEl élément de contrôle à partir duquel la navigation est déclenchée
     *@throws BadPageException lorsque la page cible est introuvable
     */
    protected void goToMakePayment(Control controlEl) throws BadPageException {
        goToPage(controlEl, "paiements/paiement.fxml", "Retirer");
    }
}


