package com.fitj.dao;

import com.fitj.enums.PaiementType;


/**
 * Classe parente de tous les modèles paiement qui permettent d'intéragir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les paiements
 * @see DAO
 * @author Etienne Tillier
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
    public abstract void createPaiement(int idcommande, double prix, PaiementType paiementType) throws Exception;
}
