package com.fitj.facades;

import com.fitj.controllers.ControllerLogin;
import com.fitj.models.ModelClient;
import com.fitj.models.factory.FactoryModel;
import com.fitj.models.tool.PasswordAuthentication;

public abstract class FacadeClient extends Facade {

    protected PasswordAuthentication passwordAuthentication;
    protected ModelClient modelClient;

//    protected ControllerLogin controllerLogin;

    protected FacadeClient(){
        this.modelClient = FactoryModel.getInstance().getModelClient();
//        this.controllerLogin = new ControllerLogin();
//        this.controller = FactoryController.getInstance().getControllerClient();
        this.passwordAuthentication = new PasswordAuthentication();
    }

    public abstract String connexion(String mail, String password);

    public abstract String inscription(String mail, String pseudo, String password, float poids, int taille, String photo) throws Exception;


}
