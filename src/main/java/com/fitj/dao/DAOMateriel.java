package com.fitj.dao;

import com.fitj.classes.Materiel;
import kotlin.Pair;

import java.util.List;

/**
 * Classe parente de tous les DAO matériel qui permettent d'interagir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec le matériel
 * @see DAO
 * @author Etienne Tillier, Romain Frezier
 */
public abstract class DAOMateriel extends DAO {
    public DAOMateriel() {
        super("materiel");
    }

    /**
     * Ajoute un materiel dans la base de donnée
     * @param nom String, le nom du sport
     * @throws Exception si le matériel existe déjà
     */
    public abstract Materiel createMateriel(String nom) throws Exception;

    /**
     * @param id int, l'id du materiel
     * @return Materiel, le materiel dans la base de donnée contenant l'id rentré en paramètre
     * @throws Exception si le materiel n'existe pas
     */
    public abstract Materiel getMaterielById(int id) throws Exception;

    /**
     * @param nom String, le nom du sport
     * @return Materiel, le materiel dans la base de donnée contenant le nom rentré en paramètre
     * @throws Exception si le materiel n'existe pas
     */
    public abstract Materiel getMaterielByNom(String nom) throws Exception;

    /**
     * @return List<Materiel>, tous le materiel présents dans la base de donnée dans une List
     * @throws Exception si aucun materiel n'existe
     */
    public abstract List<Materiel> getAllMateriel() throws Exception;


    /**
     * @return List<Materiel>, tous le materiel présents dans la base de donnée dans une List
     * @param whereList List<Pair<String,Object>>, la liste des conditions du where pour la requête
     * @throws Exception si aucun materiel n'existe
     */
    public abstract List<Materiel> getAllMaterielWhere(List<Pair<String,Object>> whereList) throws Exception;


    /**
     * Supprime le materiel de la base de donnée
     * @param id int, l'id du materiel
     * @throws Exception si le materiel n'existe pas
     */
    public abstract void supprimerMateriel(int id) throws Exception;

    /**
     * Met à jour le materiel dans la base de donnée
     * @param updateList List<Pair<String,Object>>, la liste du setter
     * @param id int, l'id du materiel
     * @throws Exception si le materiel n'existe pas
     */
    public abstract Materiel updateMateriel(List<Pair<String,Object>> updateList, int id) throws Exception;

}
