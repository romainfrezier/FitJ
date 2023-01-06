package com.fitj.controllers.programmes.programmesNutritions;

import com.fitj.classes.ProgrammeNutrition;
import com.fitj.exceptions.BadPageException;
import com.fitj.exceptions.UnselectedItemException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Optional;

/**
 * Controller de la liste des programmes nutrition
 * @see ControllerProgrammeNutrition
 * @author Paco Munarriz
 */
public class ControllerProgrammeNutritionList extends ControllerProgrammeNutrition {

    @FXML
    private Button addProgrammeNutritionButton;

    @FXML
    private Button detailProgrammeNutritionButton;
    @FXML
    private ListView<ProgrammeNutrition> listView;
    @FXML
    private Button updateProgrammeNutritionButton;
    @FXML
    private Button deleteProgrammeNutritionButton;
    @FXML
    private Text errorText;

    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        initializeProgrammeNutritionList();
    }

    /**
     * Methode permettant d'initialiser la liste des programmes nutrition
     */
    private void initializeProgrammeNutritionList() {
        try {
            List<ProgrammeNutrition> programmeNutritions = facadeProgrammeNutrition.getListeProgrammeNutrition();
            super.initializeProgrammeNutritionList(listView, programmeNutritions);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }


    /**
     * Méthode appelée lors du clic sur un programme nutrition de la liste
     */
    @FXML
    private void selectItemProgramme(){
        setObjectSelected(listView.getSelectionModel().getSelectedItem());
    }

    /**
     * Methode permettant de se rendre sur la page d'ajout d'une recette
     */
    @FXML
    private void goToAddProgrammeNutrition() {
        try {
            super.hideError(errorText);
            super.goToAddProgrammeNutrition(addProgrammeNutritionButton);
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de se rendre sur la page de modification d'un programme nutrition
     */
    @FXML
    private void goToUpdateProgrammeNutrition() {
        try {
            super.hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            super.goToUpdateProgrammeNutrition(updateProgrammeNutritionButton);
        } catch (BadPageException | UnselectedItemException e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de se rendre sur la page de modification d'un programme nutrition
     */
    @FXML
    private void goToDetailProgrammeNutrition() {
        try {
            super.hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            super.goToDetailProgrammeNutrition(detailProgrammeNutritionButton);
        } catch (BadPageException | UnselectedItemException e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de supprimer un programme nutrition
     */
    @FXML
    private void deleteProgrammeNutrition() {
        try {
            hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            showConfirmationDeleteProgrammeNutrition();
        } catch (UnselectedItemException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de verifier si un programme nutrition est selectionné
     * @throws UnselectedItemException si aucun programme nutrition n'est selectionné
     */
    private void checkSelected() throws UnselectedItemException {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Vous devez sélectionner un programme nutrition");
        }
    }

    /**
     * Méthode permettant d'afficher une fenêtre de confirmation de suppression d'un programme nutrition
     */
    private void showConfirmationDeleteProgrammeNutrition() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete programme nutrition");
        alert.setHeaderText("Vous êtes sûr de vouloir supprimer ce programme nutrition ?");
        alert.setContentText("Vous ne pourrez pas revenir en arrière");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK){
            try {
                facadeProgrammeNutrition.deleteProgrammeNutrition(getIdObjectSelected());
                listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
            } catch (Exception e) {
                super.displayError(errorText, e.getMessage());
            }
        }

    }

}
