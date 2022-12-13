package com.fitj.controllers;

import com.fitj.Constante;
import com.fitj.ViewSetter;
import com.fitj.facades.FacadeClient;
import com.fitj.facades.factory.FactoryFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerRegister {

    private final FacadeClient clientFacade = FactoryFacade.getInstance().getFacadeClient();
    @FXML
    private Button register;

    @FXML
    private Button login;

    @FXML
    private TextField mail;

    @FXML
    private TextField pseudo;

    @FXML
    private TextField password;

    @FXML
    private TextField passwordConfirm;

    @FXML
    private Text errorMessage;

    @FXML
    private Button homeButton;

    @FXML
    private void handleButtonRegister(ActionEvent event) throws Exception {
        if (checkPassword() && checkForm()) {
            System.out.println("here");
            // TODO ajouter le sex et les valeur venant du form
            String result = clientFacade.inscription(mail.getText(), pseudo.getText(), password.getText(), 80f, 180, "Femme");
            if (result.equals(Constante.REGISTERED)) {
                hideError();
                try {
                    register();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                displayError(result);
            }
        }
        else {
            displayError("Le Formulaire n'est pas complet");
        }
    }

    //Verifie que les formulaires sont remplis
    public boolean checkForm() {
        return !mail.getText().equals("") && !pseudo.getText().equals("") && !password.getText().equals("") && !passwordConfirm.getText().equals("");
    }
    //Creer un compte et ouvre la fenêtre principale ptincipal-view.fxml
    public void register() throws IOException {
        System.out.println("Compte créé");
        System.out.println("Connecté, maintenant ouvrir la fenêtre principale");
        //ouvrir la fenêtre principale
        Stage stage = (Stage) register.getScene().getWindow();
        Scene scene = ViewSetter.getScene("principal-view.fxml");
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }


    //Verifie si le mot de passe et sa confirmation sont identiques
    public boolean checkPassword() {
        if (!(password.equals(""))) {
            return password.getText().equals(passwordConfirm.getText());
        }
        return false;
    }

    @FXML
    //Retourne vers la page de connexion
    private void goToLogin(ActionEvent actionEvent) throws IOException {
        //ouvrir la fenêtre login
        Stage stage = (Stage) login.getScene().getWindow();
        Scene scene = ViewSetter.getScene("login-view.fxml");
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }





    //--------------------------------------------------------------------------------
    //Affiche un message d'erreur

    public void displayError(String message){
        errorMessage.setText(message);
    }

    //Cache le message d'erreur
    public void hideError(){
        errorMessage.setText("");
    }

    public void goToHome(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) homeButton.getScene().getWindow();
        Scene scene = ViewSetter.getScene("home-view.fxml");
        stage.setTitle("Welcome to FitJ");
        stage.setScene(scene);
        stage.show();
    }
}
