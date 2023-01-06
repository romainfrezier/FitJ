package com.fitj.dao.postgresql;

import com.fitj.classes.*;
import com.fitj.dao.DAODemande;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.dao.tools.DaoMapper;
import com.fitj.enums.DemandeEtat;
import com.fitj.exceptions.DBProblemException;
import kotlin.Pair;
import kotlin.Triple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            DaoMapper demande = ((MethodesPostgreSQL)this.methodesBD).selectJoin(joinList,whereList, this.table);
            List<Map<String,Object>> result = demande.getListeData();
            List<Map<Integer,Object>> resultIndex = demande.getListeDataIndex();
            if (!result.isEmpty()){
                Map<String,Object> resultSet = result.get(0);
                Map<Integer,Object> resultSetIndex = resultIndex.get(0);
                /*
                 * index = 1 : id de la demande
                 * index = 8 : id du programme personnalisé
                 * index = 3 : Description de la demande
                 * index = 13 : Description du programme
                 */
                Coach coach = (Coach)FactoryDAOPostgreSQL.getInstance().getDAOClient().getClientById(((Long)resultSet.get("idcoach")).intValue());
                ProgrammePersonnalise programmePersonnalise = new ProgrammePersonnalise(((Long)resultSetIndex.get(8)).intValue(),(String)resultSet.get("nom"),(String)resultSetIndex.get(13),((Number)resultSet.get("prix")).doubleValue(), coach);
                Sport sport = FactoryDAOPostgreSQL.getInstance().getDAOSport().getSportById(((Long)resultSet.get("idsport")).intValue());
                return new Demande(id, ((Long)resultSet.get("nbmois")).intValue(), (String)resultSetIndex.get(3),(boolean) resultSet.get("programmesportif"),(boolean) resultSet.get("programmenutrition"),((Long)resultSet.get("nbseancesemaine")).intValue(),((Long)resultSet.get("nbrecettesemaine")).intValue(),sport,programmePersonnalise, DemandeEtat.getDemandeEtat((String)resultSet.get("etat")));
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
            DaoMapper wrapper = ((MethodesPostgreSQL)this.methodesBD).selectJoin(joinList,whereList,this.table);
            List<Map<String,Object>> listData = wrapper.getListeData();
            List<Map<Integer,Object>> listDataIndex = wrapper.getListeDataIndex();
            int idCurrentDemande = -1;
            int i = 0;
            while (i < listData.size()){
                Map<String,Object> data = listData.get(i);
                Map<Integer,Object> dataIndex = listDataIndex.get(i);
                /*
                 * index 1 = id de la séance
                 * index 2 = nom de la séance
                 * index 7 = id du coach
                 * index 10 = nom du coach
                 */
                System.out.println((String) dataIndex.get(3));
                if (idCurrentDemande != ((Long)dataIndex.get(1)).intValue()){
                    Sport sport = new Sport(((Long)data.get("idsport")).intValue(), (String) dataIndex.get(12));
                    Demande demande = new Demande(((Long)dataIndex.get(1)).intValue(), ((Long)data.get("nbmois")).intValue(), (String) dataIndex.get(3), (boolean) data.get("programmesportif"), (boolean) data.get("programmenutrition"), ((Long)data.get("nbseancesemaine")).intValue(), ((Long)data.get("nbrecettesemaine")).intValue(), sport, DemandeEtat.getDemandeEtat((String) data.get("etat")));
                    listeDemande.add(demande);
                    idCurrentDemande = ((Long)dataIndex.get(1)).intValue();
                }
                i++;
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
