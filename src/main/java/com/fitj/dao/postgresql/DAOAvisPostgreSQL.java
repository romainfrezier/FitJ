package com.fitj.dao.postgresql;

import com.fitj.dao.DAOAvis;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux avis
 *
 * @author Etienne Tillier
 */
public class DAOAvisPostgreSQL extends DAOAvis {

    public DAOAvisPostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }
}
