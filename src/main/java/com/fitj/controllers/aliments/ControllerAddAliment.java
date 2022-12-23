package com.fitj.controllers.aliments;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Controller de la page d'ajout d'un aliment
 * @see ControllerAliment
 * @author Paco Munarriz
 */
public class ControllerAddAliment extends ControllerAliment {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private TextField alimentName;
    @FXML
    private Button addAlimentButton;
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
     * Méthode appelée lors du clic sur le bouton "Ajouter". Ajoute le aliment
     */
    @FXML
    private void addAliment() {
        try {
            hideError(errorText);
            alimentFacade.createAliment(alimentName.getText());
            super.goToMonEspace(addAlimentButton);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }
}
