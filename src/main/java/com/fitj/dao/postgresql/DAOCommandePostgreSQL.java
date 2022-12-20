package com.fitj.dao.postgresql;

import com.fitj.classes.Commande;
import com.fitj.dao.DAOCommande;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import kotlin.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux commandes
 *
 * @author Etienne Tillier
 */
public class DAOCommandePostgreSQL extends DAOCommande {

    public DAOCommandePostgreSQL() {
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    /**
     * @param id int, l'id de la commande
     * @return un objet de type Commande dans la base de donnée avec l'id rentré en paramètre
     * @throws Exception
     */
    @Override
    public Commande getCommandeByID(int id) throws SQLException {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        ResultSet resultCommande = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
        Commande commande;

        //commande
        return null;
    }
}
