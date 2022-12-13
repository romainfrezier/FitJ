package com.fitj.models.factory;

import com.fitj.models.ModelClient;

/**
 * Classe parente de toutes les factory de model qui permettent de créer des model en fonction du type de la base de donnée
 *
 * Classe abstraite non instanciable (singleton)
 *
 * @author Etienne Tillier
 */
public abstract class FactoryModel {

    /**
     * instance du singleton
     */
    private static FactoryModel instance = null;

    /**
     * type de la base de donnée
     */
    public static final String databaseType = "postgreSQL";

    protected FactoryModel(){

    }

    /**
     * @return l'instance du singleton
     */
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

    /**
     * @return l'instance du modelClient
     */
    public abstract ModelClient getModelClient();

}

