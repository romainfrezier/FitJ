package com.fitj.controllers.seances;

import com.fitj.classes.Client;
import com.fitj.classes.Seance;
import com.fitj.exceptions.BadPageException;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.Facade;
import com.fitj.facades.FacadeSeance;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.List;
import java.util.Optional;

/**
 * Controller de la page list-mesSeances.fxml
 * @see ControllerSeance
 * @author Paul Merceur
 */
public class ControllerMesSeances extends ControllerSeance {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private ListView<Seance> listView;

    @FXML
    private Button addSeanceButton;

    @FXML
    private Button updateSeanceButton;

    @FXML
    private Button deleteSeanceButton;

    @FXML
    private Text errorText;
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        initializeSeanceList();
    }

    /**
     * Methode permettant d'initialiser la liste des seances
     */
    private void initializeSeanceList() {
        try {
            Client currentClient = Facade.currentClient;
            List<Seance> seances = seanceFacade.getAllSeancesFromCoach(currentClient.getId());
            listView.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Seance> call(ListView<Seance> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Seance item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                setText(item.toString());
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
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode appelée lors du clic sur le bouton "Ajouter une séance"
     */
    @FXML
    private void goToAddSeance() {
        super.hideError(errorText);
        try {
            super.goToAddSeance(addSeanceButton);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode appelée lors du clic sur le bouton "Modifier une séance"
     * @throws BadPageException si la page n'est pas correctement chargée
     */
    @FXML
    private void goToUpdateSeance() {
        super.hideError(errorText);
        try {
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            super.goToUpdateSeance(updateSeanceButton);
        } catch (BadPageException | UnselectedItemException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode appelée lors du clic sur le bouton "Supprimer une séance"
     */
    @FXML
    private void goToDeleteSeance() {
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
     * Methode permettant de verifier si une séance est selectionnée
     * @throws UnselectedItemException si aucune séance n'est selectionnée
     */
    private void checkSelected() throws UnselectedItemException {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Aucune séance n'est sélectionnée");
        }
    }

    /**
     * Methode permettant d'afficher la fenetre de confirmation de suppression
     */
    private void showDeleteConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Suppression d'une séance");
        alert.setContentText("Voulez-vous vraiment supprimer cette séance ?");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK) {
            FacadeSeance seanceFacade = FacadeSeance.getInstance();
            try {
                seanceFacade.deleteSeance(getIdObjectSelected());
                listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
            } catch (Exception e) {
                super.displayError(errorText, e.getMessage());
            }
        }
    }

}
