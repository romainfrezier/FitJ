package com.fitj.dao.postgresql;

import com.fitj.classes.*;
import com.fitj.dao.DAOSeance;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.dao.tool.DaoMapper;

import com.fitj.enums.Sexe;
import com.fitj.exceptions.DBProblemException;

import kotlin.Pair;
import kotlin.Triple;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux séances
 *
 * @author Etienne Tillier, Romain Frezier
 */
public class DAOSeancePostgreSQL extends DAOSeance {

    public DAOSeancePostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    /**
     * @param id int, l'id de la séance
     * @return la séance dans la base de donnée contenant l'id rentré en paramètre
     * @throws Exception si une erreur SQL est détectée
     */
    @Override
    public Seance getSeanceById(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", id));
        try {
            DaoMapper seanceBD = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
            List<Map<String,Object>> result = seanceBD.getListeData();
            if (!result.isEmpty()) {
                Map<String, Object> data = result.get(0);
                Coach coach = (Coach) FactoryDAOPostgreSQL.getInstance().getDAOClient().getClientById(((Long)data.get("idcoach")).intValue());
                Sport sport = FactoryDAOPostgreSQL.getInstance().getDAOSport().getSportById(((Long)data.get("idsport")).intValue());
                List<Triple<Exercice, Integer, Integer>> listeExercice = this.getExercices(id);
                return new Seance(((Long)data.get("id")).intValue(), (String)data.get("nom"), (String)data.get("description"),((Number)data.get("prix")).doubleValue(),coach,sport,listeExercice);
            }
            else {
                throw new DBProblemException("Aucune séance avec cet id n'existe");
            }
        }
        catch(Exception e){
            throw new DBProblemException("La sélection de la séance a échoué");
        }

    }

