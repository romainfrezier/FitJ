package com.fitj.dao.postgresql;

import com.fitj.classes.*;
import com.fitj.dao.DAOPack;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.dao.tools.DaoMapper;
import com.fitj.enums.Sexe;
import com.fitj.exceptions.DBProblemException;
import kotlin.Pair;
import kotlin.Triple;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe qui permet d'interagir avec la base de données PostgreSQL pour ce qui fait référence aux packs.
 */
public class DAOPackPostgreSQL extends DAOPack {

    /**
     * Constructeur qui instancie les méthodes de la base de données PostgreSQL.
     */
    public DAOPackPostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    /**
     * Méthode qui crée un nouveau pack dans la base de données avec les informations passées en paramètre.
     *
     * @param nom le nom du pack à créer.
     * @param description la description du pack à créer.
     * @param prix le prix du pack à créer.
     * @param coach le coach associé au pack à créer.
     * @return le pack créé.
     * @throws Exception si la création du pack a échoué.
     */
    @Override
    public Pack createPack(String nom, String description, double prix, Coach coach) throws Exception {
        List<Pair<String,Object>> listeInsert = new ArrayList<>();
        listeInsert.add(new Pair<>("nom",nom));
        listeInsert.add(new Pair<>("description",description));
        listeInsert.add(new Pair<>("prix",prix));
        listeInsert.add(new Pair<>("idcoach",coach.getId()));
        try {
            int idPack = ((MethodesPostgreSQL)this.methodesBD).insert(listeInsert, this.table);
            return this.getPackById(idPack);
        }
        catch (Exception e){
            throw new SQLException("La création du pack a échoué");
        }
    }

