package com.fitj.dao.postgresql;

import com.fitj.classes.Paiement;
import com.fitj.dao.DAOPaiement;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.enums.PaiementType;
import com.fitj.exceptions.DBProblemException;
import kotlin.Pair;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux paiements
 *
 * @author Etienne Tillier, Romain Frezier
 */
public class DAOPaiementPostgreSQL extends DAOPaiement {

    public DAOPaiementPostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    @Override
    public Paiement createPaiement(int idcommande, double prix, PaiementType paiementType) throws Exception {
        List<Pair<String, Object>> listeInsertPaiement = new ArrayList<>();
        listeInsertPaiement.add(new Pair<>("idcommande", idcommande));
        listeInsertPaiement.add(new Pair<>("montant", prix));
        listeInsertPaiement.add(new Pair<>("type", PaiementType.getPaiementType(paiementType)));
        int id = ((MethodesPostgreSQL) this.methodesBD).insert(listeInsertPaiement, "paiement");
        return this.getPaiementById(id);
    }

    @Override
    public Paiement getPaiementById(int id) throws Exception {
        List<Pair<String, Object>> listeWherePaiement = new ArrayList<>();
        listeWherePaiement.add(new Pair<>("id", id));
        return this.getAllPaiementsWhere(listeWherePaiement).get(0);
    }

    @Override
    public List<Paiement> getAllPaiements() throws Exception {
        List<Pair<String, Object>> listeWherePaiement = new ArrayList<>();
        return this.getAllPaiementsWhere(listeWherePaiement);
    }

    @Override
    public List<Paiement> getAllPaiementsWhere(List<Pair<String, Object>> whereList) throws Exception {
        List<Paiement> paiements = new ArrayList<>();
        try {
            ResultSet paiementData = ((MethodesPostgreSQL) this.methodesBD).selectWhere(whereList, this.table);
            while (paiementData.next()) {
                paiements.add(new Paiement(
                        paiementData.getInt("id"),
                        paiementData.getInt("montant"),
                        PaiementType.getPaiementType(paiementData.getString("type"))));
            }
            return paiements;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBProblemException("Erreur lors de la récupération des paiements");
        }
    }

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
