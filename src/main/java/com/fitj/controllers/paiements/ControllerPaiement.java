package com.fitj.controllers.paiements;

import com.fitj.classes.Admin;
import com.fitj.classes.Coach;
import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.Facade;
import com.fitj.facades.FacadePaiement;
import javafx.scene.control.Control;

public abstract class ControllerPaiement extends Controller {

    FacadePaiement facadePaiement = FacadePaiement.getInstance();

    protected void goToShop(Control controlEl) throws BadPageException {
        super.goToPage(controlEl, "shop/shop.fxml", "Shop");
    }

    protected void goToMonCompte(Control controlEl) throws BadPageException {
        String role;
        if (Facade.currentClient instanceof Admin) {
            role = "admin";
        } else if (Facade.currentClient instanceof Coach) {
            role = "coach";
        } else {
            role = "client";
        }
        super.goToPage(controlEl, role + "s/monCompte-" + role + ".fxml", "Mon compte");
    }
}
