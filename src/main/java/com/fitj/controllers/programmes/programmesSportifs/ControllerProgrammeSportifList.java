package com.fitj.controllers.programmes.programmesSportifs;

import com.fitj.classes.ProgrammeSportif;
import com.fitj.exceptions.BadPageException;
import com.fitj.exceptions.UnselectedItemException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Optional;

public class ControllerProgrammeSportifList extends ControllerProgrammeSportif {


    @FXML
    private Button addProgrammeSportifButton;

    @FXML
    private Button detailProgrammeSportifButton;
    @FXML
    private ListView<ProgrammeSportif> listView;
    @FXML
    private Button updateProgrammeSportifButton;
    @FXML
    private Button deleteProgrammeSportifButton;
    @FXML
    private Text errorText;

    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        initializeProgrammeSportifList();
    }

    /**
     * Methode permettant d'initialiser la liste des programmes sportifs
     */
    private void initializeProgrammeSportifList() {
        try {
            List<ProgrammeSportif> programmeSportifs = facadeProgrammeSportif.getListeProgrammeSportif();
            super.initializeProgrammeSportifList(listView, programmeSportifs);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }


    /**
     * Méthode appelée lors du clic sur un programme sportif de la liste
     */
    @FXML
    private void selectItemProgramme(){
        setObjectSelected(listView.getSelectionModel().getSelectedItem());
    }

    /**
     * Methode permettant de se rendre sur la page d'ajout d'une séance
     */
    @FXML
    private void goToAddProgrammeSportif() {
        try {
            super.hideError(errorText);
            super.goToAddProgrammeSportif(addProgrammeSportifButton);
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de se rendre sur la page de modification d'un programme sportif
     */
    @FXML
    private void goToUpdateProgrammeSportif() {
        try {
            super.hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            super.goToUpdateProgrammeSportif(updateProgrammeSportifButton);
        } catch (BadPageException | UnselectedItemException e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de se rendre sur la page de modification d'un programme sportif
     */
    @FXML
    private void goToDetailProgrammeSportif() {
        try {
            super.hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            super.goToDetailProgrammeSportif(detailProgrammeSportifButton);
        } catch (BadPageException | UnselectedItemException e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de supprimer un programme sportif
     */
    @FXML
    private void deleteProgrammeSportif() {
        try {
            hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            showConfirmationDeleteProgrammeSportif();
        } catch (UnselectedItemException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de verifier si un programme sportif est selectionné
     * @throws UnselectedItemException si aucun programme sportif n'est selectionné
     */
    private void checkSelected() throws UnselectedItemException {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Vous devez sélectionner un programme sportif");
        }
    }

    /**
     * Méthode permettant d'afficher une fenêtre de confirmation de suppression d'un programme sportif
     */
    private void showConfirmationDeleteProgrammeSportif() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete programme sportif");
        alert.setHeaderText("Vous êtes sûr de vouloir supprimer ce programme sportif ?");
        alert.setContentText("Vous ne pourrez pas revenir en arrière");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK){
            try {
                facadeProgrammeSportif.deleteProgrammeSportif(getIdObjectSelected());
                listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
            } catch (Exception e) {
                super.displayError(errorText, e.getMessage());
            }
        }

    }


}
