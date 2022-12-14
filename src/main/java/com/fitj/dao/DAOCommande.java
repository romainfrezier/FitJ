package com.fitj.dao;

/**
 * Classe parente de tous les modèles commande qui permettent d'intéragir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les commandes
 *
 * Classe abstraite non instanciable
 *
 * @author Etienne Tillier
 */
public abstract class DAOCommande extends DAO {
    public DAOCommande() {
        super("commande");
    }
}
