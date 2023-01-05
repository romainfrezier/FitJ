package com.fitj.controllers.recettes;

import com.fitj.classes.Aliment;
import com.fitj.classes.Recette;
import com.fitj.dao.factory.FactoryDAO;
import com.fitj.interfaces.Ingredient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller de la page de modification de la recette
 * @see ControllerRecette
 * @author Etienne Tillier
 */
public class ControllerModifyRecette extends ControllerRecette {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private TextField recetteName;
    @FXML
    private Button updateRecetteButton;
    @FXML
    private Text errorText;

    private Ingredient ingredientSelected = null;

    private Ingredient ingredientSelectedForDelete = null;

    @FXML
    private ListView<Ingredient> listViewIngredientRecette;

    @FXML
    private ListView<Aliment> listViewAliment;

    @FXML
    private ListView<Recette> listViewRecette;

    private Recette recette;

    private List<Ingredient> listeIngredients = new ArrayList<>();


    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        try {
            this.recette = recetteFacade.getRecetteById(getIdObjectSelected());
            this.recetteName.setText(this.recette.getNom());
            this.listeIngredients.addAll(recette.getIngredients());
        }
        catch (Exception e){
            super.displayError(errorText, e.getMessage());
        }
        setObjectSelected(null);
        initializeIngredientList();
        initializeAlimentList();
        initializeRecetteList();
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

    private void initializeAlimentList(){
        try {
            listViewAliment.getItems().clear();
            List<Aliment> aliments = FactoryDAO.getInstance().getDAOAliment().getAllAliments();
            super.initializeAlimentList(listViewAliment, aliments);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    private void initializeRecetteList() {
        try {
            listViewRecette.getItems().clear();
            List<Recette> listeRecettes = FactoryDAO.getInstance().getDAORecette().getAllRecettes();
            super.initializeRecetteList(listViewRecette, listeRecettes);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Ajoute un ingrédient à la recette pour sa mise à jour
     */
    @FXML
    private void addIngredient() {
        if (ingredientSelected != null && ingredientSelected instanceof Ingredient) {
            listeIngredients.add(ingredientSelected);
            ingredientSelected = null;
            initializeIngredientList();
        } else {
            super.displayError(errorText, "Aucun ingrédient sélectionné");
        }
    }

    /**
     * Supprime un ingrédient de la recette pour sa mise à jour
     */
    @FXML
    private void supprimerIngredient() {
        listeIngredients.remove(this.ingredientSelectedForDelete);
        this.ingredientSelectedForDelete = null;
        initializeIngredientList();
    }


    /**
     * Méthode appelée lors du clic sur le bouton "Ajouter". Ajoute la recette
     */
    @FXML
    private void updateRecette() {
        try {
            hideError(errorText);
            List<Ingredient> ingredientsEnMoin = recette.getIngredients().stream().filter(ingredient -> !listeIngredients.contains(ingredient)).toList();
            for (Ingredient ingredient : ingredientsEnMoin) {
                try {
                    recetteFacade.removeIngredientFromRecette(recette.getId(), ingredient);
                } catch (Exception e) {
                    e.printStackTrace();
                    displayError(errorText, e.getMessage());
                }
            }
            List<Ingredient> ingredientsEnPlus = listeIngredients.stream().filter(ingredient -> !recette.getIngredients().contains(ingredient)).toList();
            for (Ingredient ingredient : ingredientsEnPlus) {
                try {
                    recetteFacade.addIngredientToRecette(recette.getId(), ingredient);
                } catch (Exception e) {
                    e.printStackTrace();
                    displayError(errorText, e.getMessage());
                }
            }
            recetteFacade.updateRecette(recette.getId(), recetteName.getText());
            super.goToMonEspace(updateRecetteButton);
        } catch (Exception e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }

    public Ingredient getIngredientSelectedToDelete() {
        return ingredientSelected;
    }

    @FXML
    public void setIngredientSelectedToDelete() {
        this.ingredientSelectedForDelete = listViewIngredientRecette.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void setIngredientSelectedAliment() {
        this.ingredientSelected = listViewAliment.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void setIngredientSelectedRecette() {
        this.ingredientSelected = listViewRecette.getSelectionModel().getSelectedItem();
    }
}
