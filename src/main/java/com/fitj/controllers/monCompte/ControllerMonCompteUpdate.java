package com.fitj.controllers.monCompte;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import static com.fitj.facades.Facade.currentClient;

public class ControllerMonCompteUpdate extends ControllerMonCompte {

    @FXML
    private TextField pseudo;
    @FXML
    private TextField mail;
    @FXML
    private TextField image;
    @FXML
    private Button updateButton;
    @FXML
    private Text errorText;

    @FXML
    private void initialize() {
        super.hideError(errorText);
        //change les valeurs des textField
        pseudo.setText(currentClient.getPseudo());
        mail.setText(currentClient.getEmail());
        image.setText(currentClient.getPhoto());

    }

    // @FXML
     @FXML
     private void update() throws Exception {
         super.hideError(errorText);
         try {
             if (pseudo.getText().isEmpty() || mail.getText().isEmpty() || image.getText().isEmpty()) {
                super.displayError(errorText, "Veuillez remplir tous les champs");
             } else {
                 currentClient.setPseudo(pseudo.getText());
                 currentClient.setEmail(mail.getText());
                 currentClient.setPhoto(image.getText());
                 super.displayError(errorText, "Informations modifiées");
             }
         } catch (Exception e) {
             super.displayError(errorText, e.getMessage());

         }
     }
}
