package com.fitj.dao.postgresql;

import com.fitj.classes.*;
import com.fitj.dao.DAOCommande;
import com.fitj.dao.DAOPaiement;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.dao.tools.DaoMapper;
import com.fitj.enums.DemandeEtat;
import com.fitj.enums.PaiementType;
import com.fitj.exceptions.DBProblemException;
import kotlin.Pair;
import kotlin.Triple;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux commandes
 * @see DAOCommande
 * @author Etienne Tillier, Romain Frezier
 */
public class DAOCommandePostgreSQL extends DAOCommande {

    public DAOCommandePostgreSQL() {
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    /**
     * Crée une commande dans la base de donnée
     * @param idclient       int, l'id du client
     * @param idcoach        int, l'id du coach
     * @param produit      Produit, le produit associé à la commande
     * @param paiementType PaiementType, le type de paiement
     * @return Commande, la commande créée
     * @throws Exception si une erreur SQL survient
     */
    @Override
    public Commande createCommande(int idclient, int idcoach, Produit produit, PaiementType paiementType) throws Exception {
        List<Pair<String, Object>> listeInsert = new ArrayList<>();
        listeInsert.add(new Pair<>("idclient", idclient));
        listeInsert.add(new Pair<>("idcoach", idcoach));
        try {
            int id = ((MethodesPostgreSQL) this.methodesBD).insert(listeInsert, this.table);
            associateProduit(id, produit, null);

            DAOPaiement daoPaiement = new DAOPaiementPostgreSQL();
            daoPaiement.createPaiement(id, produit.getPrix(), paiementType);

            return this.getCommandeById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBProblemException("La création de la commande a échoué");
        }
    }


    /**
     * Crée une commande avec une demande
     * @param client       int, l'id du client
     * @param coach        int, l'id du coach
     * @param produit      Produit, le produit associé à la commande
     * @param paiementType PaiementType, le type de paiement
     * @param demande      Demande, la demande associée à la commande
     * @return Commande, la commande créée
     * @throws Exception si une erreur SQL survient
     */
    @Override
    public Commande createCommande(int client, int coach, Produit produit, PaiementType paiementType, Demande demande) throws Exception {
        List<Pair<String, Object>> listeInsert = new ArrayList<>();
        listeInsert.add(new Pair<>("idclient", client));
        listeInsert.add(new Pair<>("idcoach", coach));
        try {
            int id = ((MethodesPostgreSQL) this.methodesBD).insert(listeInsert, this.table);
            associateProduit(id, produit, demande);

            DAOPaiement daoPaiement = new DAOPaiementPostgreSQL();
            daoPaiement.createPaiement(id, produit.getPrix(), paiementType);

            return this.getCommandeById(id);

        } catch (Exception e) {
            throw new DBProblemException("La création de la commande a échoué");
        }
    }

    /**
     * Réalise l'association entre une commande et un produit
     * @param id      int, l'id de la commande
     * @param produit Produit, le produit associé à la commande
     * @param demande Demande, la demande associée à la commande
     * @throws Exception si une erreur SQL survient
     */
    private void associateProduit(int id, Produit produit, Demande demande) throws Exception {

        if (produit instanceof Pack) {
            associatePack(id, produit);
        } else if (produit instanceof ProgrammeSportif){
            associateProgrammeSportif(id, produit);
        } else if (produit instanceof ProgrammeNutrition){
            associateProgrammeNutrition(id, produit);
        } else if (produit instanceof ProgrammePersonnalise){
            associateProgrammePersonnalise(id, produit, demande);
        } else if (produit instanceof Seance) {
            associateSeance(id, produit);
        } else {
            throw new DBProblemException("Le produit n'est pas reconnu");
        }
    }

    /**
     * Réalise l'association entre une commande et une séance
     * @param id     int, l'id de la commande
     * @param produit Produit, le produit associé à la commande
     * @throws Exception si une erreur SQL survient
     */
    private void associateSeance(int id, Produit produit) throws Exception {
        insertInto(id, "idcommande", produit, "idseance", "commandeseance");
    }

    /**
     * Réalise l'association entre une commande et un programme personnalisé
     * @param id     int, l'id de la commande
     * @param produit Produit, le produit associé à la commande
     * @param demande Demande, la demande associée à la commande
     * @throws Exception si une erreur SQL survient
     */
    private void associateProgrammePersonnalise(int id, Produit produit, Demande demande) throws Exception {
        insertInto(id, "idcommande", produit, "idprogramme", "commandeprogrammepersonnalise");
        associateDemande(id, demande);
    }

    /**
     * Réalise l'association entre une commande et un programme nutrition
     * @param id    int, l'id de la commande
     * @param produit Produit, le produit associé à la commande
     * @throws Exception si une erreur SQL survient
     */
    private void associateProgrammeNutrition(int id, Produit produit) throws Exception {
        insertInto(id, "idcommande", produit, "idprogramme", "commandeprogrammenutrition");
    }

    /**
     * Réalise l'association entre une commande et un programme sportif
     * @param id    int, l'id de la commande
     * @param produit Produit, le produit associé à la commande
     * @throws Exception si une erreur SQL survient
     */
    private void associateProgrammeSportif(int id, Produit produit) throws Exception {
        insertInto(id, "idcommande", produit, "idprogramme", "commandeprogrammesportif");
    }

    /**
     * Réalise l'association entre une commande et un pack
     * @param id    int, l'id de la commande
     * @param produit Produit, le produit associé à la commande
     * @throws Exception si une erreur SQL survient
     */
    private void associatePack(int id, Produit produit) throws Exception {
        insertInto(id, "idcommande", produit, "idpack", "commandepack");
    }

    /**
     * Insert une ligne dans une table d'association
     * @param id   int, l'id de la commande
     * @param nomAttribut1 String, le nom de l'attribut correspondant a l'id de la commande
     * @param produit Produit, le produit associé à la commande
     * @param nomAttribut2 String, le nom de l'attribut correspondant a l'id du produit
     * @param table String, le nom de la table d'association
     * @throws Exception si une erreur SQL survient
     */
    private void insertInto(int id, String nomAttribut1, Produit produit, String nomAttribut2, String table) throws Exception {
        List<Pair<String, Object>> listeInsertProduit = new ArrayList<>();
        listeInsertProduit.add(new Pair<>(nomAttribut1, id));
        listeInsertProduit.add(new Pair<>(nomAttribut2, produit.getId()));
        ((MethodesPostgreSQL) this.methodesBD).insert(listeInsertProduit, table);
    }

    /**
     * Réalise l'association entre une commande et une demande
     * @param id     int, l'id de la commande
     * @param demande Demande, la demande associée à la commande
     * @throws Exception si une erreur SQL survient
     */
    private void associateDemande(int id, Demande demande) throws Exception {
        if (demande != null) {
            List<Pair<String, Object>> listeInsertDemande = new ArrayList<>();
            listeInsertDemande.add(new Pair<>("idcommande", id));
            listeInsertDemande.add(new Pair<>("iddemande", demande.getId()));
            try {
                ((MethodesPostgreSQL) this.methodesBD).insert(listeInsertDemande, "commandedemande");
            } catch (Exception e) {
                throw new DBProblemException("La liaison avec le demande a échoué");
            }
        } else {
            throw new DBProblemException("La demande n'existe pas");
        }
    }

    /**
     * Récupère une commande à partir de son id
     * @param id int, l'id de la commande
     * @return Commande, la commande correspondant à l'id
     * @throws Exception si une erreur SQL survient
     */
    @Override
    public Commande getCommandeById(int id) throws Exception {
        List<Pair<String, Object>> listeWhere = new ArrayList<>();
        listeWhere.add(new Pair<>("commande.id", id));
        return getAllCommandeWhere(listeWhere).get(0);
    }

    /**
     * Récupère toutes les commandes d'un client
     * @param client int, l'id du client
     * @return List<Commande>, la liste des commandes du client
     * @throws Exception
     */
    @Override
    public List<Commande>  getCommandeByIdClient(int client) throws Exception {
        List<Pair<String, Object>> listeWhere = new ArrayList<>();
        listeWhere.add(new Pair<>("commande.idclient", client));
        return getAllCommandeWhere(listeWhere);
    }

    /**
     * Récupère toutes les commandes d'un coach
     * @param coach Coach, le coach
     * @return List<Commande>, la liste des commandes du client
     * @throws Exception
     */
    @Override
    public List<Commande> getCommandeByIdCoach(int coach) throws Exception {
        List<Pair<String, Object>> listeWhere = new ArrayList<>();
        listeWhere.add(new Pair<>("commande.idcoach", coach));
        return getAllCommandeWhere(listeWhere);
    }

    /**
     * Retourne toutes les commandes correspondant à un produit
     * @param produit Produit, le produit associé à la commande
     * @return List<Commande>, la liste des commandes correspondant au produit
     * @throws Exception
     */
    @Override
    public List<Commande> getCommandeByProduit(Produit produit) throws Exception {
        List<Pair<String, Object>> listeWhere = new ArrayList<>();
        if (produit instanceof Seance) {
            listeWhere.add(new Pair<>("commandeseance.idseance", produit.getId()));
        } else if (produit instanceof ProgrammePersonnalise) {
            listeWhere.add(new Pair<>("commandeprogrammepersonnalise.idprogramme", produit.getId()));
        } else if (produit instanceof ProgrammeNutrition) {
            listeWhere.add(new Pair<>("commandeprogrammenutrition.idprogramme", produit.getId()));
        } else if (produit instanceof ProgrammeSportif) {
            listeWhere.add(new Pair<>("commandeprogrammesportif.idprogramme", produit.getId()));
        } else if (produit instanceof Pack) {
            listeWhere.add(new Pair<>("commandepack.idpack", produit.getId()));
        } else {
            throw new DBProblemException("Le produit n'existe pas");
        }

        return getAllCommandeWhere(listeWhere);
    }

    /**
     * Retourne toute les commandes
     * @return List<Commande>, la liste des commandes
     * @throws Exception
     */
    @Override
    public List<Commande> getAllCommande() throws Exception {
        return getAllCommandeWhere(new ArrayList<>());
    }

    /**
     * Retourne toutes les commandes correspondant à une liste de conditions
     * @param whereList List<Pair<String, Object>>, la liste des conditions
     * @return List<Commande>, la liste des commandes correspondant aux conditions
     * @throws Exception
     */
    @Override
    public List<Commande> getAllCommandeWhere(List<Pair<String, Object>> whereList) throws Exception {
        List<Commande> listeCommande = new ArrayList<>();
        List<Triple<String,String,String>> joinList = new ArrayList<>();
        joinList.add(new Triple<>("commandepack","idcommande","commande.id"));
        joinList.add(new Triple<>("commandeseance","idcommande","commande.id"));
        joinList.add(new Triple<>("commandeprogrammesportif","idcommande","commande.id"));
        joinList.add(new Triple<>("commandeprogrammenutrition","idcommande","commande.id"));
        joinList.add(new Triple<>("commandeprogrammepersonnalise","idcommande","commande.id"));
        joinList.add(new Triple<>("commandedemande","idcommande","commande.id"));
        joinList.add(new Triple<>("pack","id","commandepack.idpack"));
        joinList.add(new Triple<>("seance","id","commandeseance.idseance"));
        joinList.add(new Triple<>("programmesportif","id","commandeprogrammesportif.idprogramme"));
        joinList.add(new Triple<>("programmenutrition","id","commandeprogrammenutrition.idprogramme"));
        joinList.add(new Triple<>("programmepersonnalise","id","commandeprogrammepersonnalise.idprogramme"));
        joinList.add(new Triple<>("demande","id","commandedemande.iddemande"));
        joinList.add(new Triple<>("sport","id","demande.idsport"));
        try {
            DaoMapper commandeData = ((MethodesPostgreSQL) this.methodesBD).selectJoin(joinList, whereList, this.table);
            List<Map<String, Object>> listeData = commandeData.getListeData();
            List<Map<Integer,Object>> listeDataIndex = commandeData.getListeDataIndex();
            int i = 0;
            while (i < listeData.size()){
                Map<String,Object> data = listeData.get(i);
                Map<Integer,Object> dataIndex = listeDataIndex.get(i);
                /*
                index 6 : idpack
                index 8 : idseance
                index 10 : idprogrammesportif
                index 12 : idprogrammenutrition
                index 14 : idprogrammepersonnalise
                 */
                if (dataIndex.get(6) != null || dataIndex.get(8) != null || dataIndex.get(10) != null || dataIndex.get(12) != null || dataIndex.get(14) != null) {
                    Produit produit = getTypeProduit(dataIndex);
                    Client client = new DAOClientPostgreSQL().getClientById(((Long)dataIndex.get(2)).intValue()); // index 2 = idclient
                    Coach coach = (Coach) new DAOClientPostgreSQL().getClientById(((Long)dataIndex.get(3)).intValue()); // index 3 = idcoach
                    Commande commande;
                    if (produit instanceof ProgrammePersonnalise) {
                        Demande demande = new DAODemandePostgreSQL().getDemandeById(((Long)data.get("iddemande")).intValue());
                        commande = new CommandePayante(client, coach, produit, ((Long)dataIndex.get(1)).intValue(), (Date)dataIndex.get(4), demande); // index 1 = idcommande
                    } else {
                        if (produit.getPrix() == 0) {
                            commande = new CommandeNonPayante(client, coach, produit, ((Long)dataIndex.get(1)).intValue(), (Date)dataIndex.get(4)); // index 1 = idcommande
                        } else {
                            commande = new CommandePayante(client, coach, produit, ((Long)dataIndex.get(1)).intValue(), (Date)dataIndex.get(4)); // index 1 = idcommande
                        }
                    }
                    listeCommande.add(commande);
                }
                i++;
            }
            return listeCommande;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBProblemException("La récupération de la commande a échoué");
        }
    }

    /**
     * Récupère le produit associé à une commande
     * @param commandeData ResultSet, le résultat de la requête SQL
     * @return Produit, le produit associé à la commande
     * @throws Exception si une erreur SQL survient
     */
    private Produit getTypeProduit(Map<Integer,Object> commandeData) throws Exception {
        /*
        index 6 : idpack
        index 8 : idseance
        index 10 : idprogrammesportif
        index 12 : idprogrammenutrition
        index 14 : idprogrammepersonnalise
        index 18 : nom pack
        index 23 : nom seance
        index 29 : nom programmesportif
        index 36 : nom programmenutrition
        index 43 : nom programmepersonnalise
         */
        if (commandeData.get(6) != null) {
            return new Pack(((Long)commandeData.get(6)).intValue(), (String)commandeData.get(18));
        } else if (commandeData.get(8) != null) {
            return new Seance(((Long)commandeData.get(8)).intValue(), (String)commandeData.get(23));
        } else if (commandeData.get(10) != null) {
            return new ProgrammeSportif(((Long)commandeData.get(10)).intValue(), (String)commandeData.get(29));
        } else if (commandeData.get(12) != null) {
            return new ProgrammeNutrition(((Long)commandeData.get(12)).intValue(), (String)commandeData.get(36));
        } else if (commandeData.get(14) != null) {
            Sport sport = new Sport(((Long)commandeData.get(58)).intValue(), (String)commandeData.get(59));
            Demande demande = new Demande(((Long)commandeData.get(48)).intValue(),((Long)commandeData.get(49)).intValue(),(String)commandeData.get(50),(boolean)commandeData.get(51), (boolean) commandeData.get(52),((Long)commandeData.get(53)).intValue(),((Long)commandeData.get(54)).intValue(),sport, DemandeEtat.getDemandeEtat((String)commandeData.get(57)));
            return new ProgrammePersonnalise(((Long)commandeData.get(14)).intValue(), (String)commandeData.get(43), demande);
        } else {
            throw new DBProblemException("Le produit n'existe pas");
        }
    }

    /**
     * Supprime une commande
     * @param id int, l'id de la commande
     * @throws Exception
     */
    @Override
    public void deleteCommande(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        List<Pair<String, Object>> whereOtherTableList = new ArrayList<>();
        whereOtherTableList.add(new Pair<>("idcommande",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"commandepack");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"commandeseance");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"commandeprogrammesportif");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"commandeprogrammenutrition");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"commandeprogrammepersonnalise");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"commandedemande");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereOtherTableList,"paiement");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList,this.table);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new SQLException("La suppression de la commande a échoué");
        }
    }

    /**
     * Modifie une commande
     * @param udpateList List<Pair<String,Object>>, la liste des modifications à effectuer
     * @param id int, l'id de la commande à modifier
     * @return Commande, la commande modifiée
     * @throws Exception
     */
    @Override
    public Commande updateCommande(List<Pair<String, Object>> udpateList, int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).update(udpateList,whereList,this.table);
            return getCommandeById(id);
        }
        catch(Exception e){
            throw new DBProblemException("La modification de la commande a échoué");
        }
    }
}
