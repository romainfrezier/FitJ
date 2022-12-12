package com.fitj.controllers.factory;

import com.fitj.controllers.ControllerClient;
import com.fitj.facades.*;

public abstract class FactoryController {

    private static FactoryController instance = null;

    private static final String databaseType = "postgreSQL";

    protected FactoryController(){

    }

    public static FactoryController getInstance(){
        if (instance == null){
            if (databaseType.equals("postgreSQL")){
                instance = new FactoryControllerPostgreSQL();
            }
            else {
                //exception database type
                System.out.println("Le type de base de données n'existe pas");
            }
        }
        return instance;
    }

    //vérifier à la fin du développement si on a besoins de toutes les controllers

    public abstract ControllerClient getControllerClient();



}
