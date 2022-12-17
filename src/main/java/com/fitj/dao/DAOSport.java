package com.fitj.dao;

import com.fitj.classes.Sport;

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


    /**
     * Ajoute un sport dans la base de donnée
     * @param nom String, le nom du sport
     * @throws Exception
     */
    public abstract void createSport(String nom) throws Exception;

    /**
     * @param id int, l'id du sport
     * @return le sport dans la base de donnée contenant l'id rentré en paramètre
     * @throws Exception
     */
    public abstract Sport getSportById(int id) throws Exception;
}
