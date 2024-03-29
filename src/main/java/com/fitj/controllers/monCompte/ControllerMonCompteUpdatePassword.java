package com.fitj.controllers.monCompte;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import static com.fitj.facades.Facade.currentClient;

/**
 * Controller de la page de modification du mot de passe
 * @see ControllerMonCompte
 * @author Paco Munarriz, Romain Frezier
 */
public class ControllerMonCompteUpdatePassword extends ControllerMonCompte {


    @FXML
    private TextField password;
    @FXML
    private TextField passwordConfirm;
    @FXML
    private Button updateButton;
    @FXML
    private Text errorText;


    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
    }

    /**
     * Méthode permettant de modifier le mot de passe
     */
    @FXML
    private void update() {
        super.hideError(errorText);
        try {
            if (!password.getText().isEmpty() && !passwordConfirm.getText().isEmpty()) {
                if (password.getText().equals(passwordConfirm.getText())) {
                    clientFacade.updateClientPassword(password.getText(), currentClient.getId());
                    super.goToMonCompte(updateButton);
                } else {
                    super.displayError(errorText, "Les mots de passe ne correspondent pas");
                }
            } else {
                super.displayError(errorText, "Veuillez remplir les champs");
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }
}
