package com.fitj.facades.postgresql;

import com.fitj.Constante;
import com.fitj.facades.FacadeUser;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe de facade pour l'utilisateur visiteur propre à PostgreSQL
 * @see FacadeUser
 * @author Etienne Tillier
 */
public class FacadeUserPostgreSQL extends FacadeUser {

    /**
     * Instance de la facade pour le singleton
     */
    private static FacadeUserPostgreSQL instance = null;

    /**
     * Constructeur privé pour le singleton
     */
    protected FacadeUserPostgreSQL(){
    }

    /**
     * Getter de l'instance de la facade pour le singleton
     * @return l'instance de la facade
     */
    public static FacadeUserPostgreSQL getInstance(){
        if (instance == null){
            instance = new FacadeUserPostgreSQL();
        }
        return instance;
    }

    /**
     * Gère la connexion d'un utilisateur
     * @param mail     String, mail de l'utilisateur
     * @param password String, mot de passe de l'utilisateur
     * @return String, message de retour
     */
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


    /**
     * Gère l'inscription d'un utilisateur
     * @param user User, utilisateur à inscrire
     * @return String, message de retour
     */
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
