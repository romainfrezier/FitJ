package com.fitj.controllers.admins;

import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

/**
 * Controller pour la page d'accueil de l'administrateur
 * @see ControllerAdmin
 * @author Romain Frezier
 */
public class ControllerAdminMonEspace extends ControllerAdmin {

    // Composants FXML-----------------------------------------------------------------------------------------------
    @FXML
    private Text adminName;
    // --------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    public void initialize(){
        adminName.setText(Facade.currentClient.getPseudo());
    }

}
