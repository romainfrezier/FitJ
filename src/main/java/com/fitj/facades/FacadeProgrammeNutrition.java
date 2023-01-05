package com.fitj.facades;

import com.fitj.classes.Coach;
import com.fitj.classes.ProgrammeNutrition;
import com.fitj.classes.Recette;
import com.fitj.dao.DAOProgrammeNutrition;
import com.fitj.dao.factory.FactoryDAO;
import com.fitj.enums.ProgrammeType;

import java.util.ArrayList;
import java.util.List;

public class FacadeProgrammeNutrition extends Facade{

    private static FacadeProgrammeNutrition instance = null;

    private static DAOProgrammeNutrition programmeNutritionDAO;
    protected FacadeProgrammeNutrition(){
        programmeNutritionDAO = FactoryDAO.getInstance().getDAOProgrammeNutrition();
    }

    public static FacadeProgrammeNutrition getInstance(){
        if (instance == null){
            instance = new FacadeProgrammeNutrition();
        }
        return instance;
    }


    /**
     * @return la liste des programmes nutritions de la base de données
     * @throws Exception si la requête échoue
     */
    public List<ProgrammeNutrition> getListeProgrammeNutrition() throws Exception{
        return programmeNutritionDAO.getAllProgrammeNutrition();
    }

    /**
     * Supprime un programme nutrition de la base de données
     * @param idProgramme l'id du programme à récupérer
     * @throws Exception si la requête échoue
     */

    public void deleteProgrammeNutrition(int idProgramme) throws Exception{
        programmeNutritionDAO.supprimerProgrammeNutrition(idProgramme);
    }

    /**
     * @param idProgramme l'id du programme à récupérer
     * @return le programme nutrition correspondant à l'id
     * @throws Exception si la requête échoue
     */
    public ProgrammeNutrition getProgrammeNutritionById(int idProgramme) throws Exception{
        return programmeNutritionDAO.getProgrammeNutritionById(idProgramme);
    }

    /**
     * @param coach le coach qui a créé le programme nutrition
     * @return la liste des programmes nutritions créées par le coach
     * @throws Exception si la requête échoue
     */
    public List<ProgrammeNutrition> getProgrammeNutritionByCoach(int coach) throws Exception{
        return programmeNutritionDAO.getProgrammeNutritionByCoach(coach);
    }


    /**
     * @param idProgramme l'id du programme nutrition à récupérer
     * @param nom le nouveau nom du programme nutrition
     * @param description la nouvelle description du programme nutrition
     * @param prix le nouveau prix du programme nutrition
     * @param type le nouveau type du programme nutrition
     * @param nbMois le nouveau nombre de mois du programme nutrition
     * @return le programme nutrition modifié
     * @throws Exception si la requête échoue
     */
    public ProgrammeNutrition updateProgrammeNutrition(int idProgramme, String nom, String description, double prix, ProgrammeType type, int nbMois) throws Exception{
        return programmeNutritionDAO.updateProgrammeNutrition(idProgramme, nom, description, prix, type, nbMois);
    }


    /**
     * @param nom le nom du programme nutrition
     * @param description la description du programme nutrition
     * @param prix le prix du programme nutrition
     * @param type le type du programme nutrition
     * @param nbMois le nombre de mois du programme nutrition
     * @param coach le coach qui a créé le programme nutrition
     * @param listeRecettes la liste des recettes du programme nutrition
     * @return le programme nutrition créé
     * @throws Exception si la requête échoue
     */
    public ProgrammeNutrition createProgrammeNutrition(String nom, String description, double prix, ProgrammeType type, int nbMois, Coach coach, ArrayList<Recette> listeRecettes) throws Exception{
        return programmeNutritionDAO.createProgrammeNutrition(nom, description, prix, type, nbMois, coach, listeRecettes);
    }


    /**
     * Supprime une recette d'un programme nutrition
     * @param idProgrammeNutrition l'id du programme nutrition à récupérer
     * @param recette la recette à ajouter au programme nutrition
     * @throws Exception si la recette est déjà dans le programme nutrition
     */
    public void removeRecetteFromProgrammeNutrition(int idProgrammeNutrition, Recette recette) throws Exception{
        programmeNutritionDAO.supprimerRecetteProgramme(recette, idProgrammeNutrition);
    }


    /**
     * Ajoute une recette à un programme nutrition
     * @param idProgrammeNutrition l'id du programme nutrition à récupérer
     * @param recette la recette à ajouter au programme nutrition
     * @throws Exception si la recette est déjà dans le programme nutrition
     */
    public void addRecetteToProgrammeNutrition(int idProgrammeNutrition, Recette recette) throws Exception{
        programmeNutritionDAO.ajouterRecetteProgramme(recette, idProgrammeNutrition);
    }

    public List<ProgrammeNutrition> getAllProgrammesNutritionsByClient(int idClient) throws Exception{
        return programmeNutritionDAO.getAllProgrammesNutritionsByClient(idClient);
    }

}
