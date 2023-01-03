package com.fitj.controllers.monCompte;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import static com.fitj.facades.Facade.currentClient;

public class ControllerMonCompteUpdatePassword extends ControllerMonCompte {


    @FXML
    private TextField password;
    @FXML
    private TextField passwordConfirm;
    @FXML
    private Button updateButton;
    @FXML
    private Text errorText;


    @FXML
    private void initialize() {
        super.hideError(errorText);
    }

    @FXML
    public void update() throws Exception {
        super.hideError(errorText);
        try {
            if (!password.getText().isEmpty() && !passwordConfirm.getText().isEmpty()) {
                if (password.getText().equals(passwordConfirm.getText())) {
                    clientFacade.updateClientPassword(password.getText(), currentClient.getId());
                    super.displayError(errorText, "Mot de passe modifié");
                } else {
                    super.displayError(errorText, "Les mots de passe ne correspondent pas");
                }
            } else {
                super.displayError(errorText, "Veuillez remplir les champs");
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }
}
