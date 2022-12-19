package com.fitj.dao.postgresql;

import com.fitj.classes.Sport;
import com.fitj.dao.DAOSport;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import kotlin.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux sports
 *
 * @author Etienne Tillier
 */
public class DAOSportPostgreSQL extends DAOSport {

    public DAOSportPostgreSQL() {
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    /**
     * Ajoute un sport dans la base de donnée
     * @param nom String, le nom du sport
     * @throws Exception
     */
    @Override
    public void createSport(String nom) throws SQLException {
        List<Pair<String,Object>> listeInsert = new ArrayList<>();
        listeInsert.add(new Pair<>("nom",nom));
        ((MethodesPostgreSQL)this.methodesBD).insert(listeInsert, this.table);
    }

    /**
     * @param id int, l'id du sport
     * @return le sport dans la base de donnée contenant l'id rentré en paramètre
     * @throws Exception
     */
    @Override
    public Sport getSportById(int id) throws SQLException {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", id));
        ResultSet sportData = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
        if (sportData.next()){
            Sport sport = new Sport(id, sportData.getString("nom"));
            return sport;
        }
        else {
            throw new SQLException("Sport non trouvé dans la bd");
        }
    }
}
