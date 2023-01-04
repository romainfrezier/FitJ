package com.fitj.controllers.seances;

import com.fitj.classes.Seance;
import com.fitj.exceptions.BadPageException;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.List;
import java.util.Optional;

/**
 * Controller de la page de liste des séances d'un coach
 * @see ControllerSeance
 * @author Romain Frezier
 */
public class ControllerMesSeanceList extends ControllerSeance{


    @FXML
    private Button deleteSeanceButton;
    @FXML
    private Button addSeanceButton;

    @FXML
    private Button detailSeanceButton;
    @FXML
    private ListView<Seance> listView;
    @FXML
    private Button updateSeanceButton;
    @FXML
    private Button deleteSeancebutton;
    @FXML
    private Text errorText;

    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        initializeAlimentList();
    }

    /**
     * Methode permettant d'initialiser la liste des séances
     */
    private void initializeAlimentList() {
        try {
            List<Seance> seances = facadeSeance.getAllSeancesFromCoach(Facade.currentClient.getId());
            listView.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Seance> call(ListView<Seance> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Seance item, boolean empty) {
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
            for (Seance seance : seances) {
                listView.getItems().add(seance);
            }
        } catch (Exception e) {
            e.printStackTrace();
            super.displayError(errorText, "Erreur lors de la récupération des séances");
        }
    }


    /**
     * Méthode appelée lors du clic sur une séance de la liste
     */
    @FXML
    private void selectItem(){
        setObjectSelected(listView.getSelectionModel().getSelectedItem());
    }

    /**
     * Methode permettant de se rendre sur la page d'ajout d'une séance
     */
    @FXML
    private void goToAddSeance() {
        try {
            super.hideError(errorText);
            super.goToAddSeance(addSeanceButton);
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de se rendre sur la page de modification d'une séance
     */
    @FXML
    private void goToUpdateSeance() {
        try {
            super.hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            super.goToUpdateSeance(updateSeanceButton);
        } catch (BadPageException | UnselectedItemException e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de se rendre sur la page de modification d'une séance
     */
    @FXML
    private void goToDetailSeance() {
        try {
            super.hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            super.goToDetailSeance(detailSeanceButton);
        } catch (BadPageException | UnselectedItemException e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de supprimer une séance
     */
    @FXML
    private void deleteSeance() {
        try {
            hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            showConfirmationDeleteRecette();
        } catch (UnselectedItemException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de verifier si une séance est selectionnée
     * @throws UnselectedItemException si aucune séance n'est selectionnée
     */
    private void checkSelected() throws UnselectedItemException {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Vous devez sélectionner une séance");
        }
    }

    /**
     * Méthode permettant d'afficher une fenêtre de confirmation de suppression d'une séance
     */
    private void showConfirmationDeleteRecette() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Séance");
        alert.setHeaderText("Vous êtes sûr de vouloir supprimer cette séance ?");
        alert.setContentText("Vous ne pourrez pas revenir en arrière");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK){
            try {
                facadeSeance.deleteSeance(getIdObjectSelected());
                listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
            } catch (Exception e) {
                super.displayError(errorText, e.getMessage());
            }
        }

    }
}
