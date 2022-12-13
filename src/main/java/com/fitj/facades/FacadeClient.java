package com.fitj.facades;

import com.fitj.controllers.ControllerClient;
import com.fitj.controllers.factory.FactoryController;
import com.fitj.models.ModelClient;
import com.fitj.models.tool.PasswordAuthentication;

public abstract class FacadeClient extends Facade {

    protected PasswordAuthentication passwordAuthentication;
    protected ModelClient modelClient;

    protected FacadeClient(){
        this.modelClient = this.factoryModel.getModelClient();
//        this.controller = FactoryController.getInstance().getControllerClient();
        this.passwordAuthentication = new PasswordAuthentication();
    }

    public abstract String connexion(String mail, String password);

    public abstract void inscription(String mail, String pseudo, String password, float poids, int taille, String photo) throws Exception;


}
