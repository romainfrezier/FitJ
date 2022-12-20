package com.fitj.dao.postgresql;

import com.fitj.dao.DAOMateriel;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux matériels
 *
 * @author Etienne Tillier
 */
public class DAOMaterielPostgreSQL extends DAOMateriel {


    public DAOMaterielPostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

}
