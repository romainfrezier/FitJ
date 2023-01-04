package com.fitj.facades;

import com.fitj.classes.Coach;
import com.fitj.classes.ProgrammeNutrition;
import com.fitj.classes.Recette;
import com.fitj.dao.DAOProgrammeNutrition;
import com.fitj.dao.factory.FactoryDAO;
import com.fitj.enums.ProgrammeType;

import java.util.ArrayList;
import java.util.List;

/**
 * Facade utilisée pour les opérations sur les programmes nutrition
 * @see Facade
 * @author Paul Merceur
 */
public class FacadeProgrammeNutrition extends Facade{

    /**
     * Instance de la Facade, utilisée pour le pattern Singleton
     */
    private static FacadeProgrammeNutrition instance = null;

    /**
     * Instance du DAO
     */
    private static DAOProgrammeNutrition programmeNutritionDAO;

    /**
     * Constructeur de la FacadeProgrammeNutrition
     */
    protected FacadeProgrammeNutrition(){
        programmeNutritionDAO = FactoryDAO.getInstance().getDAOProgrammeNutrition();
    }

    /**
     * Méthode permettant de récupérer l'instance de la FacadeProgrammeNutrition
     * @return FacadeProgrammeNutrition, l'instance de la FacadeProgrammeNutrition
     */
    public static FacadeProgrammeNutrition getInstance(){
        if (instance == null){
            instance = new FacadeProgrammeNutrition();
        }
        return instance;
    }


    /**
     * Méthode permettant de récupérer tous les programmes nutrition
     * @return List<ProgrammeNutrition>, la liste des programmes nutrition
     * @throws Exception en cas d'erreur
     */
    public List<ProgrammeNutrition> getListeProgrammeNutrition() throws Exception{
        return this.programmeNutritionDAO.getAllProgrammeNutrition();
    }

    /**
     * Supprime un programme nutrition de la base de données
     * @param idProgramme int, l'id du programme à récupérer
     * @throws Exception en cas d'erreur
     */

    public void deleteProgrammeNutrition(int idProgramme) throws Exception{
        this.programmeNutritionDAO.supprimerProgrammeNutrition(idProgramme);
    }

    /**
     * Méthode permettant de récupérer un programme nutrition par son id
     * @param idProgramme int, l'id du programme à récupérer
     * @return ProgrammeNutrition, le programme nutrition
     * @throws Exception en cas d'erreur
     */
    public ProgrammeNutrition getProgrammeNutritionById(int idProgramme) throws Exception{
        return this.programmeNutritionDAO.getProgrammeNutritionById(idProgramme);
    }

    /**
     * Méthode permettant de récupérer tous les programmes nutrition d'un coach
     * @param coach Coach, le coach qui a créé le programme nutrition
     * return List<ProgrammeNutrition>, la liste des programmes nutrition
     * @throws Exception en cas d'erreur
     */
    public List<ProgrammeNutrition> getProgrammeNutritionByCoach(Coach coach) throws Exception{
        return this.programmeNutritionDAO.getProgrammeNutritionByCoach(coach.getId());
    }


    /**
     * Méthode permettant de modifier un programme nutrition
     * @param idProgramme int, l'id du programme nutrition à récupérer
     * @param nom String, le nouveau nom du programme nutrition
     * @param description String, la nouvelle description du programme nutrition
     * @param prix double, le nouveau prix du programme nutrition
     * @param type ProgrammeType, le nouveau type du programme nutrition
     * @param nbMois int, le nouveau nombre de mois du programme nutrition
     * @return ProgrammeNutrition, le programme nutrition modifié
     * @throws Exception en cas d'erreur
     */
    public ProgrammeNutrition updateProgrammeNutrition(int idProgramme, String nom, String description, double prix, ProgrammeType type, int nbMois) throws Exception{
        return this.programmeNutritionDAO.updateProgrammeNutrition(idProgramme, nom, description, prix, type, nbMois);
    }


    /**
     * Méthode permettant de créer un programme nutrition
     * @param nom String, le nom du programme nutrition
     * @param description String, la description du programme nutrition
     * @param prix double, le prix du programme nutrition
     * @param type ProgrammeType, le type du programme nutrition
     * @param nbMois int, le nombre de mois du programme nutrition
     * @param coach Coach, le coach qui a créé le programme nutrition
     * @return ProgrammeNutrition, le programme nutrition créé
     * @throws Exception en cas d'erreur
     */
    public ProgrammeNutrition createProgrammeNutrition(String nom, String description, double prix, ProgrammeType type, int nbMois, Coach coach, ArrayList<Recette> listeRecettes) throws Exception{
        return this.programmeNutritionDAO.createProgrammeNutrition(nom, description, prix, type, nbMois, coach, listeRecettes);
    }


    /**
     * Supprime une recette d'un programme nutrition
     * @param idProgrammeNutrition int, l'id du programme nutrition à récupérer
     * @param recette Recette, la recette à retirer au programme nutrition
     * @throws Exception si la recette est n'est pas dans le programme nutrition
     */
    public void removeRecetteFromProgrammeNutrition(int idProgrammeNutrition, Recette recette) throws Exception{
        this.programmeNutritionDAO.supprimerRecetteProgramme(recette, idProgrammeNutrition);
    }


    /**
     * Ajoute une recette à un programme nutrition
     * @param idProgrammeNutrition int, l'id du programme nutrition à récupérer
     * @param recette Recette, la recette à ajouter au programme nutrition
     * @throws Exception si la recette est déjà dans le programme nutrition
     */
    public void addRecetteToProgrammeNutrition(int idProgrammeNutrition, Recette recette) throws Exception{
        this.programmeNutritionDAO.ajouterRecetteProgramme(recette, idProgrammeNutrition);
    }

    /**
     * Méthode permettant de récupérer tous les programmes nutrition d'un client
     * @param idClient int, le client qui a acheté le programme nutrition
     * @return List<ProgrammeNutrition>, la liste des programmes nutrition
     * @throws Exception en cas d'erreur
     */
    public List<ProgrammeNutrition> getAllProgrammesNutritionsByClient(int idClient) throws Exception{
        return this.programmeNutritionDAO.getAllProgrammesNutritionsByClient(idClient);
    }

}
