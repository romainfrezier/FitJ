package com.fitj.controllers.monCompte;

import com.fitj.facades.FacadeClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import static com.fitj.facades.Facade.currentClient;

public class ControllerUpdatePassword extends ControllerMonCompte {


    @FXML
    private TextField password;
    @FXML
    private TextField passwordConfirm;
    @FXML
    private Button UpdateButton;
    @FXML
    private Text errorText;


    @FXML
    private void initialize() {
        super.hideError(errorText);
    }

    @FXML
    public void Update() throws Exception {
        super.hideError(errorText);
        if (!password.getText().isEmpty() && !passwordConfirm.getText().isEmpty()) {
            if (password.getText().equals(passwordConfirm.getText())) {
                clientFacade.updateClientPassword(password.getText(), currentClient.getId());
                super.displayError(errorText, "Mot de passe modifié");
            } else {
                super.displayError(errorText, "Les mots de passe ne correspondent pas");
            }
        } else {
            super.displayError(errorText, "Veuillez remplir tous les champs");
        }
    }

}
