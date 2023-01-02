package com.fitj.controllers.monCompte;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import static com.fitj.facades.Facade.currentClient;

public class ControllerUpload extends ControllerMonCompte {

    @FXML
    private TextField pseudo;
    @FXML
    private TextField password;
    @FXML
    private TextField passwordConfirm;
    @FXML
    private TextField mail;
    @FXML
    private TextField image;
    @FXML
    private Button UpdateButton;
    @FXML
    private Text errorText;

    @FXML
    private void initialize() {
        super.hideError(errorText);
        //change les valeurs des textField
        pseudo.setText(currentClient.getPseudo());
        password.setText(currentClient.getPassword());
        mail.setText(currentClient.getEmail());
        image.setText(currentClient.getPhoto());
    }

    @FXML
    private void Update() {
        super.hideError(errorText);
    }

}
