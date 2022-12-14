package com.fitj.dao;

/**
 * Classe parente de tous les modèles sport qui permettent d'intéragir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les sports
 *
 * Classe abstraite non instanciable
 *
 * @author Etienne Tillier
 */
public abstract class DAOSport extends DAO {
    public DAOSport() {
        super("sport");
    }
}
