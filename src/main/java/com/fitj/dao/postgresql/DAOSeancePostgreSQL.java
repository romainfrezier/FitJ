package com.fitj.dao.postgresql;

import com.fitj.classes.Exercice;
import com.fitj.classes.Seance;
import com.fitj.dao.DAOSeance;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import kotlin.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux séances
 *
 * @author Etienne Tillier
 */
public class DAOSeancePostgreSQL extends DAOSeance {

    /**
     * @param id int, l'id de la séance
     * @return la séance dans la base de donnée contenant l'id rentré en paramètre
     * @throws Exception
     */
    @Override
    public Seance getSeanceById(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", id));
        ResultSet seance = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
        if (seance.next()){
            return null;
            //return new Seance(seance.getInt("id"), seance.getString("nom"), seance.getString("description"));
        }
        else {
            throw new SQLException("Il n'y a pas d'exercice avec cet id");
        }
    }
}
