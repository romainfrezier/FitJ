package com.fitj.dao.postgresql;

import com.fitj.classes.Exercice;
import com.fitj.dao.DAOExercice;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.dao.tools.DaoMapper;
import com.fitj.exceptions.DBProblemException;
import kotlin.Pair;
import kotlin.Triple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux exercices
 *
 * @author Etienne Tillier, Romain Frezier
 */
public class DAOExercicePostgreSQL extends DAOExercice {


    public DAOExercicePostgreSQL() {
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    /**
     * @param id int, l'id de l'exercice
     * @return l'exercice dans la base de donnée avec l'id rentré en paramètre
     * @throws Exception si une erreur SQL est détectée
     */
    public Exercice getExerciceById(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", id));
        try{
            DaoMapper exerciceData = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
            List<Map<String,Object>> result = exerciceData.getListeData();
            if (!result.isEmpty()){
                Map<String,Object> data = result.get(0);
                return new Exercice(((Long)data.get("id")).intValue(), (String) data.get("nom"), (String)data.get("description"));
            }
            else{
                throw new DBProblemException("Aucun exercice avec cet id n'existe");
            }
        }
        catch (Exception e){
            throw new DBProblemException("Il y a eu un problème lors de la recherche de cet exercice");
        }
    }

    @Override
    public List<Exercice> getAllExercice() throws Exception{
        return this.getAllExerciceWhere(new ArrayList<>());
    }

    @Override
    public List<Exercice> getAllExerciceWhere(List<Pair<String, Object>> whereList) throws Exception {
        List<Exercice> listExercice = new ArrayList<>();
        List<Triple<String,String,String>> joinList = new ArrayList<>();
        joinList.add(new Triple<>("exercicemateriel","idexercice", "exercice.id"));
        joinList.add(new Triple<>("seanceexercice","idexercice", "exercice.id"));
        joinList.add(new Triple<>("seance","id", "seanceexercice.idseance"));
        try {
            DaoMapper resultSet = ((MethodesPostgreSQL)this.methodesBD).selectJoin(joinList,whereList,this.table);
            List<Map<String,Object>> listData = resultSet.getListeData();
            List<Map<Integer,Object>> listDataIndex = resultSet.getListeDataIndex();
            int idCurrentExercice = -1;
            int i = 0;
            while (i < listData.size()){
                Map<String,Object> data = listData.get(i);
                Map<Integer,Object> dataIndex = listDataIndex.get(i);
                if (idCurrentExercice != ((Long)dataIndex.get(1)).intValue()){
                    listExercice.add(new Exercice(((Long)dataIndex.get(1)).intValue(), (String) dataIndex.get(2), (String) dataIndex.get(3)));
                    idCurrentExercice =  ((Long)dataIndex.get(1)).intValue();
                }
                i++;
            }
            return listExercice;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("Impossible de récupérer tous les exercices");
        }
    }

    /**
     * Creer un exercice dans la base de donnée
     * @param nom String, le nom de l'exercice
     * @param description String, la description de l'exercice
     * @throws Exception si une erreur SQL est détectée
     */
    @Override
    public Exercice createExercice(String nom, String description) throws Exception {
        List<Pair<String,Object>> data = new ArrayList<>();
        data.add(new Pair<>("nom", nom));
        data.add(new Pair<>("description", description));
        try {
            int id = ((MethodesPostgreSQL)this.methodesBD).insert(data, this.table);
            return this.getExerciceById(id);
        }
        catch (Exception e){
            throw new DBProblemException("La création de l'exercice a échoué");
        }

    }

    /**
     * Supprimer l'exercice de la base de donnée
     * @param id int, l'id de l'exercice
     * @throws Exception si une erreur SQL est détectée
     */
    @Override
    public void supprimerExercice(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        List<Pair<String, Object>> whereOtherTableList = new ArrayList<>();
        whereOtherTableList.add(new Pair<>("idexercice",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"exercicemateriel");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"seanceexercice");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList,this.table);
        }
        catch (Exception e){
            throw new DBProblemException("La suppression de l'exercice a échoué");
        }

    }

    /**
     * Met à jour l'exercice dans la base de donnée
     * @param updateList List<Pair<String,Object>>, la liste des attributs à mettre à jour dans la base de donnée
     * @param id int, l'id de l'exercice
     * @return l'exercice mis à jour
     * @throws Exception si une erreur SQL est détectée
     */
    @Override
    public Exercice updateExercice(List<Pair<String, Object>> updateList, int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).update(updateList,whereList,this.table);
            return this.getExerciceById(id);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour de l'exercice a échoué");
        }
    }

    @Override
    public List<Exercice> getAllExerciceByCoachId(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("seance.idcoach", id));
        return this.getAllExerciceWhere(whereList);
    }


}
