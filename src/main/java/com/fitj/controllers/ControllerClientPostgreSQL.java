package com.fitj.controllers;

import com.fitj.facades.postgresql.FacadeClientPostgreSQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class ControllerClientPostgreSQL extends ControllerClient {

    private static ControllerClientPostgreSQL instance = null;
    protected ControllerClientPostgreSQL(){

    }

    @FXML
    public static ControllerClientPostgreSQL getInstance(){
        if (instance == null){
            instance = new ControllerClientPostgreSQL();
        }
        return instance;
    }

    public void inscrire(String mail, String pseudo, String password, float poids, int taille, String photo) throws Exception {
        ((FacadeClientPostgreSQL)this.facade).inscription(mail,pseudo,password,poids,taille,photo);
    }
    public void connexion(String login, String password) {
        ((FacadeClientPostgreSQL)this.facade).connexion(login, password);
    }


}
