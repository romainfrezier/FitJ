package com.fitj.dao;

import com.fitj.classes.*;
import com.fitj.enums.ProgrammeType;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe parente de tous les modèles programme nutrition qui permettent d'interagir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les programmes nutrition
 * @see DAO
 * @author Etienne Tillier, Romain Frezier
 */
public abstract class DAOProgrammeNutrition extends DAO{

    public DAOProgrammeNutrition() {
        super("programmenutrition");
    }

    /**
     * @param nom String, le nom du programme
     * @param description String, la description du programme
     * @param prix double, le prix du programme
     * @param type ProgrammeType, le type du programme
     * @param nbMois int, le nombre de mois du programme
     * @param coach Coach, le coach du programme
     * @param listeRecettes ArrayList<Recette>, la liste des recettes du programme
     * @return le ProgrammeNutrition crée
     * @throws Exception si la requête échoue
     */
    public abstract ProgrammeNutrition createProgrammeNutrition(String nom, String description, double prix, ProgrammeType type, int nbMois, Coach coach, ArrayList<Recette> listeRecettes) throws Exception;

    /**
     * @param id int, l'id du programme
     * @return le ProgrammeNutrition récupéré dans la base de donnée
     * @throws Exception si la requête échoue
     */
    public abstract ProgrammeNutrition getProgrammeNutritionById(int id) throws Exception;

    /**
     * @param coachId int, l'id du coach
     * @return la liste des programmes nutrition du coach
     * @throws Exception si le coach n'existe pas
     */
    public abstract List<ProgrammeNutrition> getProgrammeNutritionByCoach(int coachId) throws Exception;

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
    public abstract ProgrammeNutrition updateProgrammeNutrition(int idProgramme, String nom, String description, double prix, ProgrammeType type, int nbMois) throws Exception;

    /**
     * @param updateList List<Pair<String,Object>>, la liste des objet à modifié dans la table pour le programme nutrition
     * @param id int, l'id du programme
     * @return le ProgrammeNutrition modifié dans la base de donnée
     * @throws Exception si la requête échoue
     */
    public abstract ProgrammeNutrition updateProgrammeNutrition(List<Pair<String,Object>> updateList, int id) throws Exception;

    /**
     * Supprimer dans la base de donnée le programme nutrition
     * @param id int, l'id du programme
     * @throws Exception si la requête échoue
     */
    public abstract void supprimerProgrammeNutrition(int id) throws Exception;

    /**
     * @return la liste de tous les programmes nutrition
     * @throws Exception si la requête échoue
     */
    public abstract List<ProgrammeNutrition> getAllProgrammeNutrition() throws Exception;

    /**
     * @return la liste de tous les programmes nutrition
     * @param whereList List<Pair<String,Object>>, la liste des conditions du where pour la requête
     * @throws Exception si la requête échoue
     */
    public abstract List<ProgrammeNutrition> getAllProgrammeNutritionWhere(List<Pair<String,Object>> whereList) throws Exception;

    /**
     * @param id int, l'id du programme
     * @return la liste des recettes du programme
     * @throws Exception si la requête échoue
     */
    public abstract  List<Recette> getRecettes(int id) throws Exception;

    /**
     * Ajouter une recette au programme nutritif
     * @param recette Recette, la recette à ajouter au programme nutritif
     * @param id int, l'id du programme nutritif
     * @throws Exception si la requête échoue
     */
    public abstract void ajouterRecetteProgramme(Recette recette, int id) throws Exception;

    /**
     * Supprimer une recette du programme nutritif
     * @param recette Recette, la recette à supprimer du programme nutritif
     * @param id int, l'id du programme nutritif
     * @throws Exception si la requête échoue
     */
    public abstract void supprimerRecetteProgramme(Recette recette, int id) throws Exception;

    /**
     * @param idClient int, l'id du client
     * @return la liste des programmes nutrition du client
     * @throws Exception si le client n'existe pas
     */
    public abstract List<ProgrammeNutrition> getAllProgrammesNutritionsByClient(int idClient) throws Exception;





}
