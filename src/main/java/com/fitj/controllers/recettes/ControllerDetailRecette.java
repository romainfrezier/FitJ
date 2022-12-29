package com.fitj.controllers.recettes;

import com.fitj.classes.Admin;
import com.fitj.classes.Recette;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.Facade;
import com.fitj.interfaces.Ingredient;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

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
    private VBox headerAdmin;

    @FXML
    private VBox headerCoach;

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
        if (Facade.currentClient instanceof Admin) {
            headerAdmin.setVisible(true);
        } else {
            headerCoach.setVisible(true);
        }
        detailRecetteButton.setVisible(false);
        try {
            this.recette = recetteFacade.getRecetteById(getIdObjectSelected());
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
     * Methode permettant d'initialiser la liste des ingrédients présents dans la recette
     */
    private void initializeIngredientList() {
        listViewIngredientRecette.getItems().clear();
        try {
            listViewIngredientRecette.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Ingredient> call(ListView<Ingredient> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Ingredient item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                setText(item.getNom());
                            } else {
                                setText("");
                            }
                        }
                    };
                }
            });
            for (Ingredient ingredient : listeIngredients) {
                listViewIngredientRecette.getItems().add(ingredient);
            }
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
