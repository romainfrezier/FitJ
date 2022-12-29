package com.fitj.controllers.recettes;

import com.fitj.classes.Aliment;
import com.fitj.classes.Coach;
import com.fitj.classes.Recette;
import com.fitj.dao.factory.FactoryDAO;
import com.fitj.interfaces.IsIngredient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller de la page d'ajout d'un aliment
 * @see ControllerRecette
 * @author Paco Munarriz
 */
public class ControllerAddRecette extends ControllerRecette {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private TextField recetteName;
    @FXML
    private Button addRecetteButton;
    @FXML
    private Text errorText;

    private IsIngredient ingredientSelected = null;

    private IsIngredient ingredientSelectedForDelete = null;


    @FXML
    private ListView<IsIngredient> listViewIngredientRecette;

    @FXML
    private ListView<Aliment> listViewAliment;

    @FXML
    private ListView<Recette> listViewRecette;

    private List<IsIngredient> listeIngredients = new ArrayList<>();


    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
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
                public ListCell<IsIngredient> call(ListView<IsIngredient> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(IsIngredient item, boolean empty) {
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
            for (IsIngredient ingredient : listeIngredients) {
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
        if (ingredientSelected != null && ingredientSelected instanceof IsIngredient) {
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

    public IsIngredient getIngredientSelectedToDelete() {
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
