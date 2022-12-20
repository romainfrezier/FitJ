package com.fitj.dao.postgresql;

import com.fitj.dao.DAORecette;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;


/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux recettes
 *
 * @author Etienne Tillier
 */
public class DAORecettePostgreSQL extends DAORecette {

    public DAORecettePostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

}
