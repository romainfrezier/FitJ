package com.fitj.dao.postgresql;

import com.fitj.classes.Materiel;
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
     * @throws SQLException si une erreur SQL est détectée
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
     * @throws SQLException si une erreur SQL est détectée
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

    /**
     * Récupère tous les sports dans la base de donnée
     * @return List<Sport>, la liste de tous les sports
     * @throws Exception si une erreur est détectée
     */
    @Override
    public List<Sport> getAllSports() throws Exception {
        ResultSet result = ((MethodesPostgreSQL)this.methodesBD).selectAll(this.table);
        try {
            List<Sport> listeSports = new ArrayList<>();
            if (result.next()){
                do {
                    listeSports.add(new Sport(result.getInt("id"), result.getString("nom")));
                } while (result.next());
            }
            return listeSports;
        }
        catch (SQLException e){
            throw new SQLException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
