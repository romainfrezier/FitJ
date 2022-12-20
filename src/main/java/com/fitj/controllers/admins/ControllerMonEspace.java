package com.fitj.controllers.admins;

import com.fitj.classes.Sport;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.FacadeSport;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
    private TextField sportName;
    @FXML
    private Text adminName;
    @FXML
    private ProgressBar adminGrade;
    @FXML
    private TableView<Sport> sportList;

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

    @FXML
    private void addSport(ActionEvent actionEvent) {
    }

    @FXML
    private void initialize() {
        FacadeSport facadeSport = FacadeSport.getInstance();
        try {
            ObservableList<Sport> sports = facadeSport.getAllSports();
            sportList.setItems(sports);
        } catch (Exception e) {
            displayError(e.getMessage());
        }

    }

    private void displayError(String message) {
    }
}
