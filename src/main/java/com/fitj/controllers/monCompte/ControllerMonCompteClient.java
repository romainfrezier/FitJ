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
    private Text password;
    @FXML
    private Text mail;
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
        //change l'image a partir d'un lien
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



}
