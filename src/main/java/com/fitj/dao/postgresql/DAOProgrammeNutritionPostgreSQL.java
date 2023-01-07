package com.fitj.dao.postgresql;

import com.fitj.classes.*;
import com.fitj.dao.DAOProgrammeNutrition;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.dao.tools.DaoMapper;
import com.fitj.enums.ProgrammeType;
import com.fitj.enums.Sexe;
import com.fitj.exceptions.DBProblemException;
import kotlin.Pair;
import kotlin.Triple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux exercices
 *
 * @author Etienne Tillier, Romain Frezier
 */
public class DAOProgrammeNutritionPostgreSQL extends DAOProgrammeNutrition {

    /**
     * Constructeur
     */
    public DAOProgrammeNutritionPostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    /**
     * Crée un programme de nutrition dans la base de données
     * @param nom String, le nom du programme
     * @param description String, la description du programme
     * @param prix double, le prix du programme
     * @param coach Coach, le coach qui a créé le programme
     * @param listeRecette List<Recette>, la liste des recettes du programme
     * @param type ProgrammeType, le type du programme
     * @param nbMois int, le nombre de mois du programme
     * @return le programme de nutrition créé
     * @throws Exception
     */
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
                listeInsertRecette.add(new Pair<>("idrecette",recette.getId()));
                ((MethodesPostgreSQL)this.methodesBD).insert(listeInsertRecette, "programmenutritionrecette");
            }
            return this.getProgrammeNutritionById(idProgramme);
        }
        catch (Exception e){
            throw new DBProblemException("La création du programme nutrition a échoué");
        }
    }

    /**
     * Récupère un programme de nutrition dans la base de données
     * @param id int, l'id du programme
     * @return le programme de nutrition
     * @throws Exception
     */
    @Override
    public ProgrammeNutrition getProgrammeNutritionById(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", id));
        try{
            DaoMapper programmeDB = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
            List<Map<String,Object>> result = programmeDB.getListeData();
            if (!result.isEmpty()) {
                Map<String, Object> data = result.get(0);
                Coach coach = (Coach) FactoryDAOPostgreSQL.getInstance().getDAOClient().getClientById(((Long)data.get("idcoach")).intValue());
                ArrayList<Recette> listeRecette = (ArrayList<Recette>) this.getRecettes(id);
                return new ProgrammeNutrition(((Long)data.get("id")).intValue(), (String) data.get("nom"), (String)data.get("description"),((Number)data.get("prix")).doubleValue(),ProgrammeType.getProgrammeType((String) data.get("type")), ((Number)data.get("nbmois")).intValue(),coach,listeRecette);
            }
            else {
                throw new DBProblemException("Aucun programme nutrition avec cet id n'existe");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            throw new DBProblemException("La sélection du programme nutrition a échoué");
        }
    }

    /**
     * Récupère tous les programmes de nutrition d'un coach
     * @param coachId int, l'id du coach
     * @return la liste des programmes de nutrition du coach
     * @throws Exception
     */
    @Override
    public List<ProgrammeNutrition> getProgrammeNutritionByCoach(int coachId) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("programmenutrition.idcoach",coachId));
        return this.getAllProgrammeNutritionWhere(whereList);
    }

    /**
     * Met à jour un programme de nutrition dans la base de données
     * @param idProgramme int, l'id du programme à modifier
     * @param nom String, le nouveau nom du programme
     * @param description String, la nouvelle description du programme
     * @param prix double, le nouveau prix du programme
     * @param type ProgrammeType, le nouveau type du programme
     * @param nbMois int, le nouveau nombre de mois du programme
     * @return le programme de nutrition modifié
     * @throws Exception
     */
    @Override
    public ProgrammeNutrition updateProgrammeNutrition(int idProgramme, String nom, String description, double prix, ProgrammeType type, int nbMois) throws Exception {
        List<Pair<String,Object>> listeUpdate = new ArrayList<>();
        listeUpdate.add(new Pair<>("nom",nom));
        listeUpdate.add(new Pair<>("description",description));
        listeUpdate.add(new Pair<>("prix",prix));
        listeUpdate.add(new Pair<>("type",ProgrammeType.getProgrammeType(type)));
        listeUpdate.add(new Pair<>("nbMois",nbMois));
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",idProgramme));
        try {
            ((MethodesPostgreSQL)this.methodesBD).update(listeUpdate, whereList, this.table);
            return this.getProgrammeNutritionById(idProgramme);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour du programme nutrition a échoué");
        }
    }

    /**
     * Récupère toutes les recettes associées à un programme de nutrition donné.
     *
     * @param id L'ID du programme de nutrition pour lequel récupérer les recettes
     * @return La liste de toutes les recettes associées au programme de nutrition
     * @throws Exception Si une erreur survient lors de la récupération des recettes associées au programme de nutrition
     */
    public List<Recette> getRecettes(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("programmenutritionrecette.idprogramme", id));
        try {
            return FactoryDAOPostgreSQL.getInstance().getDAORecette().getAllRecettesWhere(whereList);
        }
        catch (Exception e) {
            throw new DBProblemException("La selection de toutes les recettes du programme a échoué !");
        }
    }

    /**
     * Met à jour un programme de nutrition dans la base de données.
     *
     * @param updateList La liste des modifications à effectuer sur le programme de nutrition
     * @param id L'ID du programme de nutrition à mettre à jour
     * @return Le programme de nutrition mis à jour
     * @throws Exception Si une erreur survient lors de la mise à jour du programme de nutrition
     */
    @Override
    public ProgrammeNutrition updateProgrammeNutrition(List<Pair<String, Object>> updateList, int id) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).update(updateList,whereList,this.table);
            return this.getProgrammeNutritionById(id);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour du programme nutrition a échoué");
        }
    }

    /**
     * Supprime un programme nutrition de la base de données.
     *
     * @param id L'identifiant du programme nutrition à supprimer.
     * @throws Exception Si la suppression échoue.
     */
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
            throw new DBProblemException("La suppresion du programme nutrition a échoué");
        }
    }

    /**
     * Récupère tous les programmes de nutrition de la base de données.
     * @return La liste de tous les programmes de nutrition de la base de données.
     * @throws Exception
     */
    @Override
    public List<ProgrammeNutrition> getAllProgrammeNutrition() throws Exception{
        return this.getAllProgrammeNutritionWhere(new ArrayList<>());
    }

    /**
     * Récupère tous les programmes nutrition de la base de données qui répondent aux critères spécifiés.
     *
     * @param whereList La liste des critères à vérifier pour chaque programme nutrition.
     * @return Une liste de tous les programmes nutrition qui répondent aux critères spécifiés.
     * @throws Exception Si la récupération échoue.
     */
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
        joinList.add(new Triple<>("commandeprogrammenutrition","idprogramme", "programmenutrition.id"));
        joinList.add(new Triple<>("commande","id", "commandeprogrammenutrition.idcommande"));
        try {
            DaoMapper resultSet = ((MethodesPostgreSQL) this.methodesBD).selectJoin(joinList, whereList, this.table);
            List<Map<String, Object>> listData = resultSet.getListeData();
            List<Map<Integer, Object>> listDataIndex = resultSet.getListeDataIndex();
            int idCurrentProgramme = -1;
            int i = 0;
            while (i < listData.size()) {
                /*
                 * index 1 = id de la séance
                 * index 2 = nom de la séance
                 * index 7 = id du coach
                 * index 10 = nom du coach
                 */
                Map<String, Object> data = listData.get(i);
                Map<Integer, Object> dataIndex = listDataIndex.get(i);
                if (idCurrentProgramme != ((Number)dataIndex.get(1)).intValue()) {
                    Coach coach = new Coach((String)data.get("mail"), (String)dataIndex.get(10), ((Number)data.get("poids")).doubleValue(), (String)data.get("photo"), ((Long)data.get("taille")).intValue(), Sexe.getSexe((String) data.get("sexe")), (String) data.get("password"), ((Number)dataIndex.get(7)).intValue(), (boolean) data.get("isbanned"));
                    listeProgrammes.add(new ProgrammeNutrition(((Number)dataIndex.get(1)).intValue(), (String)dataIndex.get(2), (String) data.get("description"), ((Number)data.get("prix")).doubleValue(), ProgrammeType.getProgrammeType((String)data.get("type")), ((Number)data.get("nbmois")).intValue(), coach));
                    idCurrentProgramme = ((Number)dataIndex.get(1)).intValue();
                }
                i++;
            }
            return listeProgrammes;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("Impossible de récupérer tous les programmes nutrition");
        }
    }

    /**
     * Ajoute une recette à un programme nutrition dans la base de données.
     *
     * @param recette La recette à ajouter au programme nutrition.
     * @param id L'identifiant du programme nutrition auquel ajouter la recette.
     * @throws Exception Si l'ajout échoue.
     */
    @Override
    public void ajouterRecetteProgramme(Recette recette, int id) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                List<Pair<String, Object>> insertList = new ArrayList<>();
                insertList.add(new Pair<>("idrecette", recette.getId()));
                insertList.add(new Pair<>("idprogramme", id));
                ((MethodesPostgreSQL) this.methodesBD).insert(insertList, "programmenutritionrecette");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Supprime une recette d'un programme nutrition dans la base de données.
     *
     * @param recette La recette à supprimer du programme nutrition.
     * @param id L'identifiant du programme nutrition duquel supprimer la recette.
     * @throws Exception Si la suppression échoue.
     */
    @Override
    public void supprimerRecetteProgramme(Recette recette, int id) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            List<Pair<String, Object>> whereList = new ArrayList<>();
            whereList.add(new Pair<>("idrecette", recette.getId()));
            whereList.add(new Pair<>("idprogramme", id));
            try {
                ((MethodesPostgreSQL) this.methodesBD).delete(whereList, "programmenutritionrecette");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Récupère tous les programmes nutrition achetés par un client donné.
     *
     * @param idClient L'identifiant du client pour lequel récupérer les programmes nutrition.
     * @return Une liste de tous les programmes nutrition achetés par le client.
     * @throws Exception Si la récupération échoue.
     */
    @Override
    public List<ProgrammeNutrition> getAllProgrammesNutritionsByClient(int idClient) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("commande.idclient",idClient));
        return this.getAllProgrammeNutritionWhere(whereList);
    }
}
