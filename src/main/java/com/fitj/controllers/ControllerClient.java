package com.fitj.controllers;

import com.fitj.models.ModelClient;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerClient {

    private ModelClient modelClient;

    public ControllerClient(){
        this.modelClient = new ModelClient("client");
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

    public boolean connexion(String mail, String password) throws SQLException {
        try {
            ResultSet compte = modelClient.connexionClient(mail);
            if (compte.getString("password").equals(password)){
                System.out.println("connexion ok");
                return true;
            }
            else {
                System.out.println("mauvais mdp");
                return false;
            }
        }
        catch (SQLException e){
            System.out.println("Le mail n'existe pas");
            return false;
        }
    }


    public static void main(String[] args) throws SQLException {
        ControllerClient controllerClient = new ControllerClient();
        controllerClient.inscrire("etiennet@gmail.coaaaa", "Josef", "123456",80,170,"superphoto");
        System.out.println(controllerClient.connexion("etiennet@gmail.com","123456"));
    }

}
