package com.fitj.controllers.monCompte;

import com.fitj.classes.Coach;
import com.fitj.exceptions.BadPageException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import static com.fitj.facades.Facade.currentClient;

public class ControllerMonCompteCoachAdmin extends ControllerMonCompte {
    @FXML
    private Button retirerButton;
    @FXML
    private Text solde;
    @FXML
    private Text pseudo;
    @FXML
    private Text mail;
    @FXML
    private ImageView image;
    @FXML
    private Button updateButton;
    @FXML
    private Button updatePasswordButton;
    @FXML
    private Text errorText;

    @FXML
    private void initialize() {
        super.hideError(errorText);
        pseudo.setText(currentClient.getPseudo());
        mail.setText(currentClient.getEmail());
        solde.setText(((Coach)currentClient).getSolde() + " €");
        Image img = new Image(currentClient.getPhoto());
        image.setImage(img);
    }

    @FXML
    private void goToUpdate() {
        try {
            super.hideError(errorText);
            super.goToUpdate(updateButton);
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    @FXML
    private void goToUpdatePassword() {
        try {
            super.hideError(errorText);
            super.goToUpdatePassword(updatePasswordButton);
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }


    @FXML
    private void handleRetirerButton() {
        try {
            super.hideError(errorText);
            setPreviousPageName("monCompte");
            super.goToMakePayment(retirerButton);
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }
}
