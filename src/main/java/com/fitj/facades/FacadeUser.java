package com.fitj.facades;

import com.fitj.models.ModelClient;
import com.fitj.models.factory.FactoryModel;
import com.fitj.models.tool.PasswordAuthentication;

/**
 * Facade User, permet de gérer les utilisateurs en appelant le controlleur et le modèle
 * Implémenté par la facade propre a la base de données
 * @see Facade
 */
public abstract class FacadeUser extends Facade {
    /**
     * Authentificateur du mot de passe
     */
    protected PasswordAuthentication passwordAuthentication;

    /**
     * Modèle de client
     */
    protected ModelClient modelClient;

    /**
     * Constructeur de la facade, initialise les attributs
     */
    protected FacadeUser(){
        this.modelClient = FactoryModel.getInstance().getModelClient();
        this.passwordAuthentication = new PasswordAuthentication();
    }

    /**
     * Gère la connexion d'un utilisateur
     * @param mail String, mail de l'utilisateur
     * @param password String, mot de passe de l'utilisateur
     * @return String, résultat de la connexion
     */
    public abstract String connexion(String mail, String password);

    // TODO remplacer par objet Client
    /**
     * Gère la création d'un utilisateur
     * @param user Client, utilisateur à créer
     * @return String, résultat de la création
     * @throws Exception si la création échoue
     */
    public abstract String inscription(String mail, String pseudo, String password, float poids, int taille, String photo) throws Exception;

}
