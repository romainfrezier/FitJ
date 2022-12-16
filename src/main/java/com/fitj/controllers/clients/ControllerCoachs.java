package com.fitj.controllers.clients;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ControllerCoachs extends ControllerClient {
    //Composants FXML-----------------------------------------------------------------------------------------------

    @FXML
    private Button monCompte;
    @FXML
    private Button coachs;
    @FXML
    private Button monEspace;
    @FXML
    private Button shop;

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

}
