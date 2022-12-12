package com.fitj.models.factory;

import com.fitj.models.ModelClient;

public abstract class FactoryModel {

    private static FactoryModel instance = null;

    public static final String databaseType = "postgreSQL";

    protected FactoryModel(){

    }

    public static FactoryModel getInstance(){
        if (instance == null){
            if (databaseType == "postgreSQL"){
                instance = new FactoryModelPostgreSQL();
            }
            else {
                //mettre une erreur ici
                System.out.println("Le type de base de données n'existe pas");
            }
        }
        return instance;
    }

    public abstract ModelClient getModelClient();

}

