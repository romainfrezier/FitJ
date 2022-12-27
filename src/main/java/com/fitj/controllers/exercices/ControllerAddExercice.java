package com.fitj.controllers.exercices;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Controller de la page d'ajout d'un exercice
 * @see ControllerExercice
 * @author Paul Merceur
 */
public class ControllerAddExercice extends ControllerExercice {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private TextField exerciceName;

    @FXML
    private TextArea exerciceDescription;
    @FXML
    private Button addExerciceButton;
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
     * Méthode appelée lors du clic sur le bouton "Ajouter". Ajoute l'exercice
     */
    @FXML
    private void addExercice() {
        try {
            hideError(errorText);
            exerciceFacade.createExercice(exerciceName.getText(), exerciceDescription.getText());
            super.goToMonEspace(addExerciceButton);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }
}
