package com.fitj.controllers.users;

import com.fitj.controllers.Controller;
import com.fitj.facades.FacadeUser;
import com.fitj.facades.factory.FactoryFacade;
import javafx.scene.control.Control;
import java.io.IOException;

public abstract class ControllerUser extends Controller {

    final FacadeUser userFacade = FactoryFacade.getInstance().getFacadeUser();

    void goToRegister(Control controlEl) throws IOException {
        goToPage(controlEl, "register-view.fxml", "Register");
    }

    void goToLogin(Control controlEl) throws IOException {
        goToPage(controlEl, "login-view.fxml", "Login");
    }

    void goToVisitor(Control controlEl) throws IOException {
        goToPage(controlEl, "visitor-view.fxml", "Welcome to FitJ");
    }

    void goToHome(Control controlEl) throws IOException {
        goToPage(controlEl, "home-view.fxml", "Home");
    }
}
