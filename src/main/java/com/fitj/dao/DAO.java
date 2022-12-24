package com.fitj.dao;


import com.fitj.dao.methodesBD.MethodesBD;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;

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

    /**
     * @param data Object, le contenu de l'attribut à vérifier
     * @param name String, le nom de l'attribut à vérifier
     * @return true si l'attribut est présent dans la table client sinon return false
     * @throws Exception si une erreur SQL survient
     */
    public boolean dataExist(Object data, String name) throws Exception {
        return (((MethodesPostgreSQL)this.methodesBD).exist(data, name, this.table));
    }

}
