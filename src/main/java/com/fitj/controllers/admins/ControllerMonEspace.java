package com.fitj.controllers.admins;

import com.fitj.classes.Sport;
import com.fitj.controllers.sports.ControllerModifySport;
import com.fitj.exceptions.BadPageException;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.FacadeSport;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllerMonEspace extends ControllerAdmin {

    //Composants FXML-----------------------------------------------------------------------------------------------
    @FXML
    private Button addSportButton;
    @FXML
    private Button monCompte;
    @FXML
    private Button coachs;
    @FXML
    private Button monEspace;
    @FXML
    private Button shop;
    @FXML
    private Button clients;
    @FXML
    private Text adminName;
    @FXML
    private ProgressBar adminGrade;
    @FXML
    private ListView<Sport> listView;
    @FXML
    private TableView<Sport> sportList;
    @FXML
    private TableColumn<Sport, String> idCol;
    @FXML
    private TableColumn<Sport, String> nomCol;
    @FXML
    private Button updateSportButton;
    @FXML
    private Button deleteSportbutton;
    //Methodes-----------------------------------------------------------------------------------------------

    @FXML
    private void goToMonCompte() throws BadPageException {
        super.goToMonCompte(monCompte);
    }

    @FXML
    private void goToCoachs() throws BadPageException {
        super.goToCoachs(coachs);
    }


    @FXML
    private void goToMonEspace() throws BadPageException {
        super.goToMonEspace(monEspace);
    }


    @FXML
    private void goToShop() throws BadPageException {
        super.goToShop(shop);
    }

    @FXML
    private void goToClients() throws BadPageException {
        super.goToClients(clients);
    }

    @FXML
    private void goToAddSport() throws BadPageException {
        super.goToAddSport(addSportButton);
    }

    private void displayError(String message) {
    }

    @FXML
    private void initialize() {
        initializeSportList();
    }

    private void initializeSportList() {
        FacadeSport facadeSport = FacadeSport.getInstance();
        try {
            List<Sport> sports = facadeSport.getAllSports();
            listView.setCellFactory(new Callback<ListView<Sport>, ListCell<Sport>>() {
                @Override
                public ListCell<Sport> call(ListView<Sport> param) {
                    return new ListCell<Sport>() {
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
            for (Sport sport : sports) {
                listView.getItems().add(sport);
            }
        } catch (Exception e) {
            displayError(e.getMessage());
        }
    }

    @FXML
    private void goToUpdateSport() {
        try {
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            super.goToUpdateSport(updateSportButton);
        } catch (BadPageException | UnselectedItemException e) {
            displayError(e.getMessage());
        }
    }

    @FXML
    private void deleteSport() {
        try {
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            showConfirmationDeleteSport();
        } catch (UnselectedItemException e) {
            displayError(e.getMessage());
        }
    }

    private void checkSelected() throws UnselectedItemException {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Vous devez selectionner un sport");
        }
    }

    private void showConfirmationDeleteSport() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Sport");
        alert.setHeaderText("Vous êtes sûr de vouloir supprimer ce sport ?");
        alert.setContentText("Vous ne pourrez pas revenir en arrière");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK){
            FacadeSport facadeSport = FacadeSport.getInstance();
            try {
                facadeSport.deleteSport(getIdObjectSelected());
                listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
            } catch (Exception e) {
                displayError(e.getMessage());
            }
        }

    }
}
