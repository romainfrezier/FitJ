package com.fitj.controllers.users;

import com.fitj.Constante;
import com.fitj.enums.Sexe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import java.io.IOException;

/**
 * Controller pour la page de création de compte
 * @see ControllerUser
 * @author Romain Frezier
 */
public class ControllerRegister extends ControllerUser {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private Button loginHeaderButton;
    @FXML
    private Button registerHeaderButton;

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
    private Text errorMessage;

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
        if (checkPassword() && checkForm()) {
            try {
                String result = super.userFacade.inscription(mail.getText(), pseudo.getText(), password.getText(), (float) poidsSlider.getValue(), (int) tailleSlider.getValue(), photoProfil.getText(), getSexFromToggleGroup());
                if (result.equals(Constante.REGISTERED)) {
                    hideError();
                    try {
                        goToHome();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    displayError(result);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        else {
            displayError("Le formulaire n'est pas complet");
        }
    }

    /**
     * @see ControllerUser#goToHome(Control)
     * @throws IOException Exception
     */
    @FXML
    private void goToHome() throws IOException {
        super.goToHome(registerButton);
    }

    /**
     * @see ControllerUser#goToLogin(Control)
     * @throws IOException Exception
     */
    @FXML
    private void goToLogin() throws IOException {
        super.goToLogin(loginButton);
    }

    /**
     * @see ControllerUser#goToHome(Control)
     * @throws IOException Exception
     */
    @FXML
    private void goToVisitor() throws IOException {
        super.goToVisitor(visitorButton);
    }

    /**
     * Affiche le message d'erreur
     * @param message String, message d'erreur à afficher
     */
    private void displayError(String message){
        errorMessage.setText(message);
    }

    /**
     * Cache le message d'erreur
     */
    private void hideError(){
        errorMessage.setText("");
    }

    /**
     * Verifie que le mot de passe et la confirmation sont identiques
     * @return boolean, true si le mot de passe et sa confirmation sont identiques et non vides
     */
    private boolean checkPassword() {
        if (!(password.getText().equals(""))) {
            return password.getText().equals(passwordConfirm.getText());
        }
        return false;
    }

    /**
     * Vérifie que le formulaire est complet
     * @return boolean, true si le formulaire est complet
     */
    private boolean checkForm() {
        return !mail.getText().equals("") && !pseudo.getText().equals("") && !password.getText().equals("") && !passwordConfirm.getText().equals("");
    }

    private Sexe getSexFromToggleGroup(){
        RadioButton selectedButton = (RadioButton)sex.getSelectedToggle();
        return Sexe.getSexe(selectedButton.getText());
    }
}
