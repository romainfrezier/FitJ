package com.fitj.controllers.admins;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ControllerCoachs extends ControllerAdmin {
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
    private void goToMonCompte() throws IOException {
        super.goToMonCompte(monCompte);
    }

    @FXML
    private void goToCoachs() throws IOException {
        super.goToCoachs(coachs);
    }


    @FXML
    private void goToMonEspace() throws IOException {
        super.goToMonEspace(monEspace);
    }


    @FXML
    private void goToShop() throws IOException {
        super.goToShop(shop);
    }

    @FXML
    private void goToClients() throws IOException {
        super.goToClients(clients);
    }

}
