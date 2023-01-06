package com.fitj.controllers.headers;

import com.fitj.classes.Admin;
import com.fitj.classes.Coach;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ControllerHeaderAll extends ControllerHeader {
    @FXML
    private AnchorPane headerVisitor;
    @FXML
    private AnchorPane headerClient;
    @FXML
    private AnchorPane headerCoach;
    @FXML
    private AnchorPane headerAdmin;

    @FXML
    public void initialize() {
        if (Facade.currentClient == null) {
            headerVisitor.setVisible(true);
        } else if (Facade.currentClient instanceof Admin) {
            super.setPath("admin");
            this.headerAdmin.setVisible(true);
        } else if (Facade.currentClient instanceof Coach) {
            super.setPath("coach");
            this.headerCoach.setVisible(true);
        } else {
            super.setPath("client");
            this.headerClient.setVisible(true);
        }
    }
}
