package com.fitj.controllers.monCompte;

import com.fitj.exceptions.BadPageException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import static com.fitj.facades.Facade.currentClient;

public class ControllerMonCompteCoachAdmin extends ControllerMonCompte {
    @FXML
    private TextArea pseudo;
    @FXML
    private TextArea password;
    @FXML
    private TextArea mail;
    @FXML
    private TextArea Solde;
    @FXML
    private ImageView image;
    @FXML
    private Button UpdateButton;
    @FXML
    private Text errorText;

    @FXML
    private void initialize() {
        super.hideError(errorText);
        //change les valeurs des textArea
        pseudo.setText(currentClient.getPseudo());
        password.setText(currentClient.getPassword());
        mail.setText(currentClient.getEmail());
        //Solde.setText(currentClient.getSolde());
        //change l'image a partir d'un lien
        //image.setImage(currentClient.getPhoto());
    }

    @FXML
    private void goToUpload() {
        try {
            super.hideError(errorText);
            super.goToUpload(UpdateButton);
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }



}
