package com.fitj.controllers.monCompte;

import com.fitj.facades.FacadeClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import static com.fitj.facades.Facade.currentClient;

public class ControllerUpdate extends ControllerMonCompte {

    @FXML
    private TextField pseudo;
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
        mail.setText(currentClient.getEmail());
        image.setText(currentClient.getPhoto());

    }

    // @FXML
     @FXML
     private void Update() throws Exception {
         super.hideError(errorText);
         if (!pseudo.getText().isEmpty()) {
             clientFacade.updateClientPseudo(pseudo.getText(), currentClient.getId());
         }
         if (!mail.getText().isEmpty()){
             clientFacade.updateClientMail(mail.getText(), currentClient.getId());
         }
        if (!image.getText().isEmpty()) {
            clientFacade.updateClientPhoto(image.getText(), currentClient.getId());
        }
        super.displayError(errorText, "Informations modifiées");
     }


}
