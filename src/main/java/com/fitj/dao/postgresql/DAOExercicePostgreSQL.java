package com.fitj.dao.postgresql;

import com.fitj.classes.Exercice;
import com.fitj.dao.DAOExercice;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.enums.Sexe;
import kotlin.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux exercices
 *
 * @author Etienne Tillier
 */
public class DAOExercicePostgreSQL extends DAOExercice {


    public DAOExercicePostgreSQL() {
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    /**
     * @param id int, l'id de l'exercice
     * @return l'exercice dans la base de donnée avec l'id rentré en paramètre
     * @throws Exception
     */
    public Exercice getExerciceById(int id) throws SQLException {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", id));
        ResultSet exercice = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
        if (exercice.next()){
            return new Exercice(exercice.getInt("id"), exercice.getString("nom"), exercice.getString("description"));
        }
        else {
            throw new SQLException("Il n'y a pas d'exercice avec cet id");
        }
    }

    /**
     * Creer un exercice dans la base de donnée
     * @param nom String, le nom de l'exercice
     * @param description String, la description de l'exercice
     * @throws Exception
     */
    @Override
    public void createExercice(String nom, String description) throws SQLException {
        List<Pair<String,Object>> data = new ArrayList<>();
        data.add(new Pair<>("nom", nom));
        data.add(new Pair<>("description", description));
        ((MethodesPostgreSQL)this.methodesBD).insert(data, this.table);
    }


}
