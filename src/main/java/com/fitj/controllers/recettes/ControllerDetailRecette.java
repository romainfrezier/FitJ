package com.fitj.controllers.recettes;

import com.fitj.classes.Admin;
import com.fitj.classes.Recette;
import com.fitj.facades.Facade;
import com.fitj.interfaces.Ingredient;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller de la page de détail d'une recette
 * @see ControllerRecette
 * @author Etienne Tillier
 */
public class ControllerDetailRecette extends ControllerRecette{

    @FXML
    private Button detailRecetteButton;

    @FXML
    private Button deleteRecetteButton;

    @FXML
    private Button updateRecetteButton;

    @FXML
    private ListView<Ingredient> listViewIngredientRecette;
    @FXML
    private Text nomRecette;

    @FXML
    private Text errorText;

    private List<Ingredient> listeIngredients = new ArrayList<>();

    private Recette recette;

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        detailRecetteButton.setVisible(false);
        try {
            this.recette = recetteFacade.getRecetteById(getIdObjectSelected());
            checkCurrentCoach();
            this.listeIngredients.addAll(recette.getIngredients());
            this.nomRecette.setText(this.recette.getNom());

        }
        catch (Exception e){
            super.displayError(errorText, e.getMessage());
        }
        setObjectSelected(null);
        initializeIngredientList();

    }

    /**
     *Vérifie si le client courant est un coach ou un admin
     *Si le client courant n'est pas un admin et que le coach de la recette n'est pas le client courant, alors les boutons de modification et de suppression de la recette sont cachés
     */
    public void checkCurrentCoach(){
        if (!(Facade.currentClient instanceof Admin) && this.recette.getCoach().getId() != Facade.currentClient.getId()){
            updateRecetteButton.setVisible(false);
            deleteRecetteButton.setVisible(false);
        }
    }

    /**
     * Methode permettant d'initialiser la liste des ingrédients présents dans la recette
     */
    private void initializeIngredientList() {
        listViewIngredientRecette.getItems().clear();
        try {
            super.initializeIngredientList(listViewIngredientRecette, listeIngredients);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode permettant de supprimer la recette actuelle
     */
    @FXML
    private void deleteRecette(){
        try {
            showConfirmationDeleteRecette();
        }
        catch (Exception e){
            super.displayError(errorText, e.getMessage());
        }
    }


    /**
     * Permet d'aller sur la page de modification de la recette actuelle
     */
    @FXML
    private void updateRecette() {
        try {
            setObjectSelected(this.recette.getId());
            this.goToUpdateRecette(updateRecetteButton);
        }
        catch (Exception e){
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Permet d'aller sur la page de détail de la recette cliqué dans la liste des ingrédients
     */
    @FXML
    private void goToDetailRecette() {
        try {
            setObjectSelected(this.recette.getId());
            this.goToDetailRecette(detailRecetteButton);
        }
        catch (Exception e){
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Permet d'afficher le bouton détail de la recette que lorsqu'il s'agit d'une recette de sélectionnée dans la liste
     */
    @FXML
    private void handlerViewIngredient(){
        if (listViewIngredientRecette.getSelectionModel().getSelectedItem() instanceof Recette){
            setIdObjectSelected(listViewIngredientRecette.getSelectionModel().getSelectedItem().getId());
            this.detailRecetteButton.setVisible(true);
        }
        else {
            this.detailRecetteButton.setVisible(false);
        }
    }



    /**
     * Méthode permettant d'afficher une fenêtre de confirmation de suppression d'une recette
     */
    private void showConfirmationDeleteRecette() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Recette");
        alert.setHeaderText("Vous êtes sûr de vouloir supprimer cette recette " + recette.getNom() + " ?");
        alert.setContentText("Vous ne pourrez pas revenir en arrière");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK){
            try {
                recetteFacade.deleteRecette(recette.getId());
                setObjectSelected(null);
                this.goToMonEspace(deleteRecetteButton);
            } catch (Exception e) {
                super.displayError(errorText, e.getMessage());
            }
        }

    }


}
