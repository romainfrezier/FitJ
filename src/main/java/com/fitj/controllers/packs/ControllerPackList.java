package com.fitj.controllers.packs;

import com.fitj.classes.Pack;
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

public class ControllerPackList extends ControllerPack{

    @FXML
    private Button addPackButton;

    @FXML
    private Button detailPackButton;
    @FXML
    private ListView<Pack> listView;
    @FXML
    private Button updatePackButton;
    @FXML
    private Button deletePackButton;
    @FXML
    private Text errorText;

    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        initializePackList();
    }

    /**
     * Methode permettant d'initialiser la liste des packs
     */
    private void initializePackList() {
        try {
            List<Pack> packs = facadePack.getListePack();
            super.initializePackList(listView, packs);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }


    /**
     * Méthode appelée lors du clic sur un pack de la liste
     */
    @FXML
    private void selectItemPack(){
        setObjectSelected(listView.getSelectionModel().getSelectedItem());
    }

    /**
     * Methode permettant de se rendre sur la page d'ajout d'un pack
     */
    @FXML
    private void goToAddPack() {
        try {
            super.hideError(errorText);
            super.goToAddPack(addPackButton);
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de se rendre sur la page de modification d'un pack
     */
    @FXML
    private void goToUpdatePack() {
        try {
            super.hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            super.goToUpdatePack(updatePackButton);
        } catch (BadPageException | UnselectedItemException e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de se rendre sur la page de détail d'un pack
     */
    @FXML
    private void goToDetailPack() {
        try {
            super.hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            super.goToDetailPack(detailPackButton);
        } catch (BadPageException | UnselectedItemException e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de supprimer un pack
     */
    @FXML
    private void deletePack() {
        try {
            hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            showConfirmationDeletePack();
        } catch (UnselectedItemException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de verifier si un pack est selectionné
     * @throws UnselectedItemException si aucun pack n'est selectionné
     */
    private void checkSelected() throws UnselectedItemException {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Vous devez sélectionner un pack");
        }
    }

    /**
     * Méthode permettant d'afficher une fenêtre de confirmation de suppression d'un pack
     */
    private void showConfirmationDeletePack() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete pack");
        alert.setHeaderText("Vous êtes sûr de vouloir supprimer ce pack ?");
        alert.setContentText("Vous ne pourrez pas revenir en arrière");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK){
            try {
                facadePack.deletePack(getIdObjectSelected());
                listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
            } catch (Exception e) {
                super.displayError(errorText, e.getMessage());
            }
        }

    }
}
