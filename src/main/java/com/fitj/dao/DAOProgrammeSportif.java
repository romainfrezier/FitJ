package com.fitj.dao;

import com.fitj.classes.*;
import com.fitj.enums.ProgrammeType;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe parente de tous les modèles programme nutrition qui permettent d'interagir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les programmes sportifs
 * @see DAO
 * @author Etienne Tillier, Romain Frezier
 */
public abstract class DAOProgrammeSportif extends DAO{

    public DAOProgrammeSportif() {
        super("programmesportif");
    }

    /**
     * @param nom String, le nom du programme
     * @param description String, la description du programme
     * @param prix double, le prix du programme
     * @param type ProgrammeType, le type du programme
     * @param nbMois int, le nombre de mois du programme
     * @param coach Coach, le coach du programme
     * @param listeSeances ArrayList<Seance>, la liste des recettes du programme
     * @return le ProgrammeNutrition crée
     * @throws Exception si la requête échoue
     */
    public abstract ProgrammeSportif createProgrammeSportif(String nom, String description, double prix, ProgrammeType type, int nbMois, Coach coach, ArrayList<Seance> listeSeances) throws Exception;

    /**
     * @param id int, l'id du programme
     * @return le ProgrammeSportif récupéré dans la base de donnée
     * @throws Exception si la requête échoue
     */
    public abstract ProgrammeSportif getProgrammeSportifById(int id) throws Exception;

    /**
     * @param updateList List<Pair<String,Object>>, la liste des objet à modifié dans la table pour le programme sportif
     * @param id int, l'id du programme
     * @return le ProgrammeSportif modifié dans la base de donnée
     * @throws Exception si la requête échoue
     */
    public abstract ProgrammeSportif updateProgrammeSportif(List<Pair<String,Object>> updateList, int id) throws Exception;

    /**
     * @param idProgramme int, l'id du programme à modifier
     * @param nom String, le nouveau nom du programme
     * @param description String, la nouvelle description du programme
     * @param prix double, le nouveau prix du programme
     * @param type ProgrammeType, le nouveau type du programme
     * @param nbMois int, le nouveau nombre de mois du programme
     * @return le ProgrammeNutrition modifié
     * @throws Exception si la requête échoue
     */
    public abstract ProgrammeSportif updateProgrammeSportif(int idProgramme, String nom, String description, double prix, ProgrammeType type, int nbMois) throws Exception;

    /**
     * Supprimer dans la base de donnée le programme sportif
     * @param id int, l'id du programme
     * @throws Exception si la requête échoue
     */
    public abstract void supprimerProgrammeSportif(int id) throws Exception;

    /**
     * @return la liste de tous les programmes sportif
     * @throws Exception si la requête échoue
     */
    public abstract List<ProgrammeSportif> getAllProgrammeSportif() throws Exception;

    /**
     * @return la liste de tous les programmes sportif
     * @param whereList List<Pair<String,Object>>, la liste des conditions du where pour la requête
     * @throws Exception si la requête échoue
     */
    public abstract List<ProgrammeSportif> getAllProgrammeSportifWhere(List<Pair<String,Object>> whereList) throws Exception;

    /**
     * @param id int, l'id du programme
     * @return la liste des séances du programme
     * @throws Exception si la requête échoue
     */
    public abstract List<Seance> getSeances(int id) throws Exception;

    /**
     * Ajouter une séance au programme sportif
     * @param seance Seance, la séance à ajouter au programme sportif
     * @param id int, l'id du programme sportif
     * @throws Exception si la requête échoue
     */
    public abstract void ajouterSeanceProgramme(Seance seance, int id) throws Exception;

    /**
     * Supprimer une séance du programme sportif
     * @param seance Seance, la séance à supprimer du programme sportif
     * @param id int, l'id du programme sportif
     * @throws Exception si la requête échoue
     */
    public abstract void supprimerSeanceProgramme(Seance seance, int id) throws Exception;

    /**
     * @param idCoach int, l'id du coach
     * @return la liste des programmes sportif du coach
     * @throws Exception si la requête échoue
     */
    public abstract List<ProgrammeSportif> getAllProgrammeSportifByCoach(int idCoach) throws Exception;

    /**
     * @param idClient int, l'id du client
     * @return la liste des programmes sportif du client
     * @throws Exception si la requête échoue
     */
    public abstract List<ProgrammeSportif> getAllProgrammeSportifByClient(int idClient) throws Exception;


}