    /**
     * @param nom String, le nom de la séance
     * @return l'id de la séance
     * @throws Exception si une erreur SQL est détectée
     */
    public int getIdSeanceByNom(String nom) throws Exception{
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("nom", nom));
        try {
            DaoMapper seanceBD = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
            List<Map<String,Object>> result = seanceBD.getListeData();
            if (!result.isEmpty()) {
                Map<String, Object> data = result.get(0);
                return ((Long) data.get("id")).intValue();
            }
            else {
                throw new DBProblemException("Il n'y a pas de séance avec ce nom");
            }
        }
        catch (Exception e){
            throw new DBProblemException("La sélection de la séance a échoué");
        }

    }


    /**
     * @param id int, l'id de la séance
     * @return la liste des exercices de la séance
     * @throws Exception si la sélection des exercices a échoué
     */
    public List<Triple<Exercice, Integer, Integer>> getExercices(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("seanceexercice.idseance", id));
        List<Triple<String, String, String>> joinList = new ArrayList<>();
        joinList.add(new Triple<>("seanceexercice", "idexercice", "exercice.id"));
        try {
            DaoMapper resultSet =((MethodesPostgreSQL)this.methodesBD).selectJoin(joinList,whereList,"exercice");
            List<Map<String, Object>> listData = resultSet.getListeData();
            int i = 0;
            List<Triple<Exercice, Integer, Integer>> listeExercice = new ArrayList<>();
            while (i < listData.size()) {
                Map<String, Object> data = listData.get(i);
                listeExercice.add(new Triple<>(new Exercice(((Long)data.get("id")).intValue(),(String) data.get("nom"),(String)data.get("description")),((Long)data.get("nbrepetition")).intValue(), ((Long)data.get("nbserie")).intValue()));
            }
            return listeExercice;
        }
        catch (Exception e) {
            throw new DBProblemException("La selection de tous les exercices de la séance a échoué !");
        }

    }

    @Override
    public List<Seance> getAllSeancesFromClient(int idClient) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("commande.idclient",idClient));
        return getAllSeancesWhere(whereList);
    }

    @Override
    public List<Seance> getAllSeancesFromCoach(int idCoach) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("seance.idcoach",idCoach));
        return getAllSeancesWhere(whereList);
    }

    /**
     * Créer une séance dans la base de donnée
     * @param nom String, le nom de la séance
     * @param description String, la description de la séance
     * @param prix double, le prix de la séance
     * @param coach Coach, le coach de la séance
     * @param sport Sport, le sport de la séance
     * @param exercices List<Exercice>, la liste des exercices de la séance
     * @throws Exception si la création de la séance a échoué
     */
    public Seance createSeance(String nom, String description, double prix, Coach coach, Sport sport, List<Triple<Exercice, Integer, Integer>> exercices) throws Exception{
        List<Pair<String,Object>> listeInsert = new ArrayList<>();
        listeInsert.add(new Pair<>("nom",nom));
        listeInsert.add(new Pair<>("description",description));
        listeInsert.add(new Pair<>("prix",prix));
        listeInsert.add(new Pair<>("idcoach",coach.getId()));
        listeInsert.add(new Pair<>("idsport",sport.getId()));
        try {
            int idSeance = ((MethodesPostgreSQL)this.methodesBD).insert(listeInsert, this.table);
            for (Triple<Exercice, Integer, Integer> exercice : exercices){
                List<Pair<String,Object>> listeInsertExercice = new ArrayList<>();
                listeInsertExercice.add(new Pair<>("idexercice",exercice.getFirst().getId()));
                listeInsertExercice.add(new Pair<>("idseance",idSeance));
                listeInsertExercice.add(new Pair<>("nbrepetition", exercice.getSecond()));
                listeInsertExercice.add(new Pair<>("nbserie", exercice.getThird()));
                ((MethodesPostgreSQL)this.methodesBD).insert(listeInsertExercice, "seanceexercice");
            }
            return this.getSeanceById(idSeance);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("La création de la séance a échoué");
        }
    }

    /**
     * @param idSport int, l'id du sport
     * @return la liste des séances qui font référence à ce sport
     * @throws Exception si la sélection des séances a échoué
     */
    @Override
    public List<Seance> getSeanceFromSport(int idSport) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("idsport",idSport));
        try {
            DaoMapper resultSet = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
            List<Map<String, Object>> listData = resultSet.getListeData();
            int i = 0;
            List<Seance> listeSeances = new ArrayList<>();
            while (i < listData.size()) {
                Map<String, Object> data = listData.get(i);
                listeSeances.add(this.getSeanceById(((Long)data.get("id")).intValue()));
            }
            return listeSeances;
        }
        catch (Exception e){
            throw new DBProblemException("La séléction des séance en fonction du sport a échoué");
        }
    }

    @Override
    public List<Seance> getAllSeances() throws Exception {
        return this.getAllSeancesWhere(new ArrayList<>());
    }

    public List<Seance> getAllSeancesWhere(List<Pair<String,Object>> whereList) throws Exception {
        List<Seance> listeSeances = new ArrayList<>();
        List<Triple<String,String,String>> joinList = new ArrayList<>();
        joinList.add(new Triple<>("client","id", "seance.idcoach"));
        joinList.add(new Triple<>("programmesportseance","idseance", "seance.id"));
        joinList.add(new Triple<>("packseance","idseance", "seance.id"));
        joinList.add(new Triple<>("seanceexercice","idseance", "seance.id"));
        joinList.add(new Triple<>("avisseance","idseance", "seance.id"));
        joinList.add(new Triple<>("commandeseance","idseance", "seance.id"));
        joinList.add(new Triple<>("commande","id", "commandeseance.idcommande"));
        try {
            DaoMapper resultSet = ((MethodesPostgreSQL) this.methodesBD).selectJoin(joinList, whereList, this.table);
            List<Map<String, Object>> listData = resultSet.getListeData();
            List<Map<Integer, Object>> listDataIndex = resultSet.getListeDataIndex();
            int idCurrentSeance = -1;
            int i = 0;
            while (i < listData.size()) {
                /*
                 * index 1 = id de la séance
                 * index 2 = nom de la séance
                 * index 5 = id du coach
                 * index 9 = nom du coach
                 */
                Map<String, Object> data = listData.get(i);
                Map<Integer, Object> dataIndex = listDataIndex.get(i);
                if (idCurrentSeance != ((Long) dataIndex.get(1)).intValue()) {
                    Coach coach = new Coach((String)data.get("mail"), (String)dataIndex.get(9), ((Number)data.get("poids")).doubleValue(), (String)data.get("photo"), ((Long)data.get("taille")).intValue(), Sexe.getSexe((String) data.get("sexe")), (String)data.get("password"), ((Long) dataIndex.get(5)).intValue(), (boolean) data.get("isbanned"));
                    listeSeances.add(new Seance(((Long) dataIndex.get(1)).intValue(), (String)dataIndex.get(2), (String)data.get("description"), ((Number)data.get("prix")).doubleValue(), coach));
                    idCurrentSeance = ((Long) dataIndex.get(1)).intValue();
                }
                i++;
            }

            return listeSeances;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("Impossible de récupérer toutes les séances");
        }
    }

    /**
     * Supprimer la séance de la base de donnée
     * @param id int, l'id de la séance
     * @throws Exception si la suppression de la séance a échoué
     */
    @Override
    public void supprimerSeance(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        List<Pair<String, Object>> whereOtherTableList = new ArrayList<>();
        whereOtherTableList.add(new Pair<>("idseance",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"commandeseance");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"seanceexercice");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"avisseance");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"packseance");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"programmesportseance");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList,this.table);
        }
        catch(Exception e){
            throw new DBProblemException("La suppresion de la séance a échoué");
        }

    }

    /**
     * @param updateList List<Pair<String,Object>>, la liste des objet à modifié dans la table pour la séance
     * @param id int, l'id de la séance
     * @return l'object de type Seance qui a été mis à jour dans la base de donnée
     * @throws Exception si la mise à jour de la séance a échoué
     */
    @Override
    public Seance updateSeance(List<Pair<String, Object>> updateList, int id) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).update(updateList,whereList,this.table);
            return this.getSeanceById(id);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour de la séance a échoué");
        }
    }

    @Override
    public void ajouterExercice(Exercice exercice, int nbrepetition, int nbserie, int id) throws Exception {
        List<Pair<String, Object>> insertList = new ArrayList<>();
        insertList.add(new Pair<>("idexercice", exercice.getId()));
        insertList.add(new Pair<>("idseance", id));
        insertList.add(new Pair<>("nbrepetition", nbrepetition));
        insertList.add(new Pair<>("nbserie", nbserie));
        try {
            ((MethodesPostgreSQL)this.methodesBD).insert(insertList, "seanceexercice");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("L'ajout de l'exercice dans cette séance a échoué");
        }
    }

    @Override
    public void supprimerExercice(Exercice exercice, int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("idexercice", exercice.getId()));
        whereList.add(new Pair<>("idseance", id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList, "seanceexercice");
        }
        catch (Exception e){
            throw new DBProblemException("La suppression de l'exercice dans cette séance a échoué");
        }
    }
}
