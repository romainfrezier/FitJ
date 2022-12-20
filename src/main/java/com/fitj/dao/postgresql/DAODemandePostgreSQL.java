package com.fitj.dao.postgresql;

import com.fitj.dao.DAODemande;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux demandes
 *
 * @author Etienne Tillier
 */
public class DAODemandePostgreSQL extends DAODemande {

    public DAODemandePostgreSQL() {
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

}
