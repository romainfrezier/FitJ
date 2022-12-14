package com.fitj.dao.factory;

import com.fitj.dao.postgresql.DAOClientPostgreSQL;

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
    public DAOClientPostgreSQL getModelClient(){
        return new DAOClientPostgreSQL();
    }



}
