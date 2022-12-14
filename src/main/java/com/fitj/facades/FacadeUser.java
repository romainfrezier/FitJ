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

    protected PasswordAuthentication passwordAuthentication;
    protected ModelClient modelClient;

    protected FacadeUser(){
        this.modelClient = FactoryModel.getInstance().getModelClient();
        this.passwordAuthentication = new PasswordAuthentication();
    }

    public abstract String connexion(String mail, String password);

    // TODO remplacer par objet Client
    public abstract String inscription(String mail, String pseudo, String password, float poids, int taille, String photo) throws Exception;

}
