package com.fitj.facades;

import com.fitj.classes.*;
import com.fitj.dao.DAOClient;
import com.fitj.dao.factory.FactoryDAO;

import java.util.List;

/**
 * FacadeCoach, classe qui permet de faire le lien entre la DAO et le Controller
 * @see Facade
 * @author Paul Merceur
 */
public class FacadeCoach extends Facade {

    /**
     * Instance du DAOClient
     */
    protected DAOClient daoClient;

    /**
     * Instance de la facade, utilisée pour le singleton
     */
    private static FacadeCoach instance = null;

    /**
     * Constructeur de la facade
     */
    protected FacadeCoach(){
        this.daoClient = FactoryDAO.getInstance().getDAOClient();
    }

    /**
     * Méthode permettant de récupérer l'instance de la facade
     * @return instance de la facade
     */
    public static FacadeCoach getInstance(){
        if (instance == null){
            instance = new FacadeCoach();
        }
        return instance;
    }

    /**
     * Méthode permettant de récupérer tous les coachs
     * @return List<Coach>, la liste des coachs
     * @throws Exception en cas d'erreur
     */
    public List<Coach> getAllCoachs() throws Exception {
        return this.daoClient.getAllCoach();
    }

    /**
     * Méthode permettant de transformer un client en admin
     * @param idObjectSelected int, l'id du client à transformer
     * @throws Exception en cas d'erreur
     */
    public Admin clientBecomeAdmin(int idObjectSelected) throws Exception {
        return this.daoClient.coachBecomeAdmin(idObjectSelected);
    }

    /**
     * Méthode permettant de récupérer tous les clients d'un coach
     * @param coachId int, l'id du coach
     * @return List<Client>, la liste des clients
     * @throws Exception en cas d'erreur
     */
    public List<Client> getAllClientsForACoach(int coachId) throws Exception {
        return this.daoClient.getAllClientForACoach(coachId);
    }

    /**
     * Méthode permettant de récupérer un coach à partir de son id
     * @param id int, l'id du coach
     * @return Coach, le coach
     * @throws Exception en cas d'erreur
     */
    public Coach getCoachById(int id) throws Exception {
        return (Coach)this.daoClient.getClientById(id);
    }

    /**
     * Méthode permettant de récupérer les séances d'un coach
     * @param coach Coach, le coach
     * @return List<Seance>, la liste des séances
     * @throws Exception en cas d'erreur
     */
    public List<Seance> getSeanceByCoach(Coach coach) throws Exception {
        return FactoryDAO.getInstance().getDAOSeance().getAllSeancesFromCoach(coach.getId());
    }

    /**
     * Méthode permettant de récupérer les programmes sportifs d'un coach
     * @param coach Coach, le coach
     * @return List<ProgrammeSportif>, la liste des programmes sportifs
     * @throws Exception en cas d'erreur
     */
    public List<ProgrammeSportif> getCoachProgrammeSportifs(Coach coach) throws Exception {
        return FactoryDAO.getInstance().getDAOProgrammeSportif().getAllProgrammeSportifByCoach(coach.getId());
    }

    /**
     * Méthode permettant de récupérer les programmes nutritionnels d'un coach
     * @param coach Coach, le coach
     * @return List<ProgrammeNutritionnel>, la liste des programmes nutritionnels
     * @throws Exception en cas d'erreur
     */
    public List<ProgrammeNutrition> getCoachProgrammeNutrition(Coach coach) throws Exception {
        return FactoryDAO.getInstance().getDAOProgrammeNutrition().getProgrammeNutritionByCoach(coach.getId());
    }

    /**
     * Méthode permettant de récupérer les packs d'un coach
     * @param coach Coach, le coach
     * @return List<Pack>, la liste des packs
     * @throws Exception en cas d'erreur
     */
    public List<Pack> getAllPackByCoach(Coach coach) throws Exception {
        return FactoryDAO.getInstance().getDAOPack().getAllPackByCoach(coach.getId());
    }

}
