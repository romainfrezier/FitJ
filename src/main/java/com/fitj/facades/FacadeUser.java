package com.fitj.facades;

import com.fitj.classes.Client;
import com.fitj.dao.DAOClient;
import com.fitj.dao.factory.FactoryModel;
import com.fitj.dao.tool.PasswordAuthentication;
import com.fitj.enums.Sexe;
import com.fitj.exceptions.UsedEmailException;
import com.fitj.exceptions.UsedPseudoException;
import com.fitj.exceptions.DBProblemException;
import com.fitj.exceptions.BadLoginException;
import com.fitj.exceptions.BadPasswordException;

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
    public Client connexion(String mail, String password) throws Exception{
        try {
            Client client = this.daoClient.getClientAccount(mail);
            if (passwordAuthentication.authenticate(password.toCharArray(), client.getPassword())){
                Facade.currentClient = client;
                return client;
            }
            else {
                throw new BadPasswordException("Mauvais mot de passe");
            }
        }
        catch (BadLoginException e){
            throw new BadLoginException(e.getMessage());
        }
    }

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
    public Client inscription(String mail, String pseudo, String password, float poids, int taille, String photo, Sexe sexe) throws Exception {
        if (daoClient.verifier(mail, "mail")) {
            throw new UsedEmailException(mail);
        } else {
            if (daoClient.verifier(pseudo, "pseudo")) {
                throw new UsedPseudoException(pseudo);
            } else {
                try {
                    daoClient.createClient(mail, pseudo, password, poids, taille, photo, sexe);
                    Client newClient = daoClient.getClientAccount(mail);
                    Facade.currentClient = newClient;
                    return newClient;
                } catch (SQLException e) {
                    throw new DBProblemException(e.getMessage());
                }
            }
        }
    }
}
