package com.fitj.dao.postgresql;

import com.fitj.classes.Paiement;
import com.fitj.dao.DAOPaiement;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.dao.tools.DaoMapper;
import com.fitj.enums.PaiementType;
import com.fitj.exceptions.DBProblemException;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux paiements
 *
 * @author Etienne Tillier, Romain Frezier
 */
public class DAOPaiementPostgreSQL extends DAOPaiement {

    /**
     * Constructeur
     */
    public DAOPaiementPostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    /**
     * Crée un paiement dans la base de données
     * @param idcommande int, l'id de la commande
     * @param prix double, le prix du paiement
     * @param paiementType PaiementType, le type de paiement
     * @return le paiement créé
     * @throws Exception
     */
    @Override
    public Paiement createPaiement(int idcommande, double prix, PaiementType paiementType) throws Exception {
        List<Pair<String, Object>> listeInsertPaiement = new ArrayList<>();
        listeInsertPaiement.add(new Pair<>("idcommande", idcommande));
        listeInsertPaiement.add(new Pair<>("montant", prix));
        listeInsertPaiement.add(new Pair<>("type", PaiementType.getPaiementType(paiementType)));
        int id = ((MethodesPostgreSQL) this.methodesBD).insert(listeInsertPaiement, "paiement");
        return this.getPaiementById(id);
    }

    /**
     * Permet de récupérer le paiement possédant l'id rentré en paramètre
     * @param id int, l'id du paiement
     * @return le paiement dans la base de donnée contenant l'id rentré en paramètre
     * @throws Exception si une erreur SQL est détectée
     */
    @Override
    public Paiement getPaiementById(int id) throws Exception {
        List<Pair<String, Object>> listeWherePaiement = new ArrayList<>();
        listeWherePaiement.add(new Pair<>("id", id));
        return this.getAllPaiementsWhere(listeWherePaiement).get(0);
    }

    /**
     * Permet de récupérer tous les paiements de la base de données
     * @return la liste de tous les paiements de la base de données
     * @throws Exception si une erreur SQL est détectée
     */
    @Override
    public List<Paiement> getAllPaiements() throws Exception {
        List<Pair<String, Object>> listeWherePaiement = new ArrayList<>();
        return this.getAllPaiementsWhere(listeWherePaiement);
    }

    /**
     * Permet de récupérer tous les paiements de la base de données qui respectent les conditions rentrées en paramètre
     * @param whereList List<Pair<String, Object>>, la liste des conditions
     * @return la liste de tous les paiements de la base de données qui respectent les conditions rentrées en paramètre
     * @throws Exception si une erreur SQL est détectée
     */
    @Override
    public List<Paiement> getAllPaiementsWhere(List<Pair<String, Object>> whereList) throws Exception {
        List<Paiement> paiements = new ArrayList<>();
        try {
            DaoMapper resultSet = ((MethodesPostgreSQL) this.methodesBD).selectWhere(whereList, this.table);
            List<Map<String, Object>> listData = resultSet.getListeData();
            int i = 0;
            while (i < listData.size()) {
                Map<String, Object> data = listData.get(i);
                paiements.add(new Paiement(
                        ((Long)data.get("id")).intValue(),
                        (double)data.get("montant"),
                        PaiementType.getPaiementType((String)data.get("type"))));
                i++;
            }
            return paiements;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBProblemException("Erreur lors de la récupération des paiements");
        }
    }

    /**
     * Permet de mettre à jour un paiement dans la base de données
     * @param idpaiement Int, le paiement à mettre à jour
     * @throws Exception si une erreur SQL est détectée
     */
    @Override
    public Paiement updatePaiement(List<Pair<String, Object>> udpateList, int idpaiement) throws Exception {
        List<Pair<String, Object>> listeWherePaiement = new ArrayList<>();
        listeWherePaiement.add(new Pair<>("id", idpaiement));
        try {
            ((MethodesPostgreSQL)this.methodesBD).update(udpateList,listeWherePaiement,this.table);
            return getPaiementById(idpaiement);
        } catch (Exception e) {
            throw new DBProblemException("Erreur lors de la mise à jour du paiement");
        }
    }

    /**
     * Permet de supprimer un paiement de la base de données
     * @param idpaiement int, l'id du paiement à supprimer
     * @throws Exception si une erreur SQL est détectée
     */
    @Override
    public void deletePaiement(int idpaiement) throws Exception {
        List<Pair<String, Object>> listeWherePaiement = new ArrayList<>();
        listeWherePaiement.add(new Pair<>("id", idpaiement));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(listeWherePaiement,this.table);
        } catch (Exception e) {
            throw new DBProblemException("Erreur lors de la suppression du paiement");
        }
    }

}
