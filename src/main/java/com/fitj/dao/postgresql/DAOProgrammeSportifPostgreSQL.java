package com.fitj.dao.postgresql;

import com.fitj.classes.*;
import com.fitj.dao.DAOProgrammeSportif;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.dao.tools.DaoMapper;
import com.fitj.enums.ProgrammeType;
import com.fitj.enums.Sexe;
import com.fitj.exceptions.DBProblemException;
import kotlin.Pair;
import kotlin.Triple;

import java.sql.SQLException;
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
public class DAOProgrammeSportifPostgreSQL extends DAOProgrammeSportif {

    /**
     * Constructeur
     */
    public DAOProgrammeSportifPostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    /**
     * Crée un programme sportif avec les informations spécifiées.
     *
     * @param nom Le nom du programme sportif.
     * @param description La description du programme sportif.
     * @param prix Le prix du programme sportif.
     * @param type Le type du programme sportif (débutant, intermédiaire, avancé).
     * @param nbMois La durée du programme sportif en mois.
     * @param coach Le coach responsable du programme sportif.
     * @param listeSeances La liste des séances incluses dans le programme sportif.
     * @return Le programme sportif créé.
     * @throws Exception Si une erreur survient lors de la création du programme sportif.
     */
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
                listeInsertSeance.add(new Pair<>("idseance",seance.getId()));
                ((MethodesPostgreSQL)this.methodesBD).insert(listeInsertSeance, "programmesportseance");
            }
            return this.getProgrammeSportifById(idProgramme);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("La création du programme sportif a échoué");
        }
    }

    /**
     * Renvoie le programme sportif associé à l'identifiant spécifié.
     *
     * @param id L'identifiant du programme sportif.
     * @return Le programme sportif associé à l'identifiant.
     * @throws Exception Si une erreur survient lors de la récupération du programme sportif.
     */
    @Override
    public ProgrammeSportif getProgrammeSportifById(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", id));
        try {
            DaoMapper programmeDB = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
            List<Map<String,Object>> result = programmeDB.getListeData();
            if (!result.isEmpty()) {
                Map<String, Object> data = result.get(0);
                Coach coach = (Coach) FactoryDAOPostgreSQL.getInstance().getDAOClient().getClientById(((Long)data.get("idcoach")).intValue());
                ArrayList<Seance> listeSeance = (ArrayList<Seance>) this.getSeances(id);
                return new ProgrammeSportif(((Long)data.get("id")).intValue(), (String) data.get("nom"), (String) data.get("description"),((Number)data.get("prix")).doubleValue(),ProgrammeType.getProgrammeType((String) data.get("type")), ((Long)data.get("nbmois")).intValue(),coach,listeSeance);
            }
            else {
                throw new DBProblemException("Aucun programme sportif avec cet id n'existe");
            }
        }
        catch(Exception e){
            throw new SQLException("La sélection du programme sportif a échoué");
        }
    }

    /**
     * Met à jour le programme sportif associé à l'identifiant spécifié avec les nouvelles informations spécifiées.
     *
     * @param updateList La liste des modifications à apporter au programme sportif.
     * @param id L'identifiant du programme sportif à mettre à jour.
     * @return Le programme sportif mis à jour.
     * @throws Exception Si une erreur survient lors de la mise à jour du programme sportif.
     */
    @Override
    public ProgrammeSportif updateProgrammeSportif(List<Pair<String, Object>> updateList, int id) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).update(updateList,whereList,this.table);
            return this.getProgrammeSportifById(id);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour du programme sportif a échoué");
        }
    }

    /**
     * Met à jour le programme sportif associé à l'identifiant spécifié avec les nouvelles informations spécifiées.
     *
     * @param idProgramme L'identifiant du programme sportif à mettre à jour.
     * @param nom Le nouveau nom du programme sportif.
     * @param description La nouvelle description du programme sportif.
     * @param prix Le nouveau prix du programme sportif.
     * @param type Le nouveau type du programme sportif (débutant, intermédiaire, avancé).
     * @param nbMois La nouvelle durée du programme sportif en mois.
     * @return Le programme sportif mis à jour.
     * @throws Exception Si une erreur survient lors de la mise à jour du programme sportif.
     */
    @Override
    public ProgrammeSportif updateProgrammeSportif(int idProgramme, String nom, String description, double prix, ProgrammeType type, int nbMois) throws Exception {
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
            return this.getProgrammeSportifById(idProgramme);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour du programme sportif a échoué");
        }
    }

    /**
     * Supprime le programme sportif associé à l'identifiant spécifié.
     *
     * @param id L'identifiant du programme sportif à supprimer.
     * @throws Exception Si une erreur survient lors de la suppression du programme sportif.
     */
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
            throw new DBProblemException("La suppresion du programme sportif a échoué");
        }
    }

    /**
     * Récupère tous les programmes sportif de la base de données.
     * @return La liste de tous les programmes sportifs de la base de données.
     * @throws Exception
     */
    @Override
    public List<ProgrammeSportif> getAllProgrammeSportif() throws Exception{
        return this.getAllProgrammeSportifWhere(new ArrayList<>());
    }

    /**
     * Récupère tous les programmes sportifs de la base de données qui répondent aux critères spécifiés.
     *
     * @param whereList La liste des critères à vérifier pour chaque programme sportif.
     * @return Une liste de tous les programmes sportif qui répondent aux critères spécifiés.
     * @throws Exception Si la récupération échoue.
     */
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
        joinList.add(new Triple<>("commande","id", "commandeprogrammesportif.idcommande"));
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
                if (idCurrentProgramme != ((Long)dataIndex.get(1)).intValue()) {
                    Coach coach = new Coach((String) data.get("mail"),(String) dataIndex.get(10), ((Number)data.get("poids")).doubleValue(), (String) data.get("photo"), ((Long)data.get("taille")).intValue(), Sexe.getSexe((String)data.get("sexe")), (String)data.get("password"), ((Long)dataIndex.get(7)).intValue(), (boolean) data.get("isbanned"));
                    listeProgrammes.add(new ProgrammeSportif(((Long)dataIndex.get(1)).intValue(), (String)dataIndex.get(2), (String)data.get("description"), ((Number)data.get("prix")).doubleValue(), ProgrammeType.getProgrammeType((String)data.get("type")), ((Long)data.get("nbmois")).intValue(), coach));
                    idCurrentProgramme = ((Long)dataIndex.get(1)).intValue();
                }
                i++;
            }
            return listeProgrammes;
        }
        catch (Exception e){
            throw new DBProblemException("Impossible de récupérer tous les programmes sportifs");
        }
    }

    /**
     * Recupere toutes les seances d'un programme sportif
     * @param id int, l'id du programme
     * @return List<Seance>, la liste des seances du programme
     * @throws Exception
     */
    @Override
    public List<Seance> getSeances(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("programmesportseance.idprogramme", id));
        try {
            return FactoryDAOPostgreSQL.getInstance().getDAOSeance().getAllSeancesWhere(whereList);
        }
        catch (Exception e) {
            throw new DBProblemException("La selection de toutes les séances du programme a échoué !");
        }
    }

    /**
     * Ajoute une seance à un programme sportif dans la base de données.
     *
     * @param seance La seance à ajouter au programme sportif.
     * @param id L'identifiant du programme sportif auquel ajouter la seance.
     * @throws Exception Si l'ajout échoue.
     */
    @Override
    public void ajouterSeanceProgramme(Seance seance, int id) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            List<Pair<String, Object>> insertList = new ArrayList<>();
            insertList.add(new Pair<>("idseance", seance.getId()));
            insertList.add(new Pair<>("idprogramme", id));
            try {
                ((MethodesPostgreSQL)this.methodesBD).insert(insertList, "programmesportseance");
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    /**
     * Supprime une seance d'un programme sportif dans la base de données.
     *
     * @param seance La seance à supprimer du programme sportif.
     * @param id L'identifiant du programme sportif duquel supprimer la seance.
     * @throws Exception Si la suppression échoue.
     */
    @Override
    public void supprimerSeanceProgramme(Seance seance, int id) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            List<Pair<String, Object>> whereList = new ArrayList<>();
            whereList.add(new Pair<>("idseance", seance.getId()));
            whereList.add(new Pair<>("idprogramme", id));
            try {
                ((MethodesPostgreSQL)this.methodesBD).delete(whereList, "programmesportseance");
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
    }


    /**
     * Recupere tous les programmes sportifs d'un coach
     * @param idCoach int, l'id du coach
     * @return List<ProgrammeSportif>, la liste des programmes sportifs du coach
     * @throws Exception
     */
    @Override
    public List<ProgrammeSportif> getAllProgrammeSportifByCoach(int idCoach) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("programmesportif.idcoach",idCoach));
        return this.getAllProgrammeSportifWhere(whereList);
    }

    /**
     * Récupère tous les programmes sportif achetés par un client donné.
     *
     * @param idClient L'identifiant du client pour lequel récupérer les programmes sportif.
     * @return Une liste de tous les programmes sportif achetés par le client.
     * @throws Exception Si la récupération échoue.
     */
    @Override
    public List<ProgrammeSportif> getAllProgrammeSportifByClient(int idClient) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("commande.idclient",idClient));
        return this.getAllProgrammeSportifWhere(whereList);
    }
}
