package com.fitj.dao.postgresql;

import com.fitj.classes.*;
import com.fitj.dao.DAOProgrammeNutrition;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.enums.ProgrammeType;
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
            return FactoryDAOPostgreSQL.getInstance().getModelRecette().getAllRecettes(whereList);
        }
        catch (Exception e) {
            throw new SQLException("La selection de tous les exercices de la séance a échoué !");
        }
    }

    @Override
    public ProgrammeNutrition updateProgrammeNutrition(List<Pair<String, Object>> updateList, int id) throws Exception {
        return null;
    }

    @Override
    public void supprimerProgrammeNutrition(int id) throws Exception {

    }

    @Override
    public List<ProgrammeNutrition> getAllProgrammeNutrition(List<Pair<String, Object>> whereList) throws Exception {
        return null;
    }
}
