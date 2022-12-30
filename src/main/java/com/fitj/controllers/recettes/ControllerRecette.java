package com.fitj.controllers.recettes;

import com.fitj.classes.Admin;
import com.fitj.classes.Aliment;
import com.fitj.classes.Recette;
import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.Facade;
import com.fitj.facades.FacadeRecette;
import com.fitj.interfaces.Ingredient;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.List;

/**
 * Controller générique des pages recettes
 *
 * @author Etienne Tillier
 */
public abstract class ControllerRecette extends Controller {

    /**
     * Facade pour les recettes
     */
    final FacadeRecette recetteFacade = FacadeRecette.getInstance();

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages accessibles aux admins
     */
    private final String admin = "admins/";

    private final String coach = "coachs/";

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages relatives aux admins
     */
    private final String recette = "recettes/";

    /**
     * Methode permettant de se rendre sur la page mon espace
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToMonEspace(Control controlEl) throws BadPageException {
        if (Facade.currentClient instanceof Admin) {
            goToPage(controlEl, admin + "monEspace-admin.fxml", "Mon Compte");
        } else {
            goToPage(controlEl,  coach + "monEspace-coach.fxml", "Mon Compte");
        }
    }

    /**
     * Methode permettant de se rendre sur la page d'ajout d'une recette
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToAddRecette(Control controlEl) throws BadPageException {
        goToPage(controlEl, recette + "create-recette.fxml", "Création d'une recette");
    }

    /**
     * Methode permettant de se rendre sur la page de modification d'une recette
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToUpdateRecette(Control controlEl) throws BadPageException {
        goToPage(controlEl, recette + "update-recette.fxml", "Modification d'une recette");
    }

    /**
     * Methode permettant de se rendre sur la page de détail d'une recette
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToDetailRecette(Control controlEl) throws BadPageException {
        goToPage(controlEl, recette + "detail-recette.fxml", "Détail d'une recette");
    }

    void initializeRecetteList(ListView<Recette> listView, List<Recette> recettes) {
        super.initializeList(listView, recettes, new Callback<ListView<Recette>, ListCell<Recette>>() {
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
    }

    void initializeAlimentList(ListView<Aliment> listView, List<Aliment> aliments) {
        super.initializeList(listView, aliments, new Callback<ListView<Aliment>, ListCell<Aliment>>() {
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
    }

    void initializeIngredientList(ListView<Ingredient> listView, List<Ingredient> ingredients) {
        super.initializeList(listView, ingredients, new Callback<ListView<Ingredient>, ListCell<Ingredient>>() {
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
    }
}
