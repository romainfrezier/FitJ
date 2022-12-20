package com.fitj.dao.postgresql;

import com.fitj.dao.DAOProgramme;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux programmes
 *
 * @author Etienne Tillier
 */
public class DAOProgrammePostgreSQL extends DAOProgramme {

    public DAOProgrammePostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }


}
