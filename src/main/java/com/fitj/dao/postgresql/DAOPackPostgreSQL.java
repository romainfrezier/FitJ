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

    @Override
    public List<Pack> getAllPack() throws Exception {
        return this.getAllPackWhere(new ArrayList<>());
    }

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

    @Override
    public List<Pack> getAllPackByCoach(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("idcoach", id));
        return this.getAllPackWhere(whereList);
    }


}
