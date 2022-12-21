package com.fitj.controllers.sports;

import com.fitj.exceptions.BadPageException;
import com.fitj.facades.FacadeSport;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ControllerAddSport extends ControllerSport {

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
    private Button addSportButton;

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
    private void addSport() {
        FacadeSport facadeSport = FacadeSport.getInstance();
        try {
            facadeSport.createSport(sportName.getText());
            goToMonEspace();
        } catch (Exception e) {
            displayError(e.getMessage());
        }
    }

    private void displayError(String message) {
    }
}
