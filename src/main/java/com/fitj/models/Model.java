package com.fitj.models;


import com.fitj.models.methodesBD.MethodesBD;

/**
 * Classe parente de tous les modèles qui permettent d'intéragir avec la base de donnée
 *
 * Classe abstraite non instanciable
 *
 * @author Etienne Tillier
 */
public abstract class Model {

    /**
     * Le nom de la table en base de donnée.
     */
    protected String table;

    /**
     * L'instance pour accéder aux méthodes de base de données
     */
    protected MethodesBD methodesBD;

    public Model(String table){
        this.table = table;
    }

}
