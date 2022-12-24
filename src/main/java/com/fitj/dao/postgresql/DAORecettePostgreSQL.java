package com.fitj.dao.postgresql;

import com.fitj.classes.*;
import com.fitj.dao.DAORecette;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.enums.Sexe;
import com.fitj.exceptions.DBProblemException;
import com.fitj.interfaces.IsIngredient;
import kotlin.Pair;
import kotlin.Triple;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux recettes
 *
 * @author Etienne Tillier, Romain Frezier
 */
public class DAORecettePostgreSQL extends DAORecette {

    public DAORecettePostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    @Override
    public Recette createRecette(String nom,Coach coach, List<IsIngredient> ingredients) throws Exception {
        List<Pair<String,Object>> listeInsert = new ArrayList<>();
        listeInsert.add(new Pair<>("nom",nom));
        listeInsert.add(new Pair<>("idcoach",coach.getId()));
        try {
            int idRecette = ((MethodesPostgreSQL)this.methodesBD).insert(listeInsert, this.table);
            for (IsIngredient ingredient : ingredients){
                List<Pair<String,Object>> listeInsertIngredient = new ArrayList<>();
                if (ingredient instanceof Aliment){
                    listeInsertIngredient.add(new Pair<>("idaliment",((Aliment)ingredient).getId()));
                    listeInsertIngredient.add(new Pair<>("idrecette",idRecette));
                    ((MethodesPostgreSQL)this.methodesBD).insert(listeInsertIngredient, "recettealiment");
                }
                else if (ingredient instanceof Recette){
                    listeInsertIngredient.add(new Pair<>("idrecette1",idRecette));
                    listeInsertIngredient.add(new Pair<>("idrecette2",((Recette)ingredient).getId()));
                    ((MethodesPostgreSQL)this.methodesBD).insert(listeInsertIngredient, "recetterecette");
                }
                else {
                    throw new DBProblemException("Le type de l'ingrédient ne convient pas pour une recette");
                }

            }
            return this.getRecetteById(idRecette);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("La création de la recette a échoué");
        }
    }

    @Override
    public Recette getRecetteById(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", id));
        try{
            ResultSet recetteBD = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
            if (recetteBD.next()){
                Coach coach = (Coach) FactoryDAOPostgreSQL.getInstance().getDAOClient().getClientById(recetteBD.getInt("idcoach"));
                List<IsIngredient> listeIngredients = getIngredientsFromRecette(id);
                return new Recette(recetteBD.getInt("id"),recetteBD.getString("nom"),coach, (ArrayList)listeIngredients);
            }
            else {
                throw new DBProblemException("Aucune recette avec cet id n'existe");
            }
        }
        catch(Exception e){
            throw new DBProblemException("La sélection de la recette a échoué");
        }
    }

