package com.fitj.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class HelloController {
    @FXML
    private Button connect;
    @FXML
    private TextField login;
    @FXML
    private Text errorMessage;
    @FXML
    private TextField password;

    //Tentative de connexion
    @FXML
    private void handleButtonConnect(ActionEvent event) {
        if(checkLogin() && checkPassword()){
            hideError();
            connect();
        }else{
            displayError();
        }
    }
    //Vérifie l'identifiants
    public boolean checkLogin(){
        return login.getText().equals("admin");
    }

    //Vérifie le mot de passe
    public boolean checkPassword(){
        return password.getText().equals("admin");
    }

    //Connecte l'utilisateur et ouvre la fenêtre principale ptincipal-view.fxml
    public void connect(){
        System.out.println("Connecté");


    }

    //Affiche un message d'erreur
    public void displayError(){
        errorMessage.setText("Identifiant ou mot de passe incorrects");
    }
    //Cache le message d'erreur
    public void hideError(){
        errorMessage.setText("");
    }

}
