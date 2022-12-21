package com.fitj.controllers.sports;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
    @FXML
    private Text errorText;
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
    }

    /**
     * Méthode appelée lors du clic sur le bouton "Ajouter". Ajoute le sport
     */
    @FXML
    private void addSport() {
        try {
            hideError(errorText);
            sportFacade.createSport(sportName.getText());
            super.goToMonEspace(addSportButton);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }
}
