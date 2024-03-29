package com.fitj.controllers.monCompte;

import com.fitj.exceptions.BadPageException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import static com.fitj.facades.Facade.currentClient;

/**
 * Controller de la page mon compte du client
 * @see ControllerMonCompte
 * @author Paco Munarriz, Romain Frezier
 */
public class ControllerMonCompteClient extends ControllerMonCompte {
    @FXML
    private Text pseudo;
    @FXML
    private Text mail;
    @FXML
    private ImageView image;
    @FXML
    private Button updateButton;
    @FXML
    private Text errorText;
    @FXML
    private Button updatePasswordButton;


    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        //change les valeurs des textArea
        pseudo.setText(currentClient.getPseudo());
        mail.setText(currentClient.getEmail());
        Image img = new Image(currentClient.getPhoto());
        image.setImage(img);
    }

    /**
     * Méthode permettant d'aller vers la page de modification du compte
     */
    @FXML
    private void goToUpdate() {
        try {
            super.hideError(errorText);
            super.goToUpdate(updateButton);
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode permettant d'aller vers la page de modification du mot de passe
     */
    @FXML
    private void goToUpdatePassword() {
        try {
            super.hideError(errorText);
            super.goToUpdatePassword(updatePasswordButton);
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }



}
