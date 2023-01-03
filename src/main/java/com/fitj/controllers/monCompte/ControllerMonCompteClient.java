package com.fitj.controllers.monCompte;

import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import static com.fitj.facades.Facade.currentClient;

public class ControllerMonCompteClient extends ControllerMonCompte {
    @FXML
    private Text pseudo;
    @FXML
    private Text mail;
    @FXML
    private ImageView image;
    @FXML
    private Button UpdateButton;
    @FXML
    private Text errorText;
    @FXML
    private Button UpdatePasswordButton;


    @FXML
    private void initialize() {
        super.hideError(errorText);
        //change les valeurs des textArea
        pseudo.setText(currentClient.getPseudo());
        mail.setText(currentClient.getEmail());
        //image.setImage(currentClient.getPhoto());
    }

    @FXML
    private void goToUpdate() {
        try {
            super.hideError(errorText);
            super.goToUpdate(UpdateButton);
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    @FXML
    private void goToUpdatePassword() {
        try {
            super.hideError(errorText);
            super.goToUpdatePassword(UpdatePasswordButton);
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }



}
