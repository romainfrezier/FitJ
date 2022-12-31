package com.fitj.controllers.seances;

import com.fitj.classes.Client;
import com.fitj.classes.Exercice;
import com.fitj.classes.Seance;
import com.fitj.facades.Facade;
import com.fitj.facades.FacadeExercice;
import com.fitj.facades.FacadeSeance;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller de la page de modification d'une séance
 */
public class ControllerUpdateSeance extends ControllerSeance {

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
    private Button updateSeanceButton;
    @FXML
    private Text errorText;
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        getAvailableExercices();
        getChosenExercices();
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
     * Méthode permettant d'importer les exercices choisis dans la liste
     */
    private void getChosenExercices() {
        try {
            FacadeSeance seanceFacade = FacadeSeance.getInstance();
            Seance seance = seanceFacade.getSeance(getIdObjectSelected());
            // TODO : Ajouter les exercices de la séance dans la liste
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode appelée lors du clic sur le bouton "Ajouter un exercice"
     */
    @FXML
    private void addExercice() {
        try {
            Exercice exercice = listExercicesDispo.getSelectionModel().getSelectedItem();
            // TODO : Ajouter l'exercice à la liste des exercices de la séance
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode appelée lors du clic sur le bouton "Modifier la séance"
     */
    @FXML
    private void updateSeance() {
        try {
            super.hideError(errorText);
            // TODO : Récupérer les informations de la séance
            // TODO : Modifier la séance
            super.goToMonEspace(updateSeanceButton);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

}
