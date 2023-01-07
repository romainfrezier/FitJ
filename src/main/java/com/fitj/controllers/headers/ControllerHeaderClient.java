package com.fitj.controllers.headers;

import com.fitj.exceptions.BadPageException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * Controller pour le header de la page client
 * @see ControllerHeader
 * @author Romain Frezier
 */
public class ControllerHeaderClient extends ControllerHeader {

    /**
     * Chemin caractérisant la page client
     */
    private final String path = "client";
    @FXML
    private ImageView newNotifIcon;

    // Composants FXML ----------------------------------------------
    @FXML
    private Button monCompte;
    @FXML
    private Button coachs;
    @FXML
    private Button monEspace;
    @FXML
    private Button shop;
    @FXML
    private Text errorText;
    @FXML
    private ImageView notifIcon;
    // --------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement du header
     */
    @FXML
    private void initialize() {
        super.setPath(path);
        super.getNotifIcon(notifIcon, newNotifIcon, errorText);
    }

    /**
     * Methode permettant de se rendre sur la page mon compte
     */
    @FXML
    private void goToMonCompte() {
        try{
            super.hideError(errorText);
            super.goToMonCompte(monCompte);
        } catch (BadPageException e){
                super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de se rendre sur la page coachs
     */
    @FXML
    private void goToCoachs() {
        try{
            super.hideError(errorText);
            super.goToCoachs(coachs);
        } catch (BadPageException e){
            super.displayError(errorText, e.getMessage());
 }
    }

    /**
     * Methode permettant de se rendre sur la page mon espace
     */
    @FXML
    private void goToMonEspace() {
        try{
            super.hideError(errorText);
            super.goToMonEspace(monEspace);
        } catch (BadPageException e){
                    super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de se rendre sur la page shop
     */
    @FXML
    private void goToShop() {
        try{
            super.hideError(errorText);
            super.goToShop(shop);
        } catch (BadPageException e){
            super.displayError(errorText, e.getMessage());
        }

    }

    /**
     * Methode permettant de se rendre sur la page des notifications et des commandes
     */
    @FXML
    private void goToNotification() {
        try{
            super.hideError(errorText);
            super.goToNotification(shop); // Shop est sur la même page que l'icône des notifications
        } catch (BadPageException e){
            super.displayError(errorText, e.getMessage());
        }
    }
}
