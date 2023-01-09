package com.fitj.controllers.notifications;

import com.fitj.classes.Admin;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

/**
 * Controller de la page notification
 * @see ControllerNotifications
 * @author Romain Frezier
 */
public class ControllerNotificationsPage extends ControllerNotifications {
    @FXML
    private Tab commandeTab;

    @FXML
    private void initialize() {
        if (Facade.currentClient instanceof Admin) {
            commandeTab.setText("Toutes les commandes");
        }
    }
}
