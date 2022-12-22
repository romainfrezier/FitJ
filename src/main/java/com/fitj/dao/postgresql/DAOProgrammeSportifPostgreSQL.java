package com.fitj.dao.postgresql;

import com.fitj.classes.*;
import com.fitj.dao.DAOProgrammeSportif;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.enums.ProgrammeType;
import com.fitj.enums.Sexe;
import kotlin.Pair;
import kotlin.Triple;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOProgrammeSportifPostgreSQL extends DAOProgrammeSportif {

    public DAOProgrammeSportifPostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    @Override
    public ProgrammeSportif createProgrammeSportif(String nom, String description, double prix, ProgrammeType type, int nbMois, Coach coach, ArrayList<Seance> listeSeances) throws Exception {
        List<Pair<String,Object>> listeInsert = new ArrayList<>();
        listeInsert.add(new Pair<>("nom",nom));
        listeInsert.add(new Pair<>("description",description));
        listeInsert.add(new Pair<>("prix",prix));
        listeInsert.add(new Pair<>("type",ProgrammeType.getProgrammeType(type)));
        listeInsert.add(new Pair<>("nbMois",nbMois));
        listeInsert.add(new Pair<>("idcoach",coach.getId()));
        try {
            int idProgramme = ((MethodesPostgreSQL)this.methodesBD).insert(listeInsert, this.table);
            for (Seance seance : listeSeances){
                List<Pair<String,Object>> listeInsertSeance = new ArrayList<>();
                listeInsertSeance.add(new Pair<>("idprogramme",idProgramme));
                listeInsertSeance.add(new Pair<>("idseance",((Seance)seance).getId()));
                ((MethodesPostgreSQL)this.methodesBD).insert(listeInsertSeance, "programmesportseance");
            }
            return this.getProgrammeSportifId(idProgramme);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new SQLException("La création du programme sportif a échoué");
        }
    }

    @Override
    public ProgrammeSportif getProgrammeSportifId(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", id));
        try{
            ResultSet programmeDB = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
            if (programmeDB.next()){
                Coach coach = (Coach) FactoryDAOPostgreSQL.getInstance().getDAOClient().getClientAccount(programmeDB.getInt("idcoach"));
                ArrayList<Seance> listeSeance = (ArrayList<Seance>) this.getSeances(id);
                return new ProgrammeSportif(programmeDB.getInt("id"), programmeDB.getString("nom"), programmeDB.getString("description"),programmeDB.getDouble("prix"),ProgrammeType.getProgrammeType(programmeDB.getString("type")), programmeDB.getInt("nbmois"),coach,listeSeance);
            }
            else {
                throw new SQLException("Aucune programme sportif avec cet id n'existe");
            }
        }
        catch(Exception e){
            throw new SQLException("La sélection du programme sportif a échoué");
        }
    }

    @Override
    public ProgrammeSportif updateProgrammeSportif(List<Pair<String, Object>> updateList, int id) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).update(updateList,whereList,this.table);
            return this.getProgrammeSportifId(id);
        }
        catch (Exception e){
            throw new SQLException("La mise à jour du programme sportif a échoué");
        }
    }

    @Override
    public void supprimerProgrammeSportif(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        List<Pair<String, Object>> whereOtherTableList = new ArrayList<>();
        whereOtherTableList.add(new Pair<>("idprogramme",id));
        List<Pair<String, Object>> wherePersonnalise = new ArrayList<>();
        wherePersonnalise.add(new Pair<>("idprogrammesportif",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"avisprogrammesportif");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"commandeprogrammesportif");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"packprogrammesportif");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"programmesportseance");
            ((MethodesPostgreSQL)this.methodesBD).delete(wherePersonnalise,"programmepersonnaliseprogrammesportif");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList,this.table);
        }
        catch(Exception e){
            throw new SQLException("La suppresion du programme sportif a échoué");
        }
    }

    @Override
    public List<ProgrammeSportif> getAllProgrammeSportif() throws Exception{
        return this.getAllProgrammeSportifWhere(new ArrayList<>());
    }

    @Override
    public List<ProgrammeSportif> getAllProgrammeSportifWhere(List<Pair<String, Object>> whereList) throws Exception {
        List<ProgrammeSportif> listeProgrammes = new ArrayList<>();
        List<Triple<String,String,String>> joinList = new ArrayList<>();
        joinList.add(new Triple<>("client","id", "programmesportif.idcoach"));
        joinList.add(new Triple<>("programmesportseance","idprogramme", "programmesportif.id"));
        joinList.add(new Triple<>("packprogrammesportif","idprogramme", "programmesportif.id"));
        joinList.add(new Triple<>("programmepersonnaliseprogrammesportif","idprogrammesportif", "programmesportif.id"));
        joinList.add(new Triple<>("avisprogrammesportif","idprogramme", "programmesportif.id"));
        joinList.add(new Triple<>("commandeprogrammesportif","idprogramme", "programmesportif.id"));
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
                    listeProgrammes.add(new ProgrammeSportif(programmeBD.getInt(1), programmeBD.getString(2), programmeBD.getString("description"), programmeBD.getDouble("prix"), ProgrammeType.getProgrammeType(programmeBD.getString("type")), programmeBD.getInt("nbmois"), coach));
                    idCurrentProgramme = programmeBD.getInt(1);
                }
            }
            return listeProgrammes;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new SQLException("Impossible de récupérer tous les programmes sportifs");
        }
    }

    @Override
    public List<Seance> getSeances(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("programmesportseance.idprogramme", id));
        try {
            return FactoryDAOPostgreSQL.getInstance().getDAOSeance().getAllSeancesWhere(whereList);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("La selection de toutes les séances du programme a échoué !");
        }
    }

    @Override
    public void ajouterSeanceProgramme(Seance seance, int id) throws Exception {
        List<Pair<String, Object>> insertList = new ArrayList<>();
        insertList.add(new Pair<>("idseance", seance.getId()));
        insertList.add(new Pair<>("idprogramme", id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).insert(insertList, "programmesportseance");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new SQLException("L'ajout de la séance dans ce programme sportif a échoué");
        }
    }

    @Override
    public void supprimerSeanceProgramme(Seance seance, int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("idseance", seance.getId()));
        whereList.add(new Pair<>("idprogramme", id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList, "programmesportseance");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new SQLException("La suppression de la séance dans ce programme sportif a échoué");
        }
    }
}
