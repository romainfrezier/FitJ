package com.fitj.facades;

import com.fitj.Constante;
import com.fitj.classes.Client;
import com.fitj.dao.DAOClient;
import com.fitj.dao.factory.FactoryModel;
import com.fitj.dao.tool.PasswordAuthentication;
import com.fitj.enums.Sexe;

import java.sql.SQLException;

/**
 * Classe de facade pour l'utilisateur visiteur propre à PostgreSQL
 * @see FacadeUser
 * @author Etienne Tillier
 */
public class FacadeUser extends Facade {

    /**
     * Authentificateur du mot de passe
     */
    protected PasswordAuthentication passwordAuthentication;

    /**
     * Modèle de client
     */
    protected DAOClient daoClient;


    /**
     * Instance de la facade pour le singleton
     */
    private static FacadeUser instance = null;

    /**
     * Constructeur de la facade, initialise les attributs
     */
    protected FacadeUser(){
        this.daoClient = FactoryModel.getInstance().getModelClient();
        this.passwordAuthentication = new PasswordAuthentication();
    }

    /**
     * Getter de l'instance de la facade pour le singleton
     * @return l'instance de la facade
     */
    public static FacadeUser getInstance(){
        if (instance == null){
            instance = new FacadeUser();
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
            Client client = this.daoClient.getClientAccount(mail);
            if (passwordAuthentication.authenticate(password.toCharArray(), client.getPassword())){
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

    /**
     * Gère l'inscription d'un utilisateur
     * @param mail String, mail de l'utilisateur
     * @param pseudo String, pseudo de l'utilisateur
     * @param password String, mot de passe de l'utilisateur
     * @param poids float, poids de l'utilisateur
     * @param taille int, taille de l'utilisateur
     * @param photo String, photo de l'utilisateur
     * @param sexe Sexe, sexe de l'utilisateur
     * @return String, message de retour
     */
    public String inscription(String mail, String pseudo, String password, float poids, int taille, String photo, Sexe sexe) {
        try {
            if (daoClient.verifier(mail,"mail")){
                return Constante.USED_EMAIL;
            }
            else {
                if (daoClient.verifier(pseudo,"pseudo")){
                    return Constante.USED_PSEUDO;
                }
                else {
                    daoClient.createClient(mail, pseudo, password,poids,taille,photo, sexe);
                    return Constante.REGISTERED;
                }
            }
        }
        catch (Exception e){
            return Constante.REGISTER_ERROR;
        }
    }
}
