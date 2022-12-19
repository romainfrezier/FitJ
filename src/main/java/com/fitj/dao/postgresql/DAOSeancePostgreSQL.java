package com.fitj.dao.postgresql;

import com.fitj.classes.Coach;
import com.fitj.classes.Exercice;
import com.fitj.classes.Seance;
import com.fitj.classes.Sport;
import com.fitj.dao.DAOSeance;
import com.fitj.dao.factory.FactoryModelPostgreSQL;
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

    public DAOSeancePostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

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
            Coach coach = (Coach) FactoryModelPostgreSQL.getInstance().getModelClient().getClientAccount(seance.getInt("idcoach"));
            Sport sport = FactoryModelPostgreSQL.getInstance().getModelSport().getSportById(seance.getInt("idsport"));
            ArrayList<Exercice> listeExercice = (ArrayList<Exercice>) this.getExercices(id);
            return new Seance(seance.getInt("id"), seance.getString("nom"), seance.getString("description"),seance.getDouble("prix"),coach,sport,listeExercice);
        }
        else {
            throw new SQLException("Il n'y a pas de séance avec cet id");
        }
    }

    /**
     * @param nom String, le nom de la séance
     * @return l'id de la séance
     * @throws SQLException
     */
    public int getIdSeanceByNom(String nom) throws SQLException{
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("nom", nom));
        ResultSet seance = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
        if (seance.next()){
            return seance.getInt("id");
        }
        else {
            throw new SQLException("Il n'y a pas de séance avec ce nom");
        }
    }


    /**
     * @param id int, l'id de la séance
     * @return la liste des exercices de la séance
     * @throws SQLException
     */
    public List<Exercice> getExercices(int id) throws SQLException {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("seanceexercice.idseance", id));
        List<Pair<String, String>> joinList = new ArrayList<>();
        joinList.add(new Pair<>("seanceexercice", "idexercice"));
        ResultSet exerciceBD = ((MethodesPostgreSQL)this.methodesBD).selectJoin(joinList,whereList,"exercice");
        List<Exercice> listeExercice = new ArrayList<>();
        while (exerciceBD.next()){
            listeExercice.add(new Exercice(exerciceBD.getInt("id"),exerciceBD.getString("nom"),exerciceBD.getString("description")));
        }
        return listeExercice;
    }

    /**
     * Créer une séance dans la base de donnée
     * @param nom String, le nom de la séance
     * @param description String, la description de la séance
     * @param prix double, le prix de la séance
     * @param coach Coach, le coach de la séance
     * @param sport Sport, le sport de la séance
     * @param exercices List<Exercice>, la liste des exercices de la séance
     * @throws Exception
     */
    public void createSeance(String nom, String description, double prix, Coach coach, Sport sport, List<Exercice> exercices) throws SQLException{
        List<Pair<String,Object>> listeInsert = new ArrayList<>();
        listeInsert.add(new Pair<>("nom",nom));
        listeInsert.add(new Pair<>("description",description));
        listeInsert.add(new Pair<>("prix",prix));
        listeInsert.add(new Pair<>("idcoach",coach.getId()));
        listeInsert.add(new Pair<>("idsport",sport.getId()));
        ((MethodesPostgreSQL)this.methodesBD).insert(listeInsert, this.table);
        int idSeance = this.getIdSeanceByNom(nom);
        for (Exercice exercice : exercices){
            List<Pair<String,Object>> listeInsertExercice = new ArrayList<>();
            listeInsertExercice.add(new Pair<>("idexercice",exercice.getId()));
            listeInsertExercice.add(new Pair<>("idseance",idSeance));
            ((MethodesPostgreSQL)this.methodesBD).insert(listeInsertExercice, "seanceexercice");
        }
    }
}