    public List<IsIngredient> getIngredientsFromRecette(int id) throws Exception{
        List<IsIngredient> listeAliment = new ArrayList<>();
        List<Pair<String, Object>> whereListAliment = new ArrayList<>();
        whereListAliment.add(new Pair<>("idrecette", id));
        List<Pair<String, Object>> whereListRecette1 = new ArrayList<>();
        whereListRecette1.add(new Pair<>("idrecette1", id));
        try {
            ResultSet alimentsBD = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereListAliment, "recettealiment");
            ResultSet recettesBD1 = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereListRecette1, "recetterecette");
            while (alimentsBD.next()){
                listeAliment.add(FactoryDAOPostgreSQL.getInstance().getDAOAliment().getAlimentById(alimentsBD.getInt("idaliment")));
            }
            while (recettesBD1.next()){
                listeAliment.add(this.getRecetteById(recettesBD1.getInt("idrecette2")));
            }
            return listeAliment;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("Les ingrédients de la recette n'ont pas pu être trouvés");
        }
    }


    @Override
    public void supprimerRecette(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        List<Pair<String, Object>> whereAlimentRecetteTableList = new ArrayList<>();
        whereAlimentRecetteTableList.add(new Pair<>("idrecette",id));
        List<Pair<String, Object>> whereRecetteRecetteTableList1 = new ArrayList<>();
        whereRecetteRecetteTableList1.add(new Pair<>("idrecette1",id));
        List<Pair<String, Object>> whereRecetteRecetteTableList2 = new ArrayList<>();
        whereRecetteRecetteTableList2.add(new Pair<>("idrecette2",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereAlimentRecetteTableList,"recettealiment");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereRecetteRecetteTableList1,"recetterecette");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereRecetteRecetteTableList2,"recetterecette");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList,this.table);
        }
        catch(Exception e){
            throw new DBProblemException("La suppresion de la recette a échoué");
        }
    }

    @Override
    public List<Recette> getAllRecettes() throws Exception{
        return this.getAllRecettesWhere(new ArrayList<>());
    }

    @Override
    public List<Recette> getAllRecettesWhere(List<Pair<String,Object>> whereList) throws Exception {
        List<Recette> listeRecettes = new ArrayList<>();
        List<Triple<String,String,String>> joinList = new ArrayList<>();
        joinList.add(new Triple<>("client","id", "recette.idcoach"));
        joinList.add(new Triple<>("programmenutritionrecette","idrecette", "recette.id"));
        joinList.add(new Triple<>("recetterecette","idrecette1", "recette.id"));
        joinList.add(new Triple<>("recettealiment","idrecette", "recette.id"));
        try {
            ResultSet recetteBD = ((MethodesPostgreSQL)this.methodesBD).selectJoin(joinList, whereList, this.table);
            int idCurrentRecette = -1;
            while(recetteBD.next()){
                if (idCurrentRecette != recetteBD.getInt(1)){
                    Coach coach = new Coach(recetteBD.getString("mail"), recetteBD.getString(6), recetteBD.getDouble("poids"), recetteBD.getString("photo"), recetteBD.getInt("taille"), Sexe.getSexe(recetteBD.getString("sexe")), recetteBD.getString("password"), recetteBD.getInt(4));
                    listeRecettes.add(new Recette(recetteBD.getInt(1), recetteBD.getString(2), coach));
                    idCurrentRecette = recetteBD.getInt(1);
                }
            }
            return listeRecettes;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("Impossible de récupérer toutes les recettes");
        }
    }

    @Override
    public Recette updateRecette(List<Pair<String, Object>> updateList, int id) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).update(updateList,whereList,this.table);
            return this.getRecetteById(id);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour de la recette a échoué");
        }
    }

    @Override
    public void ajouterIngredient(IsIngredient ingredient, int id) throws Exception {
        if (ingredient instanceof Recette){
            this.ajouterRecette((Recette)ingredient, id);
        }
        else if (ingredient instanceof Aliment){
            this.ajouterAliment((Aliment)ingredient, id);
        }
        else {
            throw new DBProblemException("Le type d'ingrédient est inconnu pour l'ajout dans la recette");
        }
    }

    public void ajouterRecette(Recette recette, int id) throws Exception {
        List<Pair<String, Object>> insertList = new ArrayList<>();
        insertList.add(new Pair<>("idrecette1", id));
        insertList.add(new Pair<>("idrecette2", recette.getId()));
        try {
            ((MethodesPostgreSQL)this.methodesBD).insert(insertList, "recetterecette");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("L'ajout de la recette dans cette recette a échoué");
        }
    }

    public void ajouterAliment(Aliment aliment, int id) throws Exception {
        List<Pair<String, Object>> insertList = new ArrayList<>();
        insertList.add(new Pair<>("idrecette", id));
        insertList.add(new Pair<>("idaliment", aliment.getId()));
        try {
            ((MethodesPostgreSQL)this.methodesBD).insert(insertList, "recettealiment");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("L'ajout de cet aliment dans cette recette a échoué");
        }
    }

    @Override
    public void supprimerIngredient(IsIngredient ingredient, int id) throws Exception {
        if (ingredient instanceof Recette){
            this.supprimerRecette((Recette)ingredient, id);
        }
        else if (ingredient instanceof Aliment){
            this.supprimerAliment((Aliment)ingredient, id);
        }
        else {
            throw new DBProblemException("Le type d'ingrédient est inconnu pour la suppresion dans la recette");
        }
    }

    public void supprimerRecette(Recette recette, int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("idrecette1", id));
        whereList.add(new Pair<>("idrecette2", recette.getId()));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList, "recetterecette");
        }
        catch (Exception e){
            throw new DBProblemException("La suppression de la recette dans cette recette a échoué");
        }
    }

    public void supprimerAliment(Aliment aliment, int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("idrecette", id));
        whereList.add(new Pair<>("idaliment", aliment.getId()));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList, "recettealiment");
        }
        catch (Exception e){
            throw new DBProblemException("La suppression de l'aliment dans cette recette a échoué");
        }
    }

}
