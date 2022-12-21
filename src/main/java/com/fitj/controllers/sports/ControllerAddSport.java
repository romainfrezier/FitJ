package com.fitj.controllers.sports;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller de la page d'ajout d'un sport
 * @see ControllerSport
 * @author Romain Frezier
 */
public class ControllerAddSport extends ControllerSport {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private TextField sportName;
    @FXML
    private Button addSportButton;
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du clic sur le bouton "Ajouter". Ajoute le sport
     */
    @FXML
    private void addSport() {
        try {
            sportFacade.createSport(sportName.getText());
            super.goToMonEspace(addSportButton);
        } catch (Exception e) {
            displayError(e.getMessage());
        }
    }

    /**
     * Affiche le message d'erreur
     * @param message Message d'erreur à afficher
     */
    private void displayError(String message) {
    }
}
