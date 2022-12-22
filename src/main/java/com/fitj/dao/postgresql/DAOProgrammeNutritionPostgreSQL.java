package com.fitj.dao.postgresql;

import com.fitj.classes.*;
import com.fitj.dao.DAOProgrammeNutrition;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.enums.ProgrammeType;
import com.fitj.enums.Sexe;
import com.fitj.interfaces.IsIngredient;
import kotlin.Pair;
import kotlin.Triple;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOProgrammeNutritionPostgreSQL extends DAOProgrammeNutrition {

    public DAOProgrammeNutritionPostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    @Override
    public ProgrammeNutrition createProgrammeNutrition(String nom, String description, double prix, ProgrammeType type, int nbMois, Coach coach, ArrayList<Recette> listeRecette) throws Exception {
        List<Pair<String,Object>> listeInsert = new ArrayList<>();
        listeInsert.add(new Pair<>("nom",nom));
        listeInsert.add(new Pair<>("description",description));
        listeInsert.add(new Pair<>("prix",prix));
        listeInsert.add(new Pair<>("type",ProgrammeType.getProgrammeType(type)));
        listeInsert.add(new Pair<>("nbMois",nbMois));
        listeInsert.add(new Pair<>("idcoach",coach.getId()));
        try {
            int idProgramme = ((MethodesPostgreSQL)this.methodesBD).insert(listeInsert, this.table);
            for (Recette recette : listeRecette){
                List<Pair<String,Object>> listeInsertRecette = new ArrayList<>();
                listeInsertRecette.add(new Pair<>("idprogramme",idProgramme));
                listeInsertRecette.add(new Pair<>("idrecette",((Recette)recette).getId()));
                ((MethodesPostgreSQL)this.methodesBD).insert(listeInsertRecette, "programmenutritionrecette");
            }
            return this.getProgrammeNutritionId(idProgramme);
        }
        catch (Exception e){
            throw new SQLException("La création du programme nutrition a échoué");
        }
    }

    @Override
    public ProgrammeNutrition getProgrammeNutritionId(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", id));
        try{
            ResultSet programmeDB = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
            if (programmeDB.next()){
                Coach coach = (Coach) FactoryDAOPostgreSQL.getInstance().getModelClient().getClientAccount(programmeDB.getInt("idcoach"));
                ArrayList<Recette> listeRecette = (ArrayList<Recette>) this.getRecettes(id);
                return new ProgrammeNutrition(programmeDB.getInt("id"), programmeDB.getString("nom"), programmeDB.getString("description"),programmeDB.getDouble("prix"),ProgrammeType.getProgrammeType(programmeDB.getString("type")), programmeDB.getInt("nbmois"),coach,listeRecette);
            }
            else {
                throw new SQLException("Aucune programme nutrition avec cet id n'existe");
            }
        }
        catch(Exception e){
            throw new SQLException("La sélection de du programme nutrition a échoué");
        }
    }

    public List<Recette> getRecettes(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("programmenutritionrecette.idprogramme", id));
        try {
            return FactoryDAOPostgreSQL.getInstance().getModelRecette().getAllRecettesWhere(whereList);
        }
        catch (Exception e) {
            throw new SQLException("La selection de toutes les recettes du programme a échoué !");
        }
    }

    @Override
    public ProgrammeNutrition updateProgrammeNutrition(List<Pair<String, Object>> updateList, int id) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).update(updateList,whereList,this.table);
            return this.getProgrammeNutritionId(id);
        }
        catch (Exception e){
            throw new SQLException("La mise à jour du programme nutrition a échoué");
        }
    }

    @Override
    public void supprimerProgrammeNutrition(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        List<Pair<String, Object>> whereOtherTableList = new ArrayList<>();
        whereOtherTableList.add(new Pair<>("idprogramme",id));
        List<Pair<String, Object>> wherePersonnalise = new ArrayList<>();
        wherePersonnalise.add(new Pair<>("idprogrammenutrition",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"avisprogrammenutrition");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"commandeprogrammenutrition");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"packprogrammenutrition");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"programmenutritionrecette");
            ((MethodesPostgreSQL)this.methodesBD).delete(wherePersonnalise,"programmepersonnaliseprogrammenutrition");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList,this.table);
        }
        catch(Exception e){
            throw new SQLException("La suppresion du programme nutrition a échoué");
        }
    }

    @Override
    public List<ProgrammeNutrition> getAllProgrammeNutrition() throws Exception{
        return this.getAllProgrammeNutritionWhere(new ArrayList<>());
    }

    @Override
    public List<ProgrammeNutrition> getAllProgrammeNutritionWhere(List<Pair<String, Object>> whereList) throws Exception {
        List<ProgrammeNutrition> listeProgrammes = new ArrayList<>();
        List<Triple<String,String,String>> joinList = new ArrayList<>();
        joinList.add(new Triple<>("client","id", "programmenutrition.idcoach"));
        joinList.add(new Triple<>("programmenutritionrecette","idprogramme", "programmenutrition.id"));
        joinList.add(new Triple<>("packprogrammenutrition","idprogramme", "programmenutrition.id"));
        joinList.add(new Triple<>("programmepersonnaliseprogrammenutrition","idprogrammenutrition", "programmenutrition.id"));
        joinList.add(new Triple<>("programmesportseance","idprogramme", "programmenutrition.id"));
        joinList.add(new Triple<>("avisprogrammesportif","idprogramme", "programmenutrition.id"));
        joinList.add(new Triple<>("commandeprogrammesportif","idprogramme", "programmenutrition.id"));
        try {
            ResultSet programmeBD = ((MethodesPostgreSQL)this.methodesBD).selectJoin(joinList, whereList, this.table);
            int idCurrentProgramme = -1;
            while(programmeBD.next()){
                /**
                 * index 1 = id de la séance
                 * index 2 = nom de la séance
                 * index 7 = id du coach
                 * index 10 = nom du coach
                 */
                if (idCurrentProgramme != programmeBD.getInt(1)){
                    Coach coach = new Coach(programmeBD.getString("mail"), programmeBD.getString(10), programmeBD.getDouble("poids"), programmeBD.getString("photo"), programmeBD.getInt("taille"), Sexe.getSexe(programmeBD.getString("sexe")), programmeBD.getString("password"), programmeBD.getInt(7));
                    listeProgrammes.add(new ProgrammeNutrition(programmeBD.getInt(1), programmeBD.getString(2), programmeBD.getString("description"), programmeBD.getDouble("prix"), ProgrammeType.getProgrammeType(programmeBD.getString("type")), programmeBD.getInt("nbmois"), coach));
                    idCurrentProgramme = programmeBD.getInt(1);
                }
            }
            return listeProgrammes;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new SQLException("Impossible de récupérer tous les programmes nutrition");
        }
    }

    @Override
    public void ajouterRecetteProgramme(Recette recette, int id) throws Exception {
        List<Pair<String, Object>> insertList = new ArrayList<>();
        insertList.add(new Pair<>("idrecette", recette.getId()));
        insertList.add(new Pair<>("idprogramme", id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).insert(insertList, "programmenutritionrecette");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new SQLException("L'ajout de la recette dans ce programme nutrition a échoué");
        }
    }

    @Override
    public void supprimerRecetteProgramme(Recette recette, int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("idrecette", recette.getId()));
        whereList.add(new Pair<>("idprogramme", id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList, "programmenutritionrecette");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new SQLException("La suppression de la recette dans ce programme nutrition a échoué");
        }
    }
}
