package com.fitj.dao;


import com.fitj.dao.methodesBD.MethodesBD;

/**
 * Classe parente de tous les modèles qui permettent d'intéragir avec la base de donnée
 *
 * Classe abstraite non instanciable
 *
 * @author Etienne Tillier
 */
public abstract class DAO {

    /**
     * Le nom de la table en base de donnée.
     */
    protected String table;

    /**
     * L'instance pour accéder aux méthodes de base de données
     */
    protected MethodesBD methodesBD;

    public DAO(String table){
        this.table = table;
    }

}
