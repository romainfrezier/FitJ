package com.fitj.controllers.exercices;

import com.fitj.classes.Exercice;
import com.fitj.classes.Materiel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ControllerUpdateExercice extends ControllerExercice {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private TextField exerciceName;
    @FXML
    private TextArea exerciceDescription;
    @FXML
    private Button updateExerciceButton;
    @FXML
    private Text errorText;
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Identifiant de l'exercice à modifier
     */
    private int idExerciceSelected;

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        Exercice exercice = null;
        idExerciceSelected = getIdObjectSelected();
        try {
            exercice = exerciceFacade.getExerciceById(idExerciceSelected);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
        if (exercice != null) {
            exerciceName.setText(exercice.getNom());
            exerciceDescription.setText(exercice.getDescription());
        }
    }

    /**
     * Méthode appelée lors du clic sur le bouton "Modifier". Modifie l'exercice
     */
    @FXML
    private void updateExercice() {
        super.hideError(errorText);
        try {
            if (exerciceName.getText() != null && exerciceDescription.getText() != null) {
                exerciceFacade.updateExercice(idExerciceSelected, exerciceName.getText(), exerciceDescription.getText());
                super.goToMonEspace(updateExerciceButton);
            } else {
                super.displayError(errorText, "Veuillez remplir tous les champs");
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }
}
