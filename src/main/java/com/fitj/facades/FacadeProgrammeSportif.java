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

/**
 * Facade utilisée pour les opérations sur les programmes
 * @see Facade
 * @author Paul Merceur
 */
public class FacadeProgrammeSportif extends Facade{

    /**
     * Instance de la Facade, utilisée pour le pattern Singleton
     */
    private static FacadeProgrammeSportif instance = null;

    /**
     * Instance du DAO
     */
    private static DAOProgrammeSportif programmeSportifDAO;

    /**
     * Constructeur de la FacadeProgrammeSportif
     */
    protected FacadeProgrammeSportif(){

    }

    /**
     * Méthode permettant de récupérer l'instance de la FacadeProgrammeSportif
     * @return FacadeProgrammeSportif, l'instance de la FacadeProgrammeSportif
     */
    public static FacadeProgrammeSportif getInstance(){
        if (instance == null){
            instance = new FacadeProgrammeSportif();
            programmeSportifDAO = FactoryDAO.getInstance().getDAOProgrammeSportif();
        }
        return instance;
    }



    /**
     * Méthode permettant de récupérer tous les programmes sportifs
     * @return List<ProgrammeSportif>, la liste des programmes sportifs
     * @throws Exception en cas d'erreur
     */
    public List<ProgrammeSportif> getListeProgrammeSportif() throws Exception{
        return programmeSportifDAO.getAllProgrammeSportif();
    }

    /**
     * Supprime un programme sportif de la base de données
     * @param idProgramme int, l'id du programme à supprimer
     * @throws Exception en cas d'erreur
     */
    public void deleteProgrammeSportif(int idProgramme) throws Exception{
        programmeSportifDAO.supprimerProgrammeSportif(idProgramme);
    }

    /**
     * Récupérer un programme sportif de la base de données, à partir de son id
     * @param idProgramme int, l'id du programme à récupérer
     * @return ProgrammeSportif, le programme sportif
     * @throws Exception en cas d'erreur
     */
    public ProgrammeSportif getProgrammeSportifById(int idProgramme) throws Exception{
        return this.programmeSportifDAO.getProgrammeSportifById(idProgramme);
    }

    /**
     * Récupérer tous les programmes sportifs créés par un coach
     * @param coach Coach, le coach qui a créé le programme sportif
     * @return List<ProgrammeSportif>, la liste des programmes sportifs créés par le coach
     * @throws Exception en cas d'erreur
     */
    public List<ProgrammeSportif> getProgrammeSportifByCoach(Coach coach) throws Exception{
        return programmeSportifDAO.getAllProgrammeSportifByCoach(coach.getId());
    }


    /**
     * Modifier un programme sportif
     * @param idProgramme int, l'id du programme à modifier
     * @param nom String, le nouveau nom du programme
     * @param description String, la nouvelle description du programme
     * @param prix double, le nouveau prix du programme
     * @param type ProgrammeType, le nouveau type du programme
     * @param nbMois int, le nouveau nombre de mois du programme
     * @throws Exception en cas d'erreur
     */
    public ProgrammeSportif updateProgrammeSportif(int idProgramme, String nom, String description, double prix, ProgrammeType type, int nbMois) throws Exception{
        return programmeSportifDAO.updateProgrammeSportif(idProgramme, nom, description, prix, type, nbMois);
    }


    /**
     * Créer un nouveau programme sportif
     * @param nom String, le nom du programme
     * @param description String, la description du programme
     * @param prix double, le prix du programme
     * @param type ProgrammeType, le type du programme
     * @param nbMois int, le nombre de mois du programme
     * @param coach Coach, le coach qui a créé le programme
     * @return ProgrammeSportif, le programme sportif créé
     * @throws Exception en cas d'erreur
     */
    public ProgrammeSportif createProgrammeSportif(String nom, String description, double prix, ProgrammeType type, int nbMois, Coach coach, ArrayList<Seance> listeSeances) throws Exception{
        return programmeSportifDAO.createProgrammeSportif(nom, description, prix, type, nbMois, coach, listeSeances);
    }


    /**
     * Supprime une séance d'un programme sportif
     * @param idProgrammeNutrition int, l'id du programme sportif à récupérer
     * @param seance Seance, la recette à supprimer du programme sportif
     * @throws Exception si la recette n'est pas dans le programme sportif
     */
    public void removeSeanceFromProgrammeSportif(int idProgrammeNutrition, Seance seance) throws Exception{
        programmeSportifDAO.supprimerSeanceProgramme(seance, idProgrammeNutrition);
    }


    /**
     * Ajoute une séance à un programme sportif
     * @param idProgrammeNutrition int, l'id du programme sportif à récupérer
     * @param seance Seance, la recette à ajouter au programme sportif
     * @throws Exception si la recette est déjà dans le programme sportif
     */
    public void addSeanceToProgrammeSportif(int idProgrammeNutrition, Seance seance) throws Exception{
        programmeSportifDAO.ajouterSeanceProgramme(seance, idProgrammeNutrition);
    }

    /**
     * Récupérer tous les programmes sportifs d'un client
     * @param idClient int, l'id du client à récupérer
     * @return List<ProgrammeSportif>, la liste des programmes sportifs du client
     * @throws Exception en cas d'erreur
     */
    public List<ProgrammeSportif> getAllProgrammesSportifsByClient(int idClient) throws Exception{
        return programmeSportifDAO.getAllProgrammeSportifByClient(idClient);
    }

}
