package com.fitj.controllers.clients;

import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Controller pour la page d'accueil du client
 * @see ControllerClient
 * @author Romain Frezier
 */
public class ControllerMonEspace extends ControllerClient {

    // Composants FXML-----------------------------------------------------------------------------------------------
    @FXML
    private Text clientName;
    // --------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    public void initialize(){
        clientName.setText(Facade.currentClient.getPseudo());
    }
}
