package com.fitj.controllers.coachs;

import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

/**
 * Controller pour la page d'accueil du coach
 * @see ControllerCoach
 * @author Romain Frezier
 */
public class ControllerCoachMonEspace extends ControllerCoach {

    // Composants FXML-----------------------------------------------------------------------------------------------
    @FXML
    private Text coachName;
    // --------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    public void initialize(){
        coachName.setText(Facade.currentClient.getPseudo());
    }
}
