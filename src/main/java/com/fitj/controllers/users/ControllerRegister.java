package com.fitj.controllers.users;

import com.fitj.classes.Client;
import com.fitj.enums.Sexe;
import com.fitj.exceptions.BadPageException;
import com.fitj.exceptions.UncompletedFormException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

/**
 * Controller pour la page de création de compte
 * @see ControllerUser
 * @author Romain Frezier
 */
public class ControllerRegister extends ControllerUser {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private TextField mail;
    @FXML
    private TextField pseudo;

    @FXML
    private Slider poidsSlider;
    @FXML
    private Slider tailleSlider;

    @FXML
    private ToggleGroup sex;
    @FXML
    private RadioButton hommeRadio;
    @FXML
    private RadioButton femmeRadio;
    @FXML
    private RadioButton autreRadio;

    @FXML
    private TextField password;
    @FXML
    private TextField passwordConfirm;

    @FXML
    private TextField photoProfil;

    @FXML
    private Text errorText;

    @FXML
    private Button registerButton;
    @FXML
    private Button loginButton;
    @FXML
    private Button visitorButton;
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du clic sur le bouton "Créer mon compte"
     */
    @FXML
    private void handleButtonRegister(){
        try {
            checkForm();
            checkPassword();
            Client client = super.userFacade.inscription(mail.getText(), pseudo.getText(), password.getText(), (float) poidsSlider.getValue(), (int) tailleSlider.getValue(), photoProfil.getText(), getSexFromToggleGroup());
            if (client != null) {
                super.hideError(errorText);
                goToHome();
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * @see ControllerUser#goToHome(Control, String)
     */
    @FXML
    private void goToHome(){
        try {
            super.hideError(errorText);
            super.goToHome(registerButton, "client");
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode appelée lors du clic sur le bouton redirigeant vers la page de connexion
     */
    @FXML
    private void goToLogin() {
        try {
            super.hideError(errorText);
            super.goToPage(registerButton, "views/users/login-view.fxml", "Connexion");
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode appelée lors du clic sur le bouton redirigeant vers la page d'accueil visiteur
     */
    @FXML
    private void goToVisitor() {
        try {
            super.hideError(errorText);
            super.goToVisitor(visitorButton);
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Vérifie que le mot de passe et la confirmation sont identiques
     * @throws UncompletedFormException si le mot de passe et la confirmation ne sont pas identiques
     */
    private void checkPassword() throws UncompletedFormException {
        if ((password.getText().equals("")) || !password.getText().equals(passwordConfirm.getText())) {
            throw new UncompletedFormException("Le mot de passe et sa confirmation doivent être identiques");
        }
    }

    /**
     * Vérifie que le formulaire est complet
     * @throws UncompletedFormException si le formulaire n'est pas complet
     */
    private void checkForm() throws UncompletedFormException {
        if (mail.getText().equals("") || pseudo.getText().equals("") || password.getText().equals("") || passwordConfirm.getText().equals("") || photoProfil.getText().equals("")) {
            throw new UncompletedFormException("Le formulaire n'est pas complet");
        }
    }

    /**
     * Récupère le sexe sélectionné dans le ToggleGroup
     * @return Sexe, sexe sélectionné
     */
    private Sexe getSexFromToggleGroup(){
        RadioButton selectedButton = (RadioButton)sex.getSelectedToggle();
        return Sexe.getSexe(selectedButton.getText());
    }
}
