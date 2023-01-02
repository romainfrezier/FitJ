package com.fitj.facades;

import com.fitj.classes.Coach;
import com.fitj.classes.Exercice;
import com.fitj.classes.Seance;
import com.fitj.classes.Sport;
import com.fitj.dao.DAOSeance;
import com.fitj.dao.factory.FactoryDAO;
import kotlin.Pair;
import kotlin.Triple;

import java.util.ArrayList;
import java.util.List;

public class FacadeSeance extends Facade {

    private static FacadeSeance instance = null;
    protected DAOSeance daoSeance;

    protected FacadeSeance(){
        daoSeance = FactoryDAO.getInstance().getDAOSeance();
    }

    public static FacadeSeance getInstance(){
        if (instance == null){
            instance = new FacadeSeance();
        }
        return instance;
    }

    /**
     * Méthode permettant de récupérer toutes les séances
     * @return List<Seance>, la liste des séances
     * @throws Exception en cas d'erreur
     */
    public List<Seance> getAllSeances() throws Exception {
        return this.daoSeance.getAllSeances();
    }

    /**
     * Méthode permettant de récupérer toutes les séances d'un client
     * @param idClient l'id du client
     * @return List<Seance>, la liste des séances
     * @throws Exception en cas d'erreur
     */
    public List<Seance> getAllSeancesFromClient(int idClient) throws Exception {
        return this.daoSeance.getAllSeancesFromClient(idClient);
    }

    /**
     * Méthode permettant de récupérer toutes les séances d'un coach
     * @param idCoach l'id du coach
     * @return List<Seance>, la liste des séances
     * @throws Exception en cas d'erreur
     */
    public List<Seance> getAllSeancesFromCoach(int idCoach) throws Exception {
        return this.daoSeance.getAllSeancesFromCoach(idCoach);
    }

    /**
     * Méthode permettant de récupérer une séance
     * @param idSeance l'id de la séance
     * @return Seance, la séance
     * @throws Exception en cas d'erreur
     */
    public Seance getSeance(int idSeance) throws Exception {
        return this.daoSeance.getSeanceById(idSeance);
    }

    /**
     * Méthode permettant de créer une séance
     * @param nom String, le nom de la séance
     * @param description String, la description de la séance
     * @param prix double, le prix de la séance
     * @param coach Coach, le coach de la séance
     * @param sport Sport, le sport de la séance
     * @param exercices List<Triple<Exercice, Integer, Integer>>, la liste des exercices de la séance avec leur nombre de séries et de répétitions
     * @throws Exception en cas d'erreur
     */
    public void createSeance(String nom, String description, double prix, Coach coach, Sport sport, List<Triple<Exercice, Integer, Integer>> exercices) throws Exception {
        this.daoSeance.createSeance(nom, description, prix, coach, sport, exercices);
    }

    /**
     * Méthode permettant de modifier une séance
     * @param id int, l'id de la séance
     * @param nom String, le nom de la séance
     * @param description String, la description de la séance
     * @param prix double, le prix de la séance
     * @param coach Coach, le coach de la séance
     * @param sport Sport, le sport de la séance
     * @throws Exception en cas d'erreur
     */
    public void updateSeance(int id, String nom, String description, double prix, Coach coach, Sport sport) throws Exception {
        List<Pair<String, Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("nom", nom));
        updateList.add(new Pair<>("description", description));
        updateList.add(new Pair<>("prix", prix));
        updateList.add(new Pair<>("idcoach", coach.getId()));
        updateList.add(new Pair<>("idsport", sport.getId()));
        this.daoSeance.updateSeance(updateList, id);
    }

    /**
     * Méthode permettant de supprimer une séance
     * @param id int, l'id de la séance
     * @throws Exception en cas d'erreur
     */
    public void deleteSeance(int id) throws Exception {
        this.daoSeance.supprimerSeance(id);
    }

    /**
     * Méthode permettant d'ajouter un exercice à une séance
     * @param exercice Exercice, exercice à ajouter
     * @param nbrepetition int, le nombre de répétition de l'exercice
     * @param nbserie int, le nombre de série de l'exercice
     * @param id int, l'id de la séance
     */
    public void addExerciceToSeance(Exercice exercice, int nbrepetition, int nbserie, int id) throws Exception {
        this.daoSeance.ajouterExercice(exercice, nbrepetition, nbserie, id);
    }

    /**
     * Méthode permettant de supprimer un exercice d'une séance
     * @param exercice Exercice, exercice à supprimer
     * @param id int, l'id de la séance
     */
    public void deleteExerciceFromSeance(Exercice exercice, int id) throws Exception {
        this.daoSeance.supprimerExercice(exercice, id);
    }

    /**
     * Méthode permettant de récupérer les exercices d'une séance
     * @param id int, l'id de la séance
     * @return List<Triple<Exercice, Integer, Integer>>, la liste des exercices de la séance avec leur nombre de séries et de répétitions
     * @throws Exception en cas d'erreur
     */
    public List<Triple<Exercice, Integer, Integer>> getExercices(int id) throws Exception {
        return this.daoSeance.getExercices(id);
    }
}
