package com.fitj.controllers.seances;

import com.fitj.classes.Exercice;
import com.fitj.classes.Seance;
import com.fitj.facades.FacadeSeance;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.util.Callback;
import kotlin.Triple;

import java.util.List;

/**
 * Controller de la page de visualisation d'une séance
 * @see ControllerSeance
 * @author Paul Merceur
 */
public class ControllerViewSeance extends ControllerSeance {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private Text seanceName;
    @FXML
    private Text seanceDescription;
    @FXML
    private Text seanceSport;
    @FXML
    private ListView<Exercice> exercicesList;
    @FXML
    private Text exerciceName;
    @FXML
    private Text exerciceDescription;
    @FXML
    private Button goBackButton;
    @FXML
    private Text errorText;
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        getSeance();
        exerciceName.setVisible(false);
        exerciceDescription.setVisible(false);
    }

    /**
     * Méthode permettant d'importer les attributs de la séance
     */
    private void getSeance() {
        try {
            FacadeSeance seanceFacade = FacadeSeance.getInstance();
            Seance seance = seanceFacade.getSeance(getIdObjectSelected());
            seanceName.setText(seance.getName());
            seanceDescription.setText(seance.getDescription());
            seanceSport.setText(seance.getSport().getName());
            List<Triple<Exercice, Integer, Integer>> exercices = seance.getListeExercice();
            // Add to the listView each exercice, with the number of sets and reps
            exercicesList.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Exercice> call(ListView<Exercice> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Exercice item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                setText(item.getName() + " - " + exercices.get(getIndex()).getSecond() + " x " + exercices.get(getIndex()).getThird());
                            }
                        }
                    };
                }
            });
            for (Triple<Exercice, Integer, Integer> exercice : exercices) {
                exercicesList.getItems().add(exercice.getFirst());
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode permettant d'afficher les détails d'un exercice
     */
    @FXML
    private void displayExercice() {
        Exercice exercice = exercicesList.getSelectionModel().getSelectedItem();
        if (exercice != null) {
            exerciceName.setText(exercice.getName());
            exerciceDescription.setText(exercice.getDescription());
            exerciceName.setVisible(true);
            exerciceDescription.setVisible(true);
        }
    }
}
