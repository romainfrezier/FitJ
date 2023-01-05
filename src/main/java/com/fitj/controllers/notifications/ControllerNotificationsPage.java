package com.fitj.controllers.notifications;

import com.fitj.classes.Admin;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

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
