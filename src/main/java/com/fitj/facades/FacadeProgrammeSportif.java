package com.fitj.facades;

import com.fitj.classes.*;
import com.fitj.dao.DAOProgrammeNutrition;
import com.fitj.dao.DAOProgrammeSportif;
import com.fitj.dao.factory.FactoryDAO;
import com.fitj.enums.ProgrammeType;
import com.fitj.interfaces.Ingredient;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;

public class FacadeProgrammeSportif extends Facade{


    private static FacadeProgrammeSportif instance = null;


    private static DAOProgrammeSportif programmeSportifDAO;
    protected FacadeProgrammeSportif(){

    }

    public static FacadeProgrammeSportif getInstance(){
        if (instance == null){
            instance = new FacadeProgrammeSportif();
            programmeSportifDAO = FactoryDAO.getInstance().getDAOProgrammeSportif();
        }
        return instance;
    }



    /**
     * @return la liste des programmes sportifs de la base de données
     * @throws Exception
     */
    public List<ProgrammeSportif> getListeProgrammeSportif() throws Exception{
        return programmeSportifDAO.getAllProgrammeSportif();
    }

    /**
     * Supprime un programme sportif de la base de données
     * @param idProgramme l'id du programme à récupérer
     * @throws Exception
     */

    public void deleteProgrammeSportif(int idProgramme) throws Exception{
        programmeSportifDAO.supprimerProgrammeSportif(idProgramme);
    }

    /**
     * @param idProgramme l'id du programme à récupérer
     * @return le programme sportif correspondant à l'id
     * @throws Exception
     */
    public ProgrammeSportif getProgrammeSportifById(int idProgramme) throws Exception{
        return this.programmeSportifDAO.getProgrammeSportifById(idProgramme);
    }

    /**
     * @param coach le coach qui a créé le programme sportif
     * @return la liste des programmes sportifs créées par le coach
     * @throws Exception
     */
    public List<ProgrammeSportif> getProgrammeSportifByCoach(Coach coach) throws Exception{
        return programmeSportifDAO.getAllProgrammeSportifByCoach(coach.getId());
    }


    /**
     * @param idProgramme l'id du programme sportif à récupérer
     * @param nom le nouveau nom du programme sportif
     * @param description la nouvelle description du programme sportif
     * @param prix le nouveau prix du programme sportif
     * @param type le nouveau type du programme sportif
     * @param nbMois le nouveau nombre de mois du programme sportif
     * @return le programme sportif modifié
     * @throws Exception
     */
    public ProgrammeSportif updateProgrammeSportif(int idProgramme, String nom, String description, double prix, ProgrammeType type, int nbMois) throws Exception{
        return programmeSportifDAO.updateProgrammeSportif(idProgramme, nom, description, prix, type, nbMois);
    }


    /**
     * @param nom le nom du programme sportif
     * @param description la description du programme sportif
     * @param prix le prix du programme sportif
     * @param type le type du programme sportif
     * @param nbMois le nombre de mois du programme sportif
     * @param coach le coach qui a créé le programme sportif
     * @param listeSeances la liste des séances du programme sportif
     * @return le programme sportif créé
     * @throws Exception
     */
    public ProgrammeSportif createProgrammeSportif(String nom, String description, double prix, ProgrammeType type, int nbMois, Coach coach, ArrayList<Seance> listeSeances) throws Exception{
        return programmeSportifDAO.createProgrammeSportif(nom, description, prix, type, nbMois, coach, listeSeances);
    }


    /**
     * Supprime une séance d'un programme sportif
     * @param idProgrammeNutrition l'id du programme sportif à récupérer
     * @param seance la recette à ajouter au programme sportif
     * @throws Exception si la recette est déjà dans le programme sportif
     */
    public void removeSeanceFromProgrammeSportif(int idProgrammeNutrition, Seance seance) throws Exception{
        programmeSportifDAO.supprimerSeanceProgramme(seance, idProgrammeNutrition);
    }


    /**
     * Ajoute une séance à un programme sportif
     * @param idProgrammeNutrition l'id du programme sportif à récupérer
     * @param seance la recette à ajouter au programme sportif
     * @throws Exception si la recette est déjà dans le programme sportif
     */
    public void addSeanceToProgrammeSportif(int idProgrammeNutrition, Seance seance) throws Exception{
        programmeSportifDAO.ajouterSeanceProgramme(seance, idProgrammeNutrition);
    }

    /**
     * @param idClient l'id du client à récupérer
     * @return la liste des programmes sportifs achetés par le client
     * @throws Exception
     */
    public List<ProgrammeSportif> getAllProgrammesSportifsByClient(int idClient) throws Exception{
        return programmeSportifDAO.getAllProgrammeSportifByClient(idClient);
    }
}
