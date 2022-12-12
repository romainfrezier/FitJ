package com.fitj.controllers;

import com.fitj.controllers.ControllerClient;
import com.fitj.controllers.factory.FactoryController;
import com.fitj.facades.postgresql.FacadeClientPostgreSQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.SQLException;

public class ControllerFXClient {

    @FXML
    private Button connect;
    @FXML
    private TextField login;
    @FXML
    private Text errorMessage;
    @FXML
    private TextField password;

    private ControllerClient controllerClient;

    public ControllerFXClient(){
        this.controllerClient = FactoryController.getInstance().getControllerClient();
    }

    @FXML
    private void handleButtonConnect(ActionEvent event) throws SQLException {
        this.controllerClient.connexion(login.getText(), password.getText());
    }

    //Affiche un message d'erreur
    public void displayError(String message){
        errorMessage.setText(message);
    }
    //Cache le message d'erreur
    public void hideError(){
        errorMessage.setText("");
    }

    //Connecte l'utilisateur et ouvre la fenêtre principale ptincipal-view.fxml
    public void connect(){
        System.out.println("Connecté, maintenant ouvrir la fenêtre principale");
    }

    public void inscrire(String mail, String pseudo, String password, float poids, int taille, String photo) throws Exception {
        this.controllerClient.inscrire(mail,pseudo,password,poids,taille,photo);
    }


}
