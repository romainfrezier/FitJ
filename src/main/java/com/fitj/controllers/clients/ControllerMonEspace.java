package com.fitj.controllers.clients;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ControllerMonEspace extends ControllerClient {
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

    public void goToMonCompte() throws IOException {
        super.goToMonCompte(monCompte);
    }

    public void goToCoachs() throws IOException {
        super.goToCoachs(coachs);
    }


    public void goToMonEspace() throws IOException {
        super.goToMonEspace(monEspace);
    }


    public void goToShop() throws IOException {
        super.goToShop(shop);
    }

}
