package com.fitj.controllers.programmes.programmesNutritions;

import com.fitj.classes.Admin;
import com.fitj.classes.ProgrammeNutrition;
import com.fitj.classes.Recette;
import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.Facade;
import com.fitj.facades.FacadeProgrammeNutrition;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.List;

/**
 * Controller pour les ves vues des programmes nutrition
 * @author Etienne Tillier
 */
public abstract class ControllerProgrammeNutrition extends Controller {
    /**
     * Facade pour les programmes nutritions
     */
    final FacadeProgrammeNutrition facadeProgrammeNutrition = FacadeProgrammeNutrition.getInstance();

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages accessibles aux admins
     */
    private final String admin = "admins/";

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages relatives aux coachs
     */
    private final String coach = "coachs/";

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages relatives aux admins
     */
    private final String programmeNutrition = "produits/programmes/programmesNutritions/";

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
     * Methode permettant de se rendre sur la page d'ajout d'un programme nutrition
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToAddProgrammeNutrition(Control controlEl) throws BadPageException {
        goToPage(controlEl, programmeNutrition + "create-programmeNutrition.fxml", "Création d'un programme nutrition");
    }


    /**
     * Methode permettant de se rendre sur la page de modification d'un programme nutrition
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToUpdateProgrammeNutrition(Control controlEl) throws BadPageException {
        goToPage(controlEl, programmeNutrition + "update-programmeNutrition.fxml", "Modification d'un programme nutrition");
    }

    /**
     * Methode permettant de se rendre sur la page de détail d'un programme nutrition
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToDetailProgrammeNutrition(Control controlEl) throws BadPageException {
        goToPage(controlEl, programmeNutrition + "detail-programmeNutrition.fxml", "Détail d'un programme nutrition");
    }

    /**
     * Methode permettant de se rendre sur la page de détail d'une recette
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToDetailRecette(Control controlEl) throws BadPageException {
        goToPage(controlEl, "recettes/" + "detail-recette.fxml", "Détail d'une recette");
    }

    /**
     * Methode pour initialiser la liste des programmes nutrition
     */
    void initializeProgrammeNutritionList(ListView<ProgrammeNutrition> listView, List<ProgrammeNutrition> items) {
        super.initializeList(listView, items, new Callback<>() {
            @Override
            public ListCell<ProgrammeNutrition> call(ListView<ProgrammeNutrition> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(ProgrammeNutrition item, boolean empty) {
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

    /**
     * Methode pour initialiser la liste des recettes
     */
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
}
