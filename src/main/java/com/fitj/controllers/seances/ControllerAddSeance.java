package com.fitj.controllers.seances;

import com.fitj.classes.Client;
import com.fitj.classes.Exercice;
import com.fitj.classes.Sport;
import com.fitj.facades.Facade;
import com.fitj.facades.FacadeExercice;
import com.fitj.facades.FacadeSport;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller de la page d'ajout d'une séance
 * @see ControllerSeance
 * @author Paul Merceur
 */
public class ControllerAddSeance extends ControllerSeance {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private TextField seanceName;
    @FXML
    private TextArea seanceDescription;
    @FXML
    private MenuButton sportDropdown;
    @FXML
    private ListView<Exercice> listExercicesDispo;
    @FXML
    private Button addExerciceButton;

    // TODO : Liste des exercices de la séance
    // TODO : Gestion des reps/sets des exercices

    @FXML
    private Button addSeanceButton;
    @FXML
    private Text errorText;
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        seanceName.setText("Nom de la séance");
        getAvailableExercices();
        getSports();
    }

    /**
     * Méthode permettant d'importer les exercices disponibles dans la liste
     */
    private void getAvailableExercices() {
        try {
            FacadeExercice exerciceFacade = FacadeExercice.getInstance();
            List<Exercice> exercices = exerciceFacade.getAllExercices();
            listExercicesDispo.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Exercice> call(ListView<Exercice> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Exercice item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null || item.getNom() == null) {
                                setText(null);
                            } else {
                                setText(item.getNom());
                            }
                        }
                    };
                }
            });
            for (Exercice exercice : exercices) {
                listExercicesDispo.getItems().add(exercice);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode permettant d'importer les sports disponibles dans le menu déroulant
     */
    private void getSports() {
        try {
            FacadeSport sportFacade = FacadeSport.getInstance();
            List<Sport> sports = sportFacade.getAllSports();
            for (Sport sport : sports) {
                MenuItem item = new MenuItem(sport.getNom());
                item.setOnAction(event -> sportDropdown.setText(sport.getNom()));
                sportDropdown.getItems().add(item);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode permettant d'ajouter un exercice à la séance
     */
    @FXML
    private void addExercice() {
        try {
            Exercice exercice = listExercicesDispo.getSelectionModel().getSelectedItem();
            // TODO : Ajouter l'exercice à la séance
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode appelée lors du clic sur le bouton "Ajouter la séance"
     */
    @FXML
    private void addSeance() {
        try {
            hideError(errorText);
            // TODO : Ajouter la séance
            super.goToMonEspace(addSeanceButton);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }



}
