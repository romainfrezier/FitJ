package com.fitj.dao;

/**
 * Classe parente de tous les modèles paiement qui permettent d'intéragir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les paiements
 *
 * Classe abstraite non instanciable
 *
 * @author Etienne Tillier
 */
public abstract class DAOPaiement extends DAO {
    public DAOPaiement() {
        super("paiement");
    }
}
