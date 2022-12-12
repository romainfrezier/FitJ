package com.fitj.controllers;

import com.fitj.Constante;
import com.fitj.models.ModelClient;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fitj.models.ModelClientPostgreSQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ControllerClient extends Controller{

    @FXML
    private Button connect;
    @FXML
    private TextField login;
    @FXML
    private Text errorMessage;
    @FXML
    private TextField password;

    private ModelClientPostgreSQL modelClient;

    public ControllerClient(){
        this.modelClient = new ModelClientPostgreSQL();
    }

    @FXML
    private void handleButtonConnect(ActionEvent event) {
        try {
            String result = connexion(login.getText(), password.getText());
            if(result.equals(Constante.CONNECTED)){
                hideError();
                connect();
            }else{
                displayError(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public void inscrire(String mail, String pseudo, String password, float poids, int taille, String photo) {
        try {
            if (!modelClient.verifierEmail(mail)){
                //throw error custom
                System.out.println("Email déjà utilisé");
            }
            else {
                modelClient.createClient(mail, pseudo, password,poids,taille,photo);
                System.out.println("Inscription ok");
            }
        }
        catch (Exception e){
            //gérer le comportement
            //créer les erreurs
        }

    }

    public String connexion(String mail, String password) throws SQLException {
        try {
            ResultSet compte = modelClient.connexionClient(mail);
            if (compte.getString("password").equals(password)){
                return Constante.CONNECTED;
            }
            else {
                return Constante.BAD_PASSWORD;
            }
        }
        catch (SQLException e){
            return Constante.BAD_LOGIN;
        }
    }
}
