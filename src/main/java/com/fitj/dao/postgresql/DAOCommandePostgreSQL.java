package com.fitj.dao.postgresql;

import com.fitj.classes.*;
import com.fitj.dao.DAOCommande;
import com.fitj.dao.DAOPaiement;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.enums.PaiementType;
import com.fitj.exceptions.DBProblemException;
import kotlin.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
     * @param client       int, l'id du client
     * @param coach        int, l'id du coach
     * @param produit      Produit, le produit associé à la commande
     * @param paiementType PaiementType, le type de paiement
     * @return Commande, la commande créée
     * @throws Exception si une erreur SQL survient
     */
    @Override
    public Commande createCommande(int client, int coach, Produit produit, PaiementType paiementType) throws Exception {
        List<Pair<String, Object>> listeInsert = new ArrayList<>();
        listeInsert.add(new Pair<>("idclient", client));
        listeInsert.add(new Pair<>("idcoach", coach));
        try {
            int id = ((MethodesPostgreSQL) this.methodesBD).insert(listeInsert, this.table);
            associateProduit(id, produit, null);

            DAOPaiement daoPaiement = new DAOPaiementPostgreSQL();
            daoPaiement.createPaiement(id, produit.getPrix(), paiementType);

            return this.getCommandeById(id);
        } catch (Exception e) {
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
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        // TODO faire les jointures avec produits et demande
        try {
            ResultSet commandeData = ((MethodesPostgreSQL) this.methodesBD).selectWhere(whereList, this.table);
            if (commandeData.next()) {
                // TODO récupérer le produit
                Produit produit = getProduit(commandeData);
                Client client = new DAOClientPostgreSQL().getClientAccount(commandeData.getInt("idclient"));
                Coach coach = (Coach) new DAOClientPostgreSQL().getClientAccount(commandeData.getInt("idcoach"));
                Commande commande;
                if (produit != null) {
                    if (produit instanceof ProgrammePersonnalise) {
                        // TODO récupérer la demande
                        Demande demande = new DAODemandePostgreSQL().getDemandeById(commandeData.getInt("iddemande"));
                        commande = new CommandePayante(client, coach, produit, commandeData.getInt("id"), demande);
                    } else {
                        if (produit.getPrix() == 0) {
                            commande = new CommandeNonPayante(client, coach, produit, commandeData.getInt("id"));
                        } else {
                            commande = new CommandePayante(client, coach, produit, commandeData.getInt("id"));
                        }
                    }
                    return commande;
                } else {
                    throw new DBProblemException("Le produit n'existe pas");
                }
            } else {
                throw new DBProblemException("La commande n'existe pas");
            }
        } catch (SQLException e) {
            throw new DBProblemException("La récupération de la commande a échoué");
        }
    }

    private Produit getProduit(ResultSet commandeData) {
        return null;
    }

    @Override
    public Commande getCommandeByIdClient(int client) throws Exception {
        return null;
    }

    @Override
    public Commande getCommandeByIdCoach(int coach) throws Exception {
        return null;
    }

    @Override
    public Commande getCommandeByIdProduit(int produit) throws Exception {
        return null;
    }

    @Override
    public List<Commande> getAllCommande() throws Exception {
        return null;
    }

    @Override
    public List<Commande> getAllCommandeWhere(List<Pair<String, Object>> whereList) throws Exception {
        return null;
    }

    @Override
    public void deleteCommande(int id) throws Exception {

    }

    @Override
    public Commande updateCommande(List<Pair<String, Object>> udpateList, int id) throws Exception {
        return null;
    }
}
