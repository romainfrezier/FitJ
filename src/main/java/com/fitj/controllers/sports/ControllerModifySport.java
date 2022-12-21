package com.fitj.controllers.sports;

import com.fitj.classes.Sport;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller de la page de modification d'un sport
 * @see ControllerSport
 * @author Romain Frezier
 */
public class ControllerModifySport extends ControllerSport {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private TextField sportNameUpdate;
    @FXML
    private Button updateSportButton;
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Identifiant du sport à modifier ou supprimer
     */
    private int idSportSelected;

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        Sport sport = null;
        idSportSelected = getIdObjectSelected();
        try {
            sport = sportFacade.getSportById(idSportSelected);
        } catch (Exception e) {
            displayError(e.getMessage());
        }
        if (sport != null) {
            sportNameUpdate.setText(sport.getNom());
        }
    }

    /**
     * Methode appelée lors du clic sur le bouton "Modifier". Modifie le sport
     */
    @FXML
    private void modifySport() {
        try {
            if (sportNameUpdate.getText() != null) {
                sportFacade.updateSport(idSportSelected, sportNameUpdate.getText());
                super.goToMonEspace(updateSportButton);
            }
        } catch (Exception e) {
            displayError(e.getMessage());
        }
    }

    /**
     * Affiche le message d'erreur
     * @param message String, message d'erreur à afficher
     */
    private void displayError(String message) {
    }
}