    /**
     * Méthode qui met à jour le pack ayant l'identifiant passé en paramètre avec les informations passées en paramètre.
     *
     * @param updateList la liste des informations à mettre à jour.
     * @param id l'identifiant du pack à mettre à jour.
     * @return le pack mis à jour.
     * @throws Exception si la mise à jour du pack a échoué.
     */
    @Override
    public Pack updatePack(List<Pair<String, Object>> updateList, int id) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).update(updateList,whereList,this.table);
            return this.getPackById(id);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour du pack a échoué");
        }
    }

    /**
     *Supprime un pack de la base de données.
     *@param id L'identifiant unique du pack à supprimer.
     *@throws Exception si la suppression du pack a échoué.
     */

     @Override
    public void deletePack(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        List<Pair<String, Object>> whereOtherTableList = new ArrayList<>();
        whereOtherTableList.add(new Pair<>("idpack",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"avispack");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"commandepack");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"packprogrammenutrition");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"packprogrammesportif");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"packprogrammepersonnalise");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"packseance");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList,this.table);
        }
        catch(Exception e){
            throw new DBProblemException("La suppresion du pack a échoué");
        }
    }

    /**
     *Récupère un pack de la base de données à partir de son identifiant unique.
     *@param id L'identifiant unique du pack à récupérer.
     *@return Le pack correspondant à l'identifiant donné.
     *@throws Exception Si la récupération échoue.
     */
    @Override
    public Pack getPackById(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", id));
        try {
            DaoMapper packBD = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
            List<Map<String,Object>> result = packBD.getListeData();
            if (!result.isEmpty()) {
                Map<String, Object> data = result.get(0);
                Coach coach = (Coach) FactoryDAOPostgreSQL.getInstance().getDAOClient().getClientById(((Long)data.get("idcoach")).intValue());
                ArrayList<Produit> listeProduit = (ArrayList<Produit>) this.getAllProduitByPack(id);
                return new Pack(((Long)data.get("id")).intValue(), (String) data.get("nom"), (String) data.get("description"),((Number) data.get("prix")).doubleValue(),coach,listeProduit);
            }
            else {
                throw new DBProblemException("Aucun pack avec cet id n'existe");
            }
        }
        catch(Exception e){
            throw new DBProblemException("La sélection du pack a échoué");
        }
    }

    /**
     * Récupère la liste de tous les packs
     *
     * @return la liste de tous les packs
     * @throws Exception si la récupération de la liste des packs a échoué
     */
    @Override
    public List<Pack> getAllPack() throws Exception {
        return this.getAllPackWhere(new ArrayList<>());
    }

    /**
     * Cette méthode permet de récupérer tous les packs de la base de données, en respectant une liste de conditions données.
     * @param whereList une liste de conditions à respecter, sous la forme de paires (nom de colonne, valeur de la colonne)
     * @return une liste de tous les packs de la base de données respectant les conditions données
     * @throws Exception si une erreur est survenue lors de la récupération des packs
     */
    @Override
    public List<Pack> getAllPackWhere(List<Pair<String, Object>> whereList) throws Exception {
        List<Pack> listePack = new ArrayList<>();
        List<Triple<String,String,String>> joinList = new ArrayList<>();
        joinList.add(new Triple<>("client","id", "pack.idcoach"));
        joinList.add(new Triple<>("packpack","idpack1", "pack.id"));
        joinList.add(new Triple<>("packprogrammenutrition","idpack", "pack.id"));
        joinList.add(new Triple<>("packprogrammesportif","idpack", "pack.id"));
        joinList.add(new Triple<>("packseance","idpack", "pack.id"));
        joinList.add(new Triple<>("avispack","idpack", "pack.id"));
        joinList.add(new Triple<>("commandepack","idpack", "pack.id"));
        try {
            DaoMapper resultSet = ((MethodesPostgreSQL) this.methodesBD).selectJoin(joinList, whereList, this.table);
            List<Map<String, Object>> listData = resultSet.getListeData();
            List<Map<Integer, Object>> listDataIndex = resultSet.getListeDataIndex();
            int idCurrentPack = -1;
            int i = 0;
            while (i < listData.size()) {
                /*
                 * index 1 = id du pack
                 * index 2 = nom du pack
                 * index 6 = id du coach
                 * index 8 = nom du coach
                 */
                Map<String, Object> data = listData.get(i);
                Map<Integer, Object> dataIndex = listDataIndex.get(i);
                if (idCurrentPack != ((Long)dataIndex.get(1)).intValue()) {
                    Coach coach = new Coach((String) data.get("mail"), (String) dataIndex.get(8), ((Number)data.get("poids")).doubleValue(), (String)data.get("photo"), ((Long)data.get("taille")).intValue(), Sexe.getSexe((String)data.get("sexe")), (String)data.get("password"), ((Long)dataIndex.get(6)).intValue(), (boolean) data.get("isbanned"));
                    listePack.add(new Pack(((Long)dataIndex.get(1)).intValue(), (String) dataIndex.get(2), (String) data.get("description"), ((Number)data.get("prix")).doubleValue(), coach));
                    idCurrentPack = ((Long)dataIndex.get(1)).intValue();
                }
                i++;
            }
            return listePack;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("Impossible de récupérer tous les packs");
        }
    }


    /**
     * Récupère la liste de tous les produits (Programme de nutrition, Programme sportif, Programme personnalisé, Séance et Pack)
     * associés au pack ayant pour identifiant `idPack`.
     *
     * @param idPack Identifiant du pack dont on veut récupérer les produits associés.
     * @return La liste de tous les produits associés au pack.
     * @throws Exception Si une erreur est survenue lors de la récupération de la liste des produits.
     */
    @Override
    public List<Produit> getAllProduitByPack(int idPack) throws Exception {
        List<Produit> listeProduit = new ArrayList<>();
        listeProduit.addAll(this.getAllProgrammeNutritionByPack(idPack));
        listeProduit.addAll(this.getAllProgrammePersonnaliseByPack(idPack));
        listeProduit.addAll(this.getAllProgrammeSportifByPack(idPack));
        listeProduit.addAll(this.getAllSeanceByPack(idPack));
        listeProduit.addAll(this.getAllPackByPack(idPack));
        return listeProduit;
    }


    /**
     * Récupère tous les programmes de nutrition associés au pack ayant l'id spécifié.
     *
     * @param idPack l'id du pack
     * @return la liste des programmes de nutrition associés au pack
     * @throws Exception s'il y a un problème lors de la récupération des programmes de nutrition
     */
    @Override
    public List<ProgrammeNutrition> getAllProgrammeNutritionByPack(int idPack) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("packprogrammenutrition.idpack", idPack));
        try {
            return FactoryDAOPostgreSQL.getInstance().getDAOProgrammeNutrition().getAllProgrammeNutritionWhere(whereList);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new DBProblemException("La selection de tous les programmes nutrition du pack a échoué !");
        }
    }

    /**
     * Récupère la liste de tous les programmes personnalisés associés à un pack donné.
     *
     * @param idPack L'ID du pack pour lequel on veut récupérer les programmes personnalisés
     * @return La liste de tous les programmes personnalisés associés au pack
     * @throws Exception Si une erreur survient lors de la récupération des programmes personnalisés
     */
    @Override
    public List<ProgrammePersonnalise> getAllProgrammePersonnaliseByPack(int idPack) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("packprogrammepersonnalise.idpack", idPack));
        try {
            return FactoryDAOPostgreSQL.getInstance().getDAOProgrammePersonnalise().getAllProgrammePersonnaliseWhere(whereList);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new DBProblemException("La selection de tous les programmes personnalisés du pack a échoué !");
        }
    }

    /**
     * Récupère la liste de tous les programmes sportifs associés à un pack donné.
     *
     * @param idPack L'ID du pack pour lequel on veut récupérer les programmes sportifs
     * @return La liste de tous les programmes sportifs associés au pack
     * @throws Exception Si une erreur survient lors de la récupération des programmes sportifs
     */
    @Override
    public List<ProgrammeSportif> getAllProgrammeSportifByPack(int idPack) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("packprogrammesportif.idpack", idPack));
        try {
            return FactoryDAOPostgreSQL.getInstance().getDAOProgrammeSportif().getAllProgrammeSportifWhere(whereList);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new DBProblemException("La selection de tous les programmes sportifs du pack a échoué !");
        }
    }

    /**
     * Récupère la liste de toutes les séances associées à un pack donné.
     *
     * @param idPack L'ID du pack pour lequel on veut récupérer les séances
     * @return La liste de toutes les séances associées au pack
     * @throws Exception Si une erreur survient lors de la récupération des séances
     */
    @Override
    public List<Seance> getAllSeanceByPack(int idPack) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("packseance.idpack", idPack));
        try {
            return FactoryDAOPostgreSQL.getInstance().getDAOSeance().getAllSeancesWhere(whereList);
        }
        catch (Exception e) {
            throw new DBProblemException("La selection de toutes les séances du pack" + idPack + "a échoué !");
        }
    }

    /**
     * Récupère la liste de tous les packs associés à un pack donné.
     *
     * @param idPack L'ID du pack pour lequel on veut récupérer les packs associés
     * @return La liste de tous les packs associés au pack
     * @throws Exception Si une erreur survient lors de la récupération des packs
     */
    @Override
    public List<Pack> getAllPackByPack(int idPack) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("packpack.idpack1", idPack));
        try {
            return FactoryDAOPostgreSQL.getInstance().getDAOPack().getAllPackWhere(whereList);
        }
        catch (Exception e) {
            throw new DBProblemException("La selection de tous les packs dans le pack a échoué !");
        }
    }

    /**
     * Ajoute un produit (pack, programme personnalisé, programme sportif, programme nutrition ou séance) à un pack donné.
     *
     * @param produit Le produit à ajouter au pack
     * @param idPack L'ID du pack auquel ajouter le produit
     * @throws Exception Si une erreur survient lors de l'ajout du produit au pack
     */
    @Override
    public void ajouterProduit(Produit produit, int idPack) throws Exception {
        if (produit instanceof Pack){
            this.ajouterPack((Pack) produit, idPack);
        }
        else if (produit instanceof ProgrammePersonnalise){
            this.ajouterProgrammePersonnalise((ProgrammePersonnalise) produit, idPack);
        }
        else if (produit instanceof ProgrammeSportif){
            this.ajouterProgrammeSportif((ProgrammeSportif) produit, idPack);
        }
        else if (produit instanceof ProgrammeNutrition){
            this.ajouterProgrammeNutrition((ProgrammeNutrition) produit, idPack);
        }
        else if (produit instanceof Seance){
            this.ajouterSeance((Seance) produit, idPack);
        }
        else {
            throw new DBProblemException("Le produit n'est pas reconnu !");
        }
    }

    /**
     * Ajoute un programme nutrition à un pack donné.
     *
     * @param programmeNutrition Le programme nutrition à ajouter au pack
     * @param idPack L'ID du pack auquel ajouter le programme nutrition
     * @throws Exception Si une erreur survient lors de l'ajout du programme nutrition au pack
     */
    @Override
    public void ajouterProgrammeNutrition(ProgrammeNutrition programmeNutrition, int idPack) throws Exception {
        List<Pair<String, Object>> insertList = new ArrayList<>();
        insertList.add(new Pair<>("idprogramme", programmeNutrition.getId()));
        insertList.add(new Pair<>("idpack", idPack));
        try {
            ((MethodesPostgreSQL)this.methodesBD).insert(insertList, "packprogrammenutrition");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("L'ajout du programme nutrition dans ce pack a échoué");
        }
    }

    /**
     * Ajoute un programme personnalisé à un pack donné.
     *
     * @param programmePersonnalise Le programme personnalisé à ajouter au pack
     * @param idPack L'ID du pack auquel ajouter le programme personnalisé
     * @throws Exception Si une erreur survient lors de l'ajout du programme personnalisé au pack
     */
    @Override
    public void ajouterProgrammePersonnalise(ProgrammePersonnalise programmePersonnalise, int idPack) throws Exception {
        List<Pair<String, Object>> insertList = new ArrayList<>();
        insertList.add(new Pair<>("idprogramme", programmePersonnalise.getId()));
        insertList.add(new Pair<>("idpack", idPack));
        try {
            ((MethodesPostgreSQL)this.methodesBD).insert(insertList, "packprogrammepersonnalise");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("L'ajout du programme personnalisé dans ce pack a échoué");
        }
    }

    /**
     * Ajoute un programme sportif à un pack donné.
     *
     * @param programmeSportif Le programme sportif à ajouter au pack
     * @param idPack L'ID du pack auquel ajouter le programme sportif
     * @throws Exception Si une erreur survient lors de l'ajout du programme sportif au pack
     */
    @Override
    public void ajouterProgrammeSportif(ProgrammeSportif programmeSportif, int idPack) throws Exception {
        List<Pair<String, Object>> insertList = new ArrayList<>();
        insertList.add(new Pair<>("idprogramme", programmeSportif.getId()));
        insertList.add(new Pair<>("idpack", idPack));
        try {
            ((MethodesPostgreSQL)this.methodesBD).insert(insertList, "packprogrammesportif");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("L'ajout du programme sportif dans ce pack a échoué");
        }
    }

    /**
     * Ajoute une séance à un pack donné.
     *
     * @param seance La séance à ajouter au pack
     * @param idPack L'ID du pack auquel ajouter la séance
     * @throws Exception Si une erreur survient lors de l'ajout de la séance au pack
     */
    @Override
    public void ajouterSeance(Seance seance, int idPack) throws Exception {
        List<Pair<String, Object>> insertList = new ArrayList<>();
        insertList.add(new Pair<>("idseance", seance.getId()));
        insertList.add(new Pair<>("idpack", idPack));
        try {
            ((MethodesPostgreSQL)this.methodesBD).insert(insertList, "packseance");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("L'ajout de la séance dans ce pack a échoué");
        }
    }

    /**
     * Ajoute un pack à un pack donné.
     *
     * @param pack Le pack à ajouter au pack
     * @param idPack L'ID du pack auquel ajouter le pack
     * @throws Exception Si une erreur survient lors de l'ajout du pack au pack
     */
    @Override
    public void ajouterPack(Pack pack, int idPack) throws Exception {
        List<Pair<String, Object>> insertList = new ArrayList<>();
        insertList.add(new Pair<>("idpack1", idPack));
        insertList.add(new Pair<>("idpack2", pack.getId()));
        try {
            ((MethodesPostgreSQL)this.methodesBD).insert(insertList, "packpack");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("L'ajout du pack dans ce pack a échoué");
        }
    }

    /**
     * Supprime un produit (pack, programme personnalisé, programme sportif, programme nutrition ou séance) d'un pack donné.
     *
     * @param produit Le produit à supprimer du pack
     * @param idPack L'ID du pack duquel supprimer le produit
     * @throws Exception Si une erreur survient lors de la suppression du produit du pack
     */
    @Override
    public void supprimerProduit(Produit produit, int idPack) throws Exception {
        if (produit instanceof Pack){
            this.supprimerPack((Pack) produit, idPack);
        }
        else if (produit instanceof ProgrammePersonnalise){
            this.supprimerProgrammePersonnalise((ProgrammePersonnalise) produit, idPack);
        }
        else if (produit instanceof ProgrammeSportif){
            this.supprimerProgrammeSportif((ProgrammeSportif) produit, idPack);
        }
        else if (produit instanceof ProgrammeNutrition){
            this.supprimerProgrammeNutrition((ProgrammeNutrition) produit, idPack);
        }
        else if (produit instanceof Seance){
            this.supprimerSeance((Seance) produit, idPack);
        }
        else {
            throw new Exception("Le produit n'est pas reconnu !");
        }
    }

    /**
     * Supprime un programme nutrition d'un pack donné.
     *
     * @param programmeNutrition Le programme nutrition à supprimer du pack
     * @param idPack L'ID du pack duquel supprimer le programme nutrition
     * @throws Exception Si une erreur survient lors de la suppression du programme nutrition du pack
     */
    @Override
    public void supprimerProgrammeNutrition(ProgrammeNutrition programmeNutrition, int idPack) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("idprogramme", programmeNutrition.getId()));
        whereList.add(new Pair<>("idpack", idPack));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList, "packprogrammenutrition");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("La suppression du programme nutrition dans ce pack a échoué");
        }
    }

    /**
     * Supprime un programme personnalisé d'un pack donné.
     *
     * @param programmePersonnalise Le programme personnalisé à supprimer du pack
     * @param idPack L'ID du pack duquel supprimer le programme personnalisé
     * @throws Exception Si une erreur survient lors de la suppression du programme personnalisé du pack
     */
    @Override
    public void supprimerProgrammePersonnalise(ProgrammePersonnalise programmePersonnalise, int idPack) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("idprogramme", programmePersonnalise.getId()));
        whereList.add(new Pair<>("idpack", idPack));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList, "packprogrammepersonnalise");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("La suppression du programme personnalisé dans ce pack a échoué");
        }
    }

    /**
     * Supprime un programme sportif d'un pack donné.
     *
     * @param programmeSportif Le programme sportif à supprimer du pack
     * @param idPack L'ID du pack duquel supprimer le programme sportif
     * @throws Exception Si une erreur survient lors de la suppression du programme sportif du pack
     */
    @Override
    public void supprimerProgrammeSportif(ProgrammeSportif programmeSportif, int idPack) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("idprogramme", programmeSportif.getId()));
        whereList.add(new Pair<>("idpack", idPack));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList, "packprogrammesportif");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("La suppression du programme sportif dans ce pack a échoué");
        }
    }

    /**
     * Supprime une séance d'un pack donné.
     *
     * @param seance La séance à supprimer du pack
     * @param idPack L'ID du pack duquel supprimer la séance
     * @throws Exception Si une erreur survient lors de la suppression de la séance du pack
     */
    @Override
    public void supprimerSeance(Seance seance, int idPack) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("idseance", seance.getId()));
        whereList.add(new Pair<>("idpack", idPack));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList, "packseance");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("La suppression de la séance dans ce pack a échoué");
        }
    }

    /**
     * Supprime un pack d'un autre pack donné.
     *
     * @param pack Le pack à supprimer du pack
     * @param idPack L'ID du pack duquel supprimer le pack
     * @throws Exception Si une erreur survient lors de la suppression du pack du pack
     */
    @Override
    public void supprimerPack(Pack pack, int idPack) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("idpack1", idPack));
        whereList.add(new Pair<>("idpack2", pack.getId()));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList, "packpack");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("La suppression du pack dans ce pack a échoué");
        }
    }

    /**
     * Récupère tous les packs créés par un coach donné.
     *
     * @param id L'ID du coach pour lequel récupérer les packs créés
     * @return La liste de tous les packs créés par le coach
     * @throws Exception Si une erreur survient lors de la récupération des packs créés par le coach
     */
    @Override
    public List<Pack> getAllPackByCoach(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("idcoach", id));
        return this.getAllPackWhere(whereList);
    }


}
