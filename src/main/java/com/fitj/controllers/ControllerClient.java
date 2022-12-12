package com.fitj.controllers;

import com.fitj.facades.factory.FactoryFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.SQLException;

public abstract class ControllerClient extends Controller{

    @FXML
    protected Button connect;
    @FXML
    protected TextField login;
    @FXML
    protected Text errorMessage;
    @FXML
    protected TextField password;

    public ControllerClient(){
        this.facade = FactoryFacade.getInstance().getFacadeClient();
    }


    //Affiche un message d'erreur
    @FXML
    public void displayError(String message){
        this.errorMessage.setText(message);
    }

    @FXML
    public void hideError(){
        errorMessage.setText("");
    }
    @FXML
    public void connect(){
        System.out.println("Connecté, maintenant ouvrir la fenêtre principale");
    }

    public abstract void inscrire(String mail, String pseudo, String password, float poids, int taille, String photo) throws Exception;

    public abstract void connexion(String login, String password) throws SQLException;

}
