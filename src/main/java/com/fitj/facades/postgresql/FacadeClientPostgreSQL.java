package com.fitj.facades.postgresql;

import com.fitj.Constante;
import com.fitj.facades.FacadeClient;
import com.fitj.facades.factory.FactoryFacade;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FacadeClientPostgreSQL extends FacadeClient {

    private static FacadeClientPostgreSQL instance = null;
    protected FacadeClientPostgreSQL(){

    }

    public static FacadeClientPostgreSQL getInstance(){
        if (instance == null){
            instance = new FacadeClientPostgreSQL();
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
                //throw error custom
                return Constante.USED_EMAIL;
            }
            else {
                if (modelClient.verifier(pseudo,"pseudo")){
                    //throw error custom
                    return Constante.USED_PSEUDO;
                }
                else {
                    modelClient.createClient(mail, pseudo, password,poids,taille,photo);
                    return Constante.REGISTERED;
                }
            }
        }
        catch (Exception e){
            //gérer le comportement
            //créer les erreurs
            return Constante.REGISTER_ERROR;
        }
    }

//    public static void main(String[] args) throws Exception {
//        FacadeClient facadeClient = FactoryFacade.getInstance().getFacadeClient();
//        facadeClient.inscription("etiennet@gmail.coffmmmmm", "jako", "123456", 80, 180, "superbePhoto");
//        System.out.println(facadeClient.connexion("etiennet@gmail.coffmmmmm","123456"));
//    }
}
