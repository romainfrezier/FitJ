package com.fitj.dao.postgresql;

import com.fitj.classes.*;
import com.fitj.dao.DAOProgrammePersonnalise;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.enums.Sexe;
import com.fitj.exceptions.DBProblemException;
import kotlin.Pair;
import kotlin.Triple;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux exercices
 *
 * @author Etienne Tillier, Romain Frezier
 */
public class DAOProgrammePersonnalisePostgreSQL extends DAOProgrammePersonnalise {

    public DAOProgrammePersonnalisePostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    @Override
    public ProgrammePersonnalise createProgrammePersonnalise(String nom, String description, double prix, Coach coach) throws Exception {
        List<Pair<String,Object>> listeInsert = new ArrayList<>();
        listeInsert.add(new Pair<>("nom",nom));
        listeInsert.add(new Pair<>("description",description));
        listeInsert.add(new Pair<>("prix",prix));
        listeInsert.add(new Pair<>("idcoach",coach.getId()));
        try {
            int idProgramme = ((MethodesPostgreSQL)this.methodesBD).insert(listeInsert, this.table);
            return new ProgrammePersonnalise(idProgramme, nom, description, prix, coach);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("La création du programme personnalisé a échoué");
        }
    }

    @Override
    public ProgrammePersonnalise createProgrammePersonnaliseDemande(ProgrammePersonnalise programmePersonnalise, Demande demande) throws Exception {
        List<Pair<String,Object>> listeInsert = new ArrayList<>();
        listeInsert.add(new Pair<>("nom",programmePersonnalise.getNom()));
        listeInsert.add(new Pair<>("description",programmePersonnalise.getDescription()));
        listeInsert.add(new Pair<>("prix",programmePersonnalise.getPrix()));
        listeInsert.add(new Pair<>("idcoach",programmePersonnalise.getCoach().getId()));
        listeInsert.add(new Pair<>("iddemande",demande.getId()));

        try {
            int idProgramme = ((MethodesPostgreSQL)this.methodesBD).insert(listeInsert, this.table);
            return this.getProgrammePersonnaliseId(idProgramme);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("La création du programme personnalisé avec demande a échoué");
        }
    }

