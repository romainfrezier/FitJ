package com.fitj.facades;

import com.fitj.classes.Sport;
import com.fitj.dao.DAOSport;
import com.fitj.dao.factory.FactoryDAO;
import kotlin.Pair;
import java.util.*;

/**
 * Facade utilisée par les opérations sur les sports
 * @see Facade
 * @author Paul Merceur
 */
public class FacadeSport extends Facade {

    /**
     * Instance du DAO
     */
    protected DAOSport daoSport;

    /**
     * Instance de la Facade, utilisée pour le pattern Singleton
     */
    private static FacadeSport instance = null;

    /**
     * Constructeur de la FacadeSport
     */
    protected FacadeSport(){
        this.daoSport = FactoryDAO.getInstance().getDAOSport();
    }

    /**
     * Méthode permettant de récupérer l'instance de la FacadeSport
     * @return FacadeSport, l'instance de la FacadeSport
     */
    public static FacadeSport getInstance(){
        if (instance == null){
            instance = new FacadeSport();
        }
        return instance;
    }

    /**
     * Méthode permettant de récupérer tous les sports
     * @return List<Sport>, la liste des sports
     * @throws Exception en cas d'erreur
     */
    public List<Sport> getAllSports() throws Exception {
        return this.daoSport.getAllSport();
    }

    /**
     * Méthode permettant de créer un sport
     * @param sport String, le nom du sport
     * @throws Exception en cas d'erreur
     */
    public void createSport(String sport) throws Exception {
        this.daoSport.createSport(sport);
    }

    /**
     * Méthode permettant de supprimer un sport
     * @param sport String, le nom du sport
     * @throws Exception en cas d'erreur
     */
    public void deleteSport(int id) throws Exception {
        this.daoSport.supprimerSport(id);
    }

    /**
     * Méthode permettant de modifier un sport
     * @param id int, l'id du sport
     * @param sport String, le nom du sport
     * @throws Exception en cas d'erreur
     */
    public void updateSport(int id, String sport) throws Exception {
        List<Pair<String, Object>> updateValue = new ArrayList<>();
        updateValue.add(new Pair<>("nom", sport));
        this.daoSport.updateSport(updateValue, id);
    }

    /**
     * Méthode permettant de récupérer un sport par son nom
     * @param idSport int, l'id du sport
     * @return Sport, le sport
     * @throws Exception en cas d'erreur
     */
    public Sport getSportById(int idSport) throws Exception {
        return this.daoSport.getSportById(idSport);
    }

    /**
     * Méthode permettant de récupérer tous les sports d'un client
     * @param idClient int, l'id du client
     * @return List<Sport>, la liste des sports
     * @throws Exception en cas d'erreur
     */
    public List<Sport> getSportByIdClient(int idClient) throws Exception {
        return this.daoSport.getSportByIdClient(idClient);
    }

}
