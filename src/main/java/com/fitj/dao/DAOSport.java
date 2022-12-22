package com.fitj.dao;

import com.fitj.classes.Sport;
import kotlin.Pair;

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
    public abstract Sport createSport(String nom) throws Exception;

    /**
     * @param id int, l'id du sport
     * @return le sport dans la base de donnée contenant l'id rentré en paramètre
     * @throws Exception
     */
    public abstract Sport getSportById(int id) throws Exception;

    /**
     * @param nom String, le nom du sport
     * @return le sport dans la base de donnée contenant le nom rentré en paramètre
     * @throws Exception
     */
    public abstract Sport getSportByNom(String nom) throws Exception;

    /**
     * @return tous les sports présents dans la base de donnée dans une List
     * @throws Exception
     */
    public abstract List<Sport> getAllSport() throws Exception;


    /**
     * @return tous les sports présents dans la base de donnée dans une List
     * @param whereList List<Pair<String,Object>>, la liste des conditions du where pour la requête
     * @throws Exception
     */
    public abstract List<Sport> getAllSportWhere(List<Pair<String,Object>> whereList) throws Exception;


    /**
     * Supprime le sport de la base de donnée
     * @param id int, l'id du sport
     * @throws Exception
     */
    public abstract void supprimerSport(int id) throws Exception;

    /**
     * Met à jour dans la base de donnée le sport
     * @param updateList List<Pair<String,Object>>, la liste du setter
     * @param id int, l'id du sport
     */
    public abstract Sport updateSport(List<Pair<String,Object>> updateList, int id) throws Exception;
}