    @Override
    public ProgrammePersonnalise getProgrammePersonnaliseId(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", id));
        try{
            ResultSet programmeDB = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
            if (programmeDB.next()){
                Coach coach = (Coach) FactoryDAOPostgreSQL.getInstance().getDAOClient().getClientAccount(programmeDB.getInt("idcoach"));
                Demande demande = FactoryDAOPostgreSQL.getInstance().getDAODemande().getDemandeById(programmeDB.getInt("iddemande"));
                ArrayList<Programme> listeProgramme = (ArrayList<Programme>) this.getProgrammes(id);
                return new ProgrammePersonnalise(programmeDB.getInt("id"), programmeDB.getString("nom"), programmeDB.getString("description"),programmeDB.getDouble("prix"),coach, demande,listeProgramme);
            }
            else {
                throw new DBProblemException("Aucune programme personnalisé avec cet id n'existe");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            throw new DBProblemException("La sélection du programme personnalisé a échoué");
        }
    }

    @Override
    public ProgrammePersonnalise updateProgrammePersonnalise(List<Pair<String, Object>> updateList, int id) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).update(updateList,whereList,this.table);
            return this.getProgrammePersonnaliseId(id);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour du programme personnalisé a échoué");
        }
    }

    @Override
    public void supprimerProgrammePersonnalise(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        List<Pair<String, Object>> whereOtherTableList = new ArrayList<>();
        whereOtherTableList.add(new Pair<>("idprogramme",id));
        List<Pair<String, Object>> wherePersonnalise = new ArrayList<>();
        wherePersonnalise.add(new Pair<>("idprogrammepersonnalise",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"demande");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"commandeprogrammepersonnalise");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"packprogrammepersonnalise");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"avisprogrammepersonnalise");
            ((MethodesPostgreSQL)this.methodesBD).delete(wherePersonnalise,"programmepersonnaliseprogrammenutrition");
            ((MethodesPostgreSQL)this.methodesBD).delete(wherePersonnalise,"programmepersonnaliseprogrammesportif");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList,this.table);
        }
        catch(Exception e){
            throw new DBProblemException("La suppression du programme personnalise a échoué");
        }
    }

    @Override
    public List<ProgrammePersonnalise> getAllProgrammePersonnalise() throws Exception {
        return this.getAllProgrammePersonnaliseWhere(new ArrayList<>());
    }

    @Override
    public List<ProgrammePersonnalise> getAllProgrammePersonnaliseWhere(List<Pair<String, Object>> whereList) throws Exception {
        List<ProgrammePersonnalise> listeProgrammes = new ArrayList<>();
        List<Triple<String,String,String>> joinList = new ArrayList<>();
        joinList.add(new Triple<>("client","id", "programmepersonnalise.idcoach"));
        joinList.add(new Triple<>("demande","id", "programmepersonnalise.iddemande"));
        joinList.add(new Triple<>("programmepersonnaliseprogrammesportif","idprogrammepersonnalise", "programmepersonnalise.id"));
        joinList.add(new Triple<>("programmepersonnaliseprogrammenutrition","idprogrammepersonnalise", "programmepersonnalise.id"));
        joinList.add(new Triple<>("avisprogrammepersonnalise","idprogramme", "programmepersonnalise.id"));
        joinList.add(new Triple<>("commandeprogrammepersonnalise","idprogramme", "programmepersonnalise.id"));
        joinList.add(new Triple<>("packprogrammepersonnalise","idprogramme", "programmepersonnalise.id"));
        try {
            ResultSet programmeBD = ((MethodesPostgreSQL)this.methodesBD).selectJoin(joinList, whereList, this.table);
            int idCurrentProgramme = -1;
            while(programmeBD.next()){
                /*
                 * index 1 = id de la séance
                 * index 2 = nom de la séance
                 * index 7 = id du coach
                 * index 10 = nom du coach
                 */
                if (idCurrentProgramme != programmeBD.getInt(1)){
                    Coach coach = new Coach(programmeBD.getString("mail"), programmeBD.getString(10), programmeBD.getDouble("poids"), programmeBD.getString("photo"), programmeBD.getInt("taille"), Sexe.getSexe(programmeBD.getString("sexe")), programmeBD.getString("password"), programmeBD.getInt(7));
                    listeProgrammes.add(new ProgrammePersonnalise(programmeBD.getInt(1), programmeBD.getString(2), programmeBD.getString("description"), programmeBD.getDouble("prix"), coach));
                    idCurrentProgramme = programmeBD.getInt(1);
                }
            }
            return listeProgrammes;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("Impossible de récupérer tous les programmes personnalisés");
        }
    }

    @Override
    public List<Programme> getProgrammes(int id) throws Exception {
        List<Programme> listeProgramme = new ArrayList<>();
        listeProgramme.addAll(this.getProgrammesNutrition(id));
        listeProgramme.addAll(this.getProgrammesSportif(id));
        return listeProgramme;
    }

    public List<ProgrammeSportif> getProgrammesSportif(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("programmepersonnaliseprogrammesportif.idprogrammepersonnalise", id));
        try {
            return FactoryDAOPostgreSQL.getInstance().getDAOProgrammeSportif().getAllProgrammeSportifWhere(whereList);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new DBProblemException("La selection de tous les programmes sportif du programme personnalisé a échoué !");
        }
    }

    public List<ProgrammeNutrition> getProgrammesNutrition(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("programmepersonnaliseprogrammenutrition.idprogrammepersonnalise", id));
        try {
            return FactoryDAOPostgreSQL.getInstance().getDAOProgrammeNutrition().getAllProgrammeNutritionWhere(whereList);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new DBProblemException("La selection de tous les programmes nutrition du programme personnalisé a échoué !");
        }
    }

    @Override
    public void ajouterProgrammeProgrammePersonnalise(Programme programme, int id) throws Exception {
        if (programme instanceof ProgrammeSportif){
            this.ajouterProgrammeSportifProgrammePersonnalise((ProgrammeSportif) programme,id);
        }
        else if (programme instanceof ProgrammeNutrition){
            this.ajouterProgrammeNutritionProgrammePersonnalise((ProgrammeNutrition) programme,id);
        }
        else {
            throw new DBProblemException("Le type du programme à ajouter dans le programme personnalisé est inconnu");
        }
    }

    public void ajouterProgrammeSportifProgrammePersonnalise(ProgrammeSportif programmeSportif, int id) throws Exception {
        List<Pair<String, Object>> insertList = new ArrayList<>();
        insertList.add(new Pair<>("idprogrammesportif", programmeSportif.getId()));
        insertList.add(new Pair<>("idprogrammepersonnalise", id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).insert(insertList, "programmepersonnaliseprogrammesportif");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("L'ajout du programme sportif dans ce programme personnalise a échoué");
        }
    }

    public void ajouterProgrammeNutritionProgrammePersonnalise(ProgrammeNutrition programmeNutrition, int id) throws Exception {
        List<Pair<String, Object>> insertList = new ArrayList<>();
        insertList.add(new Pair<>("idprogrammenutrition", programmeNutrition.getId()));
        insertList.add(new Pair<>("idprogrammepersonnalise", id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).insert(insertList, "programmepersonnaliseprogrammenutrition");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("L'ajout du programme nutrition dans ce programme personnalise a échoué");
        }
    }

    @Override
    public void supprimerProgrammeProgrammePersonnalise(Programme programme, int id) throws Exception {
        if (programme instanceof ProgrammeSportif){
            this.supprimerProgrammeSportifProgrammePersonnalise((ProgrammeSportif) programme,id);
        }
        else if (programme instanceof ProgrammeNutrition){
            this.supprimerProgrammeNutritionProgrammePersonnalise((ProgrammeNutrition) programme,id);
        }
        else {
            throw new DBProblemException("Le type du programme à ajouter dans le programme personnalisé est inconnu");
        }
    }

    public void supprimerProgrammeSportifProgrammePersonnalise(ProgrammeSportif programmeSportif, int id) throws Exception{
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("idprogrammesportif", programmeSportif.getId()));
        whereList.add(new Pair<>("idprogrammepersonnalise", id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList, "programmepersonnaliseprogrammesportif");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("La suppression du programme sportif dans ce programme personnalisé a échoué");
        }
    }

    public void supprimerProgrammeNutritionProgrammePersonnalise(ProgrammeNutrition programmeNutrition, int id) throws Exception{
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("idprogrammenutrition", programmeNutrition.getId()));
        whereList.add(new Pair<>("idprogrammepersonnalise", id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList, "programmepersonnaliseprogrammenutrition");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("La suppression du programme nutrition dans ce programme personnalisé a échoué");
        }
    }
}
