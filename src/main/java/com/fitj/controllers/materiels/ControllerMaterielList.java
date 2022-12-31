package com.fitj.controllers.materiels;

import com.fitj.classes.Materiel;
import com.fitj.exceptions.BadPageException;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.FacadeMateriel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Optional;

/**
 * Controller de la page materiel-list-view.fxml
 * @see ControllerMateriel
 * @author Paco Munarriz
 */
public class ControllerMaterielList extends ControllerMateriel {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private Button addMaterielButton;
    @FXML
    private ListView<Materiel> listView;
    @FXML
    private Button updateMaterielButton;
    @FXML
    private Button deleteMaterielbutton;
    @FXML
    private Text errorText;
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        initializeMaterielList();
    }

    /**
     * Methode permettant d'initialiser la liste des materiels
     */
    private void initializeMaterielList() {
        try {
            List<Materiel> materiels = materielFacade.getAllMateriels();
            super.initializeMaterielList(listView, materiels);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode appelée lors du clic sur un matériel de la liste
     */
    @FXML
    private void selectItem(){
        setObjectSelected(listView.getSelectionModel().getSelectedItem());
    }

    /**
     * Methode permettant de se rendre sur la page d'ajout d'un materiel
     */
    @FXML
    private void goToAddMateriel() {
        try {
            super.hideError(errorText);
            super.goToAddMateriel(addMaterielButton);
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de se rendre sur la page de modification d'un materiel
     */
    @FXML
    private void goToUpdateMateriel() {
        try {
            super.hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            super.goToUpdateMateriel(updateMaterielButton);
        } catch (BadPageException | UnselectedItemException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de supprimer un materiel
     */
    @FXML
    private void deleteMateriel() {
        try {
            hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            showConfirmationDeleteMateriel();
        } catch (UnselectedItemException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de verifier si un materiel est selectionné
     * @throws UnselectedItemException si aucun materiel n'est selectionné
     */
    private void checkSelected() throws UnselectedItemException {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Vous devez selectionner un materiel");
        }
    }

    /**
     * Méthode permettant d'afficher une fenêtre de confirmation de suppression d'un materiel
     */
    private void showConfirmationDeleteMateriel() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Materiel");
        alert.setHeaderText("Vous êtes sûr de vouloir supprimer ce materiel ?");
        alert.setContentText("Vous ne pourrez pas revenir en arrière");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK){
            FacadeMateriel facadeMateriel = FacadeMateriel.getInstance();
            try {
                facadeMateriel.deleteMateriel(getIdObjectSelected());
                listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
            } catch (Exception e) {
                super.displayError(errorText, e.getMessage());
            }
        }

    }
}
