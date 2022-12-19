package com.fitj.dao;

import com.fitj.classes.Sport;

import java.util.List;

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

    /**
     * Récupère tous les sports dans la base de donnée
     * @return List<Sport>, la liste de tous les sports
     * @throws Exception si une erreur est détectée
     */
    public abstract List<Sport> getAllSports() throws Exception;
}
