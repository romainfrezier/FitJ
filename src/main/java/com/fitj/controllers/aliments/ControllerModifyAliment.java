package com.fitj.controllers.aliments;

import com.fitj.classes.Aliment;
import com.fitj.controllers.aliments.ControllerAliment;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Controller de la page de modification d'un aliment
 * @see ControllerAliment
 * @author Paco Munarriz
 */
public class ControllerModifyAliment extends ControllerAliment {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private TextField alimentNameUpdate;
    @FXML
    private Button updateAlimentButton;
    @FXML
    private Text errorText;
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Identifiant du aliment à modifier ou supprimer
     */
    private int idAlimentSelected;

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        Aliment aliment = null;
        idAlimentSelected = getIdObjectSelected();
        try {
            aliment = alimentFacade.getAlimentById(idAlimentSelected);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
        if (aliment != null) {
            alimentNameUpdate.setText(aliment.getNom());
        }
    }

    /**
     * Methode appelée lors du clic sur le bouton "Modifier". Modifie le aliment
     */
    @FXML
    private void modifyAliment() {
        try {
            super.hideError(errorText);
            if (alimentNameUpdate.getText() != null) {
                alimentFacade.updateAliment(idAlimentSelected, alimentNameUpdate.getText());
                super.goToMonEspace(updateAlimentButton);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }
}
