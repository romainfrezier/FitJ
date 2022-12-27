package com.fitj.controllers.materiels;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Controller de la page d'ajout d'un materiel
 * @see ControllerMateriel
 * @author Paco Munarriz
 */
public class ControllerAddMonMateriel extends ControllerMateriel {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private TextField materielName;
    @FXML
    private Button addMaterielButton;
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
     * Méthode appelée lors du clic sur le bouton "Ajouter". Ajoute le materiel
     */
    @FXML
    private void addMateriel() {
        try {
            hideError(errorText);
            materielFacade.createMateriel(materielName.getText());
            super.goToMonEspace(addMaterielButton);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }
}
