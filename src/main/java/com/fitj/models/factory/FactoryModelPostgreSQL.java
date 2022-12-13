package com.fitj.models.factory;

import com.fitj.models.factory.FactoryModel;
import com.fitj.models.postgresql.ModelClientPostgreSQL;

/**
 * Classe de la factory de model PostgreSQL qui permettent de créer des model PostgreSQL
 *
 * Classe (singleton)
 *
 * @author Etienne Tillier
 */
public class FactoryModelPostgreSQL extends FactoryModel {

    public FactoryModelPostgreSQL(){
        super();

    }


    /**
     * @return l'instance du modelClientPostgreSQL
     */
    public ModelClientPostgreSQL getModelClient(){
        return new ModelClientPostgreSQL();
    }



}
