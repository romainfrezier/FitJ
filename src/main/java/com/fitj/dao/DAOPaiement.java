package com.fitj.dao;

import com.fitj.classes.Paiement;
import com.fitj.enums.PaiementType;
import kotlin.Pair;

import java.util.List;


/**
 * Classe parente de tous les modèles paiement qui permettent d'interagir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les paiements
 * @see DAO
 * @author Etienne Tillier, Romain Frezier
 */
public abstract class DAOPaiement extends DAO {
    public DAOPaiement() {
        super("paiement");
    }

    /**
     * Ajoute un paiement dans la base de donnée
     * @param idcommande int, l'id de la commande
     * @param prix double, le prix du paiement
     * @param paiementType PaiementType, le type de paiement
     * @throws Exception si une erreur SQL survient
     * @see PaiementType
     */
    public abstract Paiement createPaiement(int idcommande, double prix, PaiementType paiementType) throws Exception;

    /**
     * Retourne un paiement à partir de son id
     * @param id int, l'id du paiement
     * @return Paiement, le paiement
     * @throws Exception si une erreur SQL survient
     */
    public abstract Paiement getPaiementById(int id) throws Exception;

    /**
     * Retourne tous les paiements
     * @return List<Paiement>, la liste des paiements
     * @throws Exception si une erreur SQL survient
     */
    public abstract List<Paiement> getAllPaiements() throws Exception;

    /**
     * Retourne tous les paiements respectant une certaine condition
     * @param whereList List<Pair<String, Object>>, la liste des conditions
     * @return List<Paiement>, la liste des paiements
     * @throws Exception si une erreur SQL survient
     */
    public abstract List<Paiement> getAllPaiementsWhere(List<Pair<String, Object>> whereList) throws Exception;

    /**
     * retourne le paiement modifié
     * @param udpateList List<Pair<String, Object>>, la liste des champs à modifier
     * @param idpaiement int, le paiement à modifier
     * @return Paiement, le paiement modifié
     * @throws Exception si une erreur SQL survient
     */
    public abstract Paiement updatePaiement(List<Pair<String, Object>> udpateList, int idpaiement) throws Exception;

    /**
     * Supprime un paiement
     * @param idpaiement int, l'id du paiement
     * @throws Exception si une erreur SQL survient
     */
    public abstract void deletePaiement(int idpaiement) throws Exception;
}
