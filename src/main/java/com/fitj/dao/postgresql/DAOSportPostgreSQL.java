package com.fitj.dao.postgresql;

import com.fitj.classes.Materiel;
import com.fitj.classes.Seance;
import com.fitj.classes.Sport;
import com.fitj.dao.DAOSport;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
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
    public Sport createSport(String nom) throws Exception {
        List<Pair<String,Object>> listeInsert = new ArrayList<>();
        listeInsert.add(new Pair<>("nom",nom));
        try {
            int id = ((MethodesPostgreSQL)this.methodesBD).insert(listeInsert, this.table);
            return this.getSportById(id);
        }
        catch (Exception e){
            throw new SQLException("La création du sport a échoué");
        }
    }

    /**
     * @param id int, l'id du sport
     * @return le sport dans la base de donnée contenant l'id rentré en paramètre
     * @throws SQLException si une erreur SQL est détectée
     */
    @Override
    public Sport getSportById(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", id));
        ResultSet sportData = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
        try{
            if (sportData.next()){
                Sport sport = new Sport(id, sportData.getString("nom"));
                return sport;
            }
            else {
                throw new SQLException("Aucun sport avec cet id n'existe");
            }
        }
        catch(Exception e){
            throw new SQLException("La selection du sport a échoué");
        }

    }

    @Override
    public Sport getSportByNom(String nom) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("nom", nom));
        ResultSet sportData = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
        try{
            if (sportData.next()){
                Sport sport = new Sport(sportData.getInt("id"), sportData.getString("nom"));
                return sport;
            }
            else {
                throw new SQLException("Aucun sport avec ce nom n'existe");
            }
        }
        catch(Exception e){
            throw new SQLException("La selection du sport a échoué");
        }
    }

    @Override
    public List<Sport> getAllSport() throws Exception {
        List<Sport> listeSport = new ArrayList<>();
        ResultSet sportsBD = ((MethodesPostgreSQL)this.methodesBD).selectAll(this.table);
        try {
            while(sportsBD.next()){
                listeSport.add(new Sport(sportsBD.getInt("id"), sportsBD.getString("nom")));
            }
            return listeSport;
        }
        catch (Exception e){
            throw new SQLException("Impossible de récupérer tous les sports");
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
        catch (Exception e){
            throw new SQLException("La sélection de tous les sport a échoué");
        }
    }

    /**
     * Supprime le sport de la base de donnée
     * @param id int, l'id du sport
     * @throws Exception
     */
    @Override
    public void supprimerSport(int id) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        List<Pair<String,Object>> whereListClientSport = new ArrayList<>();
        whereListClientSport.add(new Pair<>("idsport",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereListClientSport,"clientsport");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereListClientSport,"demandesport");
            this.supprimerSeanceSport(id);
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList,this.table);
        }
        catch (Exception e){
            throw new SQLException("La suppression du sport a échoué");
        }
    }

    /**
     * Supprimer les séances qui font référence à ce sport
     * @param id int, l'id du sport
     * @throws SQLException
     */
    public void supprimerSeanceSport(int id) throws Exception {
        List<Seance> listeSeance = FactoryDAOPostgreSQL.getInstance().getModelSeance().getSeanceFromSport(id);
        for (Seance seance : listeSeance){
            FactoryDAOPostgreSQL.getInstance().getModelSeance().supprimerSeance(seance.getId());
        }
    }


    /**
     * Met à jour dans la base de donnée le sport
     * @param updateList List<Pair<String,Object>>, la liste du setter
     * @param id int, l'id du sport
     */
    @Override
    public Sport updateSport(List<Pair<String, Object>> updateList, int id) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).update(updateList,whereList,this.table);
            return this.getSportById(id);
        }
        catch (Exception e){
            throw new SQLException("La mise à jour du sport a échoué");
        }
    }




}
