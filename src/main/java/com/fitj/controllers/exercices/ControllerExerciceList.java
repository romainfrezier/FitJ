package com.fitj.controllers.exercices;

import com.fitj.classes.Exercice;
import com.fitj.exceptions.BadPageException;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.FacadeExercice;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;
import java.util.List;
import java.util.Optional;

/**
 * Controller de la page exercice-list-view.fxml
 * @see ControllerExercice
 * @author Paul Merceur
 */
public class ControllerExerciceList extends ControllerExercice {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private ListView<Exercice> listView;

    @FXML
    private Button addExerciceButton;

    @FXML
    private Button updateExerciceButton;

    @FXML
    private Button deleteExerciceButton;

    @FXML
    private Text errorText;
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        initializeExerciceList();
    }

    /**
     * Methode permettant d'initialiser la liste des exercices
     */
    private void initializeExerciceList() {
        try {
            List<Exercice> exercices = exerciceFacade.getAllExercices();
            listView.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Exercice> call(ListView<Exercice> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Exercice item, boolean empty) {
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
            for (Exercice exercice : exercices) {
                listView.getItems().add(exercice);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode appelée lors du clic sur le bouton "Ajouter un exercice"
     */
    @FXML
    private void goToAddExercice() {
        super.hideError(errorText);
        try {
        super.goToAddExercice(addExerciceButton);
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de se rendre sur la page de modification d'un exercice
     */
    @FXML
    void goToUpdateExercice() {
        super.hideError(errorText);
        try {
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            super.goToUpdateExercice(updateExerciceButton);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de se rendre sur la page de suppression d'un exercice
     */
    @FXML
    private void goToDeleteExercice() {
        super.hideError(errorText);
        try {
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            showDeleteConfirmation();
        } catch (UnselectedItemException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de verifier si un materiel est selectionné
     * @throws UnselectedItemException si aucun materiel n'est selectionné
     */
    private void checkSelected() throws UnselectedItemException {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Aucun exercice n'est sélectionné");
        }
    }

    /**
     * Methode permettant d'afficher la fenetre de confirmation de suppression
     */
    private void showDeleteConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Suppression d'un exercice");
        alert.setContentText("Voulez-vous vraiment supprimer cet exercice ?");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK) {
            FacadeExercice facadeExercice = FacadeExercice.getInstance();
            try {
                facadeExercice.deleteExercice(getIdObjectSelected());
                listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
            } catch (Exception e) {
                super.displayError(errorText, e.getMessage());
            }
        }
    }

}
