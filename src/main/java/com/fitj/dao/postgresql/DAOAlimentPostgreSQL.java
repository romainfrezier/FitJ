package com.fitj.dao.postgresql;

import com.fitj.dao.DAOAliment;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux aliments
 *
 * @author Etienne Tillier
 */
public class DAOAlimentPostgreSQL extends DAOAliment {

    public DAOAlimentPostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

}
