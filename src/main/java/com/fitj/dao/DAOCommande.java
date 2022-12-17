package com.fitj.dao;

import com.fitj.classes.Commande;

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

    /**
     * @param id int, l'id de la commande
     * @return un objet de type Commande dans la base de donnée avec l'id rentré en paramètre
     * @throws Exception
     */
    public abstract Commande getCommandeByID(int id) throws Exception;

}
