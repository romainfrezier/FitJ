package com.fitj.controllers.materiels;

import com.fitj.classes.Materiel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Controller de la page de modification d'un materiel
 * @see ControllerMateriel
 * @author Paco Munarriz
 */
public class ControllerModifyMateriel extends ControllerMateriel {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private TextField materielNameUpdate;
    @FXML
    private Button updateMaterielButton;
    @FXML
    private Text errorText;
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Identifiant du materiel à modifier ou supprimer
     */
    private int idMaterielSelected;

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        Materiel materiel = null;
        idMaterielSelected = getIdObjectSelected();
        try {
            materiel = materielFacade.getMaterielById(idMaterielSelected);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
        if (materiel != null) {
            materielNameUpdate.setText(materiel.getNom());
        }
    }

    /**
     * Methode appelée lors du clic sur le bouton "Modifier". Modifie le materiel
     */
    @FXML
    private void modifyMateriel() {
        try {
            super.hideError(errorText);
            if (materielNameUpdate.getText() != null) {
                materielFacade.updateMateriel(idMaterielSelected, materielNameUpdate.getText());
                super.goToMonEspace(updateMaterielButton);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }
}
