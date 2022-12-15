package com.fitj.controllers.coachs;

import com.fitj.controllers.clients.ControllerClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ControllerCoachs extends ControllerCoach {
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
    private Button mesClients;

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

    public void goToMesClients() throws IOException {
        super.goToMesClients(mesClients);
    }

}
