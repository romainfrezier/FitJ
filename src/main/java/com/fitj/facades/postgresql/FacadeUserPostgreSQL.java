package com.fitj.facades.postgresql;

import com.fitj.Constante;
import com.fitj.facades.FacadeUser;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FacadeUserPostgreSQL extends FacadeUser {


    private static FacadeUserPostgreSQL instance = null;
    protected FacadeUserPostgreSQL(){

    }

    public static FacadeUserPostgreSQL getInstance(){
        if (instance == null){
            instance = new FacadeUserPostgreSQL();
        }
        return instance;
    }
    public String connexion(String mail, String password){
        try {
            ResultSet compte = (ResultSet) this.modelClient.getClientAccount(mail);
            if (passwordAuthentication.authenticate(password.toCharArray(), compte.getString("password"))){
                return Constante.CONNECTED;
            }
            else {
                return Constante.BAD_PASSWORD;
            }
        }
        catch (SQLException e){
            return Constante.BAD_LOGIN;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String inscription(String mail, String pseudo, String password, float poids, int taille, String photo) {
        try {
            if (modelClient.verifier(mail,"mail")){
                return Constante.USED_EMAIL;
            }
            else {
                if (modelClient.verifier(pseudo,"pseudo")){
                    return Constante.USED_PSEUDO;
                }
                else {
                    modelClient.createClient(mail, pseudo, password,poids,taille,photo);
                    return Constante.REGISTERED;
                }
            }
        }
        catch (Exception e){
            return Constante.REGISTER_ERROR;
        }
    }
}
