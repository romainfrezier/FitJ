package com.fitj.controllers.headers;

import com.fitj.exceptions.BadPageException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * Controller pour le header de la page coach
 * @see ControllerHeader
 * @author Romain Frezier
 */
public class ControllerHeaderCoach extends ControllerHeader {

    /**
     * Chemin caractérisant la page coach
     */
    private final String path = "coach";

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
    private Button mesClients;
    @FXML
    private Text errorText;
    // --------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement du header
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        super.setPath(path);
    }

    /**
     * Methode permettant de se rendre sur la page mon compte
     */
    @FXML
    private void goToMonCompte() {
        try{
            super.hideError(errorText);
            super.goToMonCompte(monCompte);
        }catch (BadPageException e){
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
        }catch (BadPageException e){
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
        }catch (BadPageException e){
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
        }catch (BadPageException e){
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de se rendre sur la page des clients du coach
     */
    @FXML
    private void goToMesClients() {
        try{
            super.hideError(errorText);
            super.goToPage(mesClients, path + "s/mesClients-" + path + ".fxml", "Mes Clients");
        } catch (BadPageException e){
            super.displayError(errorText, e.getMessage());
        }
    }
}