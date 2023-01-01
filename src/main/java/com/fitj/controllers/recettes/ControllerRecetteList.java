package com.fitj.controllers.recettes;

import com.fitj.classes.Recette;
import com.fitj.exceptions.BadPageException;
import com.fitj.exceptions.UnselectedItemException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Optional;

/**
 * Controller de la page list-recette-view.fxml
 * @see ControllerRecette
 * @author Etienne Tillier
 */

public class ControllerRecetteList extends ControllerRecette {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private Button addRecetteButton;

    @FXML
    private Button detailRecetteButton;
    @FXML
    private ListView<Recette> listView;
    @FXML
    private Button updateRecetteButton;
    @FXML
    private Button deleteRecetteButton;
    @FXML
    private Text errorText;

    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        initializeRecetteList();
    }

    /**
     * Methode permettant d'initialiser la liste des recettes
     */
    private void initializeRecetteList() {
        try {
            List<Recette> recettes = recetteFacade.getListeRecettes();
            super.initializeRecetteList(listView, recettes);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }


    /**
     * Méthode appelée lors du clic sur une recette de la liste
     */
    @FXML
    private void selectItem(){
        setObjectSelected(listView.getSelectionModel().getSelectedItem());
    }

    /**
     * Methode permettant de se rendre sur la page d'ajout d'une recette
     */
    @FXML
    private void goToAddRecette() {
        try {
            super.hideError(errorText);
            super.goToAddRecette(addRecetteButton);
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de se rendre sur la page de modification d'une recette
     */
    @FXML
    private void goToUpdateRecette() {
        try {
            super.hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            super.goToUpdateRecette(updateRecetteButton);
        } catch (BadPageException | UnselectedItemException e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de se rendre sur la page de modification d'une recette
     */
    @FXML
    private void goToDetailRecette() {
        try {
            super.hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            super.goToDetailRecette(detailRecetteButton);
        } catch (BadPageException | UnselectedItemException e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de supprimer une recette
     */
    @FXML
    private void deleteRecette() {
        try {
            hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            showConfirmationDeleteRecette();
        } catch (UnselectedItemException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de verifier si une recette est selectionnée
     * @throws UnselectedItemException si aucune recette n'est selectionnée
     */
    private void checkSelected() throws UnselectedItemException {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Vous devez sélectionner une recette");
        }
    }

    /**
     * Méthode permettant d'afficher une fenêtre de confirmation de suppression d'une recette
     */
    private void showConfirmationDeleteRecette() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Recette");
        alert.setHeaderText("Vous êtes sûr de vouloir supprimer cette recette ?");
        alert.setContentText("Vous ne pourrez pas revenir en arrière");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK){
            try {
                recetteFacade.deleteRecette(getIdObjectSelected());
                listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
            } catch (Exception e) {
                super.displayError(errorText, e.getMessage());
            }
        }

    }
}
