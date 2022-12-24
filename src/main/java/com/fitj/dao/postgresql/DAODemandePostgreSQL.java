package com.fitj.dao.postgresql;

import com.fitj.classes.*;
import com.fitj.dao.DAODemande;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.enums.DemandeEtat;
import com.fitj.exceptions.DBProblemException;
import kotlin.Pair;
import kotlin.Triple;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux demandes
 *
 * @author Etienne Tillier
 */
public class DAODemandePostgreSQL extends DAODemande {

    public DAODemandePostgreSQL() {
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    @Override
    public Demande getDemandeById(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("demande.id", id));
        List<Triple<String, String, String>> joinList = new ArrayList<>();
        joinList.add(new Triple<>("programmepersonnalise", "iddemande","demande.id"));
        try{
            ResultSet demande = ((MethodesPostgreSQL)this.methodesBD).selectJoin(joinList,whereList, this.table);
            if (demande.next()){
                /*
                 * index = 1 : id de la demande
                 * index = 3 : Description de la demande
                 * index = 13 : Description du programme
                 */
                Coach coach = (Coach)FactoryDAOPostgreSQL.getInstance().getDAOClient().getClientById(demande.getInt("idcoach"));
                ProgrammePersonnalise programmePersonnalise = new ProgrammePersonnalise(demande.getInt("idprogramme"),demande.getString("nom"),demande.getString(7), demande.getDouble("prix"), coach);
                Sport sport = FactoryDAOPostgreSQL.getInstance().getDAOSport().getSportById(demande.getInt("idsport"));
                return new Demande(demande.getInt(1), demande.getInt("nbmois"), demande.getString(3),demande.getBoolean("programmesportif"),demande.getBoolean("programmenutrition"),demande.getInt("nbseancesemaine"),demande.getInt("nbrecettesemaine"),sport,programmePersonnalise, DemandeEtat.getDemandeEtat(demande.getString("etat")));
            }
            else {
                throw new DBProblemException("Aucune demande avec cet id n'existe");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            throw new DBProblemException("La sélection de la demande a échoué");
        }
    }

    @Override
    public List<Demande> getAllDemande() throws Exception {
        return this.getAllDemandeWhere(new ArrayList<>());
    }

    @Override
    public List<Demande> getAllDemandeWhere(List<Pair<String, Object>> whereList) throws Exception {
        List<Demande> listeDemande = new ArrayList<>();
        List<Triple<String,String,String>> joinList = new ArrayList<>();
        joinList.add(new Triple<>("programmepersonnalise","iddemande", "demande.id"));
        joinList.add(new Triple<>("commandedemande","iddemande", "demande.id"));
        joinList.add(new Triple<>("sport","id", "demande.idsport"));
        try {
            ResultSet demandeBD = ((MethodesPostgreSQL)this.methodesBD).selectJoin(joinList, whereList, this.table);
            int idCurrentProgramme = -1;
            while(demandeBD.next()){
                /*
                 * index 1 = id de la séance
                 * index 2 = nom de la séance
                 * index 7 = id du coach
                 * index 10 = nom du coach
                 */
                System.out.println(demandeBD.getString(3));
                System.out.println(demandeBD.getString(8));
                if (idCurrentProgramme != demandeBD.getInt(1)){
                    Sport sport = new Sport(demandeBD.getInt("idsport"), demandeBD.getString(8));
                    Demande demande = new Demande(demandeBD.getInt(1), demandeBD.getInt("nbmois"), demandeBD.getString(3), demandeBD.getBoolean("programmesportif"), demandeBD.getBoolean("programmenutrition"), demandeBD.getInt("nbseancesemaine"), demandeBD.getInt("nbrecettesemaine"), sport, DemandeEtat.getDemandeEtat(demandeBD.getString("etat")));
                    listeDemande.add(demande);
                    idCurrentProgramme = demandeBD.getInt(1);
                }
            }
            return listeDemande;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("Impossible de récupérer toutes les demandes");
        }
    }

    @Override
    public Demande createDemande(int nbMois, String description, boolean programmeSportif, boolean programmeNutrition, int nbSeanceSemaine, int nbRecetteSemaine, Sport sport, ProgrammePersonnalise programmePersonnalise) throws Exception {
        List<Pair<String,Object>> listeInsert = new ArrayList<>();
        listeInsert.add(new Pair<>("nbmois",nbMois));
        listeInsert.add(new Pair<>("description",description));
        listeInsert.add(new Pair<>("programmesportif",programmeSportif));
        listeInsert.add(new Pair<>("programmenutrition",programmeNutrition));
        listeInsert.add(new Pair<>("nbseancesemaine",nbSeanceSemaine));
        listeInsert.add(new Pair<>("nbrecettesemaine",nbRecetteSemaine));
        listeInsert.add(new Pair<>("idprogramme",programmePersonnalise.getId()));
        listeInsert.add(new Pair<>("idsport",sport.getId()));
        listeInsert.add(new Pair<>("etat", DemandeEtat.getDemandeEtat(DemandeEtat.EN_ATTENTE)));
        try {
            int idDemande = ((MethodesPostgreSQL)this.methodesBD).insert(listeInsert, this.table);
            Demande demande = new Demande(idDemande, nbMois, description, programmeSportif, programmeNutrition, nbSeanceSemaine, nbRecetteSemaine, sport, DemandeEtat.EN_ATTENTE);
            ProgrammePersonnalise programmePersonnalise1 = FactoryDAOPostgreSQL.getInstance().getDAOProgrammePersonnalise().createProgrammePersonnaliseDemande(programmePersonnalise,demande);
            return this.updateProgrammePersonnalise(programmePersonnalise1.getId(), idDemande);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("La création de la demande a échoué");
        }
    }

    /**
     * Met à jour dans la base de donnée l'id du programme personnalisé
     * @param idProgrammePersonnalise int, l'id du programme personnalisé à mettre à jour
     * @param id int, l'id de la demande
     * @return la demande mise à jour dans la base de donnée
     */
    public Demande updateProgrammePersonnalise(int idProgrammePersonnalise, int id) throws Exception {
        List<Pair<String, Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("idprogramme", idProgrammePersonnalise));
        return this.updateDemande(updateList,id);
    }

    @Override
    public void supprimerDemande(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        List<Pair<String, Object>> whereOtherTableList = new ArrayList<>();
        whereOtherTableList.add(new Pair<>("iddemande",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"commandedemande");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"programmepersonnalise");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList,this.table);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new DBProblemException("La suppresion de la demande a échoué");
        }
    }

    @Override
    public Demande updateDemande(List<Pair<String, Object>> updateList, int id) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).update(updateList,whereList,this.table);
            return this.getDemandeById(id);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour de la demande a échoué");
        }
    }

}
