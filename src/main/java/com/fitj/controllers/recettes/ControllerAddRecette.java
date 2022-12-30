package com.fitj.controllers.recettes;

import com.fitj.classes.Admin;
import com.fitj.classes.Aliment;
import com.fitj.classes.Coach;
import com.fitj.classes.Recette;
import com.fitj.dao.factory.FactoryDAO;
import com.fitj.enums.ProgrammeType;
import com.fitj.facades.Facade;
import com.fitj.interfaces.Ingredient;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller de la page d'ajout d'une recette
 * @see ControllerRecette
 * @author Etienne Tillier
 */
public class ControllerAddRecette extends ControllerRecette {

    @FXML
    private TextField recetteName;
    @FXML
    private Button addRecetteButton;
    @FXML
    private Text errorText;

    private Ingredient ingredientSelected = null;

    private Ingredient ingredientSelectedForDelete = null;

    @FXML
    private VBox headerAdmin;

    @FXML
    private VBox headerCoach;


    @FXML
    private ListView<Ingredient> listViewIngredientRecette;

    @FXML
    private ListView<Aliment> listViewAliment;

    @FXML
    private ListView<Recette> listViewRecette;

    private List<Ingredient> listeIngredients = new ArrayList<>();


    // ---------------------------------------------------------------------------------------------------------------

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
        setObjectSelected(null);
        initializeAlimentList();
        initializeRecetteList();
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

    private void initializeAlimentList(){
        try {
            listViewAliment.getItems().clear();
            List<Aliment> aliments = FactoryDAO.getInstance().getDAOAliment().getAllAliments();
            listViewAliment.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Aliment> call(ListView<Aliment> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Aliment item, boolean empty) {
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
            for (Aliment aliment : aliments) {
                listViewAliment.getItems().add(aliment);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    private void initializeRecetteList() {
        try {
            listViewRecette.getItems().clear();
            List<Recette> listeRecettes = FactoryDAO.getInstance().getDAORecette().getAllRecettes();
            listViewRecette.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Recette> call(ListView<Recette> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Recette item, boolean empty) {
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
            for (Recette recette : listeRecettes) {
                listViewRecette.getItems().add(recette);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Ajoute un ingrédient à la recette pour sa création
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
     * Supprime un ingrédient de la recette pour sa création
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
    private void addRecette() {
        try {
            hideError(errorText);
            recetteFacade.createRecette(recetteName.getText(), listeIngredients, (Coach) recetteFacade.currentClient);
            super.goToMonEspace(addRecetteButton);
        } catch (Exception e) {
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
