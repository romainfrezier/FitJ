package com.fitj.controllers.monCompte;

import com.fitj.classes.Coach;
import com.fitj.exceptions.BadPageException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import static com.fitj.facades.Facade.currentClient;

/**
 * Controller de la page mon compte du coach et de l'admin
 * @see ControllerMonCompte
 * @author Paco Munarriz, Romain Frezier
 */
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

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        pseudo.setText(currentClient.getPseudo());
        mail.setText(currentClient.getEmail());
        solde.setText(((Coach)currentClient).getSolde() + " €");
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


    /**
     * Méthode permettant de retirer l'argent du compte du coach
     */
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
