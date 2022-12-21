package com.fitj.controllers.sports;

import com.fitj.classes.Sport;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
    @FXML
    private Text errorText;
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
        super.hideError(errorText);
        Sport sport = null;
        idSportSelected = getIdObjectSelected();
        try {
            sport = sportFacade.getSportById(idSportSelected);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
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
            super.hideError(errorText);
            if (sportNameUpdate.getText() != null) {
                sportFacade.updateSport(idSportSelected, sportNameUpdate.getText());
                super.goToMonEspace(updateSportButton);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }
}
