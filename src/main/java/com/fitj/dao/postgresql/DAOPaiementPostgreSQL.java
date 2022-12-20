package com.fitj.dao.postgresql;

import com.fitj.dao.DAOPaiement;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux paiements
 *
 * @author Etienne Tillier
 */
public class DAOPaiementPostgreSQL extends DAOPaiement {

    public DAOPaiementPostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

}
