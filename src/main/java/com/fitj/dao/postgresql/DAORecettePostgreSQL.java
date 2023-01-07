package com.fitj.dao.postgresql;

import com.fitj.classes.*;
import com.fitj.dao.DAORecette;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.dao.tools.DaoMapper;
import com.fitj.enums.Sexe;
import com.fitj.exceptions.DBProblemException;
import com.fitj.interfaces.Ingredient;
import kotlin.Pair;
import kotlin.Triple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux recettes
 *
 * @author Etienne Tillier, Romain Frezier
 */
public class DAORecettePostgreSQL extends DAORecette {

    /**
     * Constructeur
     */
    public DAORecettePostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    /**
     * Crée une recette dans la base de données
     * @param nom String, le nom de la recette
     * @param coach Coach, le coach qui a créé la recette
     * @param ingredients List<Ingredient>, la liste des ingrédients de la recette
     * @return la recette créée
     * @throws Exception
     */
    @Override
    public Recette createRecette(String nom,Coach coach, List<Ingredient> ingredients) throws Exception {
        List<Pair<String,Object>> listeInsert = new ArrayList<>();
        listeInsert.add(new Pair<>("nom",nom));
        listeInsert.add(new Pair<>("idcoach",coach.getId()));
        try {
            int idRecette = ((MethodesPostgreSQL)this.methodesBD).insert(listeInsert, this.table);
            for (Ingredient ingredient : ingredients){
                List<Pair<String,Object>> listeInsertIngredient = new ArrayList<>();
                if (ingredient instanceof Aliment){
                    listeInsertIngredient.add(new Pair<>("idaliment",(ingredient).getId()));
                    listeInsertIngredient.add(new Pair<>("idrecette",idRecette));
                    ((MethodesPostgreSQL)this.methodesBD).insert(listeInsertIngredient, "recettealiment");
                }
                else if (ingredient instanceof Recette){
                    listeInsertIngredient.add(new Pair<>("idrecette1",idRecette));
                    listeInsertIngredient.add(new Pair<>("idrecette2",(ingredient).getId()));
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

    /**
     * Récupère une recette dans la base de données
     * @param id int, l'id de la recette
     * @return la recette récupérée
     * @throws Exception
     */
    @Override
    public Recette getRecetteById(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", id));
        try {
            DaoMapper recetteBD = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
            List<Map<String,Object>> result = recetteBD.getListeData();
            if (!result.isEmpty()) {
                Map<String, Object> data = result.get(0);
                Coach coach = (Coach) FactoryDAOPostgreSQL.getInstance().getDAOClient().getClientById(((Long)data.get("idcoach")).intValue());
                List<Ingredient> listeIngredients = getIngredientsFromRecette(id);
                return new Recette(((Long)data.get("id")).intValue(),(String)data.get("nom"),coach, (ArrayList)listeIngredients);
            }
            else {
                throw new DBProblemException("Aucune recette avec cet id n'existe");
            }
        }
        catch(Exception e){
            throw new DBProblemException("La sélection de la recette a échoué");
        }
    }

    /**
     * Recupere une recette mais sans les ingrédients
     * @param id int, l'id de la recette
     * @return la recette récupérée
     * @throws Exception
     */
    public Recette getRecetteByIdWithoutIngredients(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", id));
        try {
            DaoMapper recetteBD = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
            List<Map<String,Object>> result = recetteBD.getListeData();
            if (!result.isEmpty()) {
                Map<String, Object> data = result.get(0);
                Coach coach = (Coach) FactoryDAOPostgreSQL.getInstance().getDAOClient().getClientById(((Long)data.get("idcoach")).intValue());
                return new Recette(((Long)data.get("id")).intValue(),(String)data.get("nom"),coach);
            }
            else {
                throw new DBProblemException("Aucune recette avec cet id n'existe");
            }
        }
        catch(Exception e){
            throw new DBProblemException("La sélection de la recette a échoué");
        }
    }

    /**
     * Récupère les ingrédients d'une recette
     * @param id int, l'id de la recette
     * @return la liste des ingrédients de la recette
     * @throws Exception
     */
    public List<Ingredient> getIngredientsFromRecette(int id) throws Exception{
        List<Ingredient> listeAliment = new ArrayList<>();
        List<Pair<String, Object>> whereListAliment = new ArrayList<>();
        whereListAliment.add(new Pair<>("idrecette", id));
        List<Pair<String, Object>> whereListRecette1 = new ArrayList<>();
        whereListRecette1.add(new Pair<>("idrecette1", id));
        try {
            try {
                DaoMapper alimentsBD = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereListAliment, "recettealiment");
                List<Map<String,Object>> result = alimentsBD.getListeData();
                int i = 0;
                while (i < result.size()){
                    Map<String, Object> data = result.get(i);
                    listeAliment.add(FactoryDAOPostgreSQL.getInstance().getDAOAliment().getAlimentById(((Long)data.get("idaliment")).intValue()));
                    i++;
                }
            }
            catch (Exception e){
                e.printStackTrace();
                throw new DBProblemException("La sélection des ingrédients de la recette a échoué");
            }
            try {
                DaoMapper recettesBD = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereListRecette1, "recetterecette");
                List<Map<String,Object>> result = recettesBD.getListeData();
                int i = 0;
                while (i < result.size()){
                    Map<String, Object> data = result.get(i);
                    listeAliment.add(getRecetteByIdWithoutIngredients(((Long)data.get("idrecette2")).intValue()));
                    i++;
                }
            }
            catch (Exception e){
                e.printStackTrace();
                throw new DBProblemException("La sélection des ingrédients de la recette a échoué");
            }
            return listeAliment;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("Les ingrédients de la recette n'ont pas pu être trouvés");
        }
    }


    /**
     * Supprime une recette de la base de données
     * @param id int, l'id de la recette
     * @throws Exception
     */
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

    /**
     * Récupère toutes les recettes de la base de données
     * @return la liste des recettes
     * @throws Exception
     */
    @Override
    public List<Recette> getAllRecettes() throws Exception{
        return this.getAllRecettesWhere(new ArrayList<>());
    }



    /**
     * Récupère toutes les recettes de la base de données qui vérifient les conditions de la liste whereList
     * @param whereList List<Pair<String, Object>>, la liste des conditions
     * @return la liste des recettes
     * @throws Exception
     */
    @Override
    public List<Recette> getAllRecettesWhere(List<Pair<String,Object>> whereList) throws Exception {
        List<Recette> listeRecettes = new ArrayList<>();
        List<Triple<String,String,String>> joinList = new ArrayList<>();
        joinList.add(new Triple<>("client","id", "recette.idcoach"));
        joinList.add(new Triple<>("programmenutritionrecette","idrecette", "recette.id"));
        joinList.add(new Triple<>("recetterecette","idrecette1", "recette.id"));
        joinList.add(new Triple<>("recettealiment","idrecette", "recette.id"));
        try {
            DaoMapper resultSet = ((MethodesPostgreSQL) this.methodesBD).selectJoin(joinList, whereList, this.table);
            List<Map<String, Object>> listData = resultSet.getListeData();
            List<Map<Integer, Object>> listDataIndex = resultSet.getListeDataIndex();
            int idCurrentRecette = -1;
            int i = 0;
            while (i < listData.size()) {
                /*
                 * index 1 = id de la recette
                 * index 2 = nom de la recette
                 * index 4 = id du coach
                 * index 6 = nom du coach
                 */
                Map<String, Object> data = listData.get(i);
                Map<Integer, Object> dataIndex = listDataIndex.get(i);
                if (idCurrentRecette != ((Long)dataIndex.get(1)).intValue()) {
                    Coach coach = new Coach((String) data.get("mail"), (String) dataIndex.get(6), ((Number)data.get("poids")).doubleValue(), (String) data.get("photo"), ((Long) data.get("taille")).intValue(), Sexe.getSexe((String) data.get("sexe")), (String) data.get("password"), ((Long)dataIndex.get(4)).intValue(), (boolean) data.get("isbanned"));
                    listeRecettes.add(new Recette(((Long)dataIndex.get(1)).intValue(), (String) dataIndex.get(2), coach));
                    idCurrentRecette = ((Long)dataIndex.get(1)).intValue();
                }
                i++;
            }
            return listeRecettes;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("Impossible de récupérer toutes les recettes");
        }
    }

    /**
     * Modifie une recette dans la base de données
     * @param updateList List<Pair<String, Object>>, la liste des attributs à mettre à jour dans la base de donnée
     * @param id int, l'id de la recette
     * @return la recette modifiée
     * @throws Exception
     */
    @Override
    public Recette updateRecette(List<Pair<String, Object>> updateList, int id) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).update(updateList,whereList,this.table);
            return this.getRecetteById(id);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("La mise à jour de la recette a échoué");
        }
    }

    /**
     * Récupère toute les recettes d'un coach
     * @param idCoach int, l'id du coach
     * @return la liste des recettes du coach
     * @throws Exception
     */
    @Override
    public List<Recette> getAllRecettesByCoach(int idCoach) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("recette.idcoach",idCoach));
        return this.getAllRecettesWhere(whereList);
    }

    /**
     * Ajoute un ingrédient à une recette
     * @param ingredient IsIngredient, l'ingrédient à ajouter à la recette
     * @param id int, l'id de la recette
     * @throws Exception
     */
    @Override
    public void ajouterIngredient(Ingredient ingredient, int id) throws Exception {
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

    /**
     * Ajoute une recette à une recette
     * @param recette Recette, la recette à ajouter à la recette
     * @param id int, l'id de la recette
     * @throws Exception
     */
    public void ajouterRecette(Recette recette, int id) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                List<Pair<String, Object>> insertList = new ArrayList<>();
                insertList.add(new Pair<>("idrecette1", id));
                insertList.add(new Pair<>("idrecette2", recette.getId()));
                ((MethodesPostgreSQL) this.methodesBD).insert(insertList, "recetterecette");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    throw new DBProblemException("L'ajout de la recette dans la recette a échoué");
                } catch (DBProblemException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

/**
     * Ajoute un aliment à une recette
     * @param aliment Aliment, l'aliment à ajouter à la recette
     * @param id int, l'id de la recette
     * @throws Exception
     */
    public void ajouterAliment(Aliment aliment, int id) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                List<Pair<String, Object>> insertList = new ArrayList<>();
                insertList.add(new Pair<>("idrecette", id));
                insertList.add(new Pair<>("idaliment", aliment.getId()));
                ((MethodesPostgreSQL) this.methodesBD).insert(insertList, "recettealiment");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    throw new DBProblemException("L'ajout de l'aliment dans la recette a échoué");
                } catch (DBProblemException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    /**
     * Supprime un ingrédient d'une recette
     * @param ingredient IsIngredient, l'ingrédient à supprimer de la recette
     * @param id int, l'id de la recette
     * @throws Exception
     */
    @Override
    public void  supprimerIngredient(Ingredient ingredient, int id) throws Exception {
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

    /**
     * Supprime une recette d'une recette
     * @param recette Recette, la recette à supprimer de la recette
     * @param id int, l'id de la recette
     * @throws Exception
     */
    public void supprimerRecette(Recette recette, int id) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                List<Pair<String, Object>> whereList = new ArrayList<>();
                whereList.add(new Pair<>("idrecette1", id));
                whereList.add(new Pair<>("idrecette2", recette.getId()));
                ((MethodesPostgreSQL) this.methodesBD).delete(whereList, "recetterecette");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    throw new DBProblemException("La suppression de la recette dans la recette a échoué");
                } catch (DBProblemException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    /**
     * Supprime un aliment d'une recette
     * @param aliment Aliment, l'aliment à supprimer de la recette
     * @param id int, l'id de la recette
     * @throws Exception
     */
    public void supprimerAliment(Aliment aliment, int id) throws DBProblemException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                List<Pair<String, Object>> whereList = new ArrayList<>();
                whereList.add(new Pair<>("idrecette", id));
                whereList.add(new Pair<>("idaliment", aliment.getId()));
                ((MethodesPostgreSQL) this.methodesBD).delete(whereList, "recettealiment");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    throw new DBProblemException("La suppression de l'aliment dans la recette a échoué");
                } catch (DBProblemException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

}
