package com.fitj.controllers.sports;

import com.fitj.classes.Sport;
import com.fitj.exceptions.BadPageException;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.FacadeSport;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;
import java.util.List;
import java.util.Optional;

/**
 * Controller de la page sport-list-view.fxml
 * @see ControllerSport
 * @author Romain Frezier
 */
public class ControllerSportList extends ControllerSport {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private Button addSportButton;
    @FXML
    private ListView<Sport> listView;
    @FXML
    private Button updateSportButton;
    @FXML
    private Button deleteSportbutton;
    @FXML
    private Text errorText;
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        initializeSportList();
    }

    /**
     * Methode permettant d'initialiser la liste des sports
     */
    private void initializeSportList() {
        try {
            List<Sport> sports = sportFacade.getAllSports();
            super.initializeList(listView, sports, new Callback<ListView<Sport>, ListCell<Sport>>() {
                @Override
                public ListCell<Sport> call(ListView<Sport> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Sport item, boolean empty) {
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
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode appelée lors du clic sur un sport de la liste
     */
    @FXML
    private void selectItemSport(){
        setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
    }

    /**
     * Methode permettant de se rendre sur la page d'ajout d'un sport
     */
    @FXML
    private void goToAddSport() {
        try {
            super.hideError(errorText);
            super.goToAddSport(addSportButton);
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de se rendre sur la page de modification d'un sport
     */
    @FXML
    private void goToUpdateSport() {
        try {
            super.hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            super.goToUpdateSport(updateSportButton);
        } catch (BadPageException | UnselectedItemException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de supprimer un sport
     */
    @FXML
    private void deleteSport() {
        try {
            hideError(errorText);
            checkSelected();
            showConfirmationDeleteSport();
        } catch (UnselectedItemException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de verifier si un sport est selectionné
     * @throws UnselectedItemException si aucun sport n'est selectionné
     */
    private void checkSelected() throws UnselectedItemException {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Vous devez selectionner un sport");
        }
    }

    /**
     * Méthode permettant d'afficher une fenêtre de confirmation de suppression d'un sport
     */
    private void showConfirmationDeleteSport() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Sport");
        alert.setHeaderText("Vous êtes sûr de vouloir supprimer ce sport ?");
        alert.setContentText("Vous ne pourrez pas revenir en arrière");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK){
            FacadeSport facadeSport = FacadeSport.getInstance();
            try {
                facadeSport.deleteSport(getIdObjectSelected());
                listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
            } catch (Exception e) {
                super.displayError(errorText, e.getMessage());
            }
        }

    }
}
