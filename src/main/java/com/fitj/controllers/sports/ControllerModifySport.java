package com.fitj.controllers.sports;

import com.fitj.classes.Sport;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.FacadeSport;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ControllerModifySport extends ControllerSport {


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
    private TextField sportNameUpdate;
    @FXML
    private Button updateSportButton;

    private int idSportSelected;

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
    private void initialize() {
        Sport sport = null;
        idSportSelected = getIdObjectSelected();
        try {
            sport = sportFacade.getSportById(idSportSelected);
        } catch (Exception e) {
            displayError(e.getMessage());
        }
        if (sport != null) {
            sportNameUpdate.setText(sport.getNom());
        }
    }

    public int getIdSportSelected() {
        return idSportSelected;
    }

    public void setIdSportSelected(int idSportSelected) {
        this.idSportSelected = idSportSelected;
    }

    @FXML
    private void modifySport() {
        FacadeSport facadeSport = FacadeSport.getInstance();
        try {
            if (sportNameUpdate.getText() != null) {
                facadeSport.updateSport(idSportSelected, sportNameUpdate.getText());
                goToMonEspace();
            }
        } catch (Exception e) {
            displayError(e.getMessage());
        }
    }

    private void displayError(String message) {
    }
}
