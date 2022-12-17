package com.fitj.controllers.admins;

import com.fitj.exceptions.BadPageException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ControllerMonEspace extends ControllerAdmin {
    //Composants FXML-----------------------------------------------------------------------------------------------

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
}
