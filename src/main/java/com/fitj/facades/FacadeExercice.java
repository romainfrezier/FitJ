package com.fitj.facades;

import com.fitj.classes.Exercice;
import com.fitj.dao.DAOExercice;
import com.fitj.dao.factory.FactoryDAO;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe FacadeExercice
 * @author Paul Merceur
 */
public class FacadeExercice extends Facade {

    protected DAOExercice daoExercice;

    private static FacadeExercice instance = null;

    protected FacadeExercice() {
        this.daoExercice = FactoryDAO.getInstance().getDAOExercice();
    }

    public static FacadeExercice getInstance() {
        if (instance == null) {
            instance = new FacadeExercice();
        }
        return instance;
    }

    /**
     * Méthode permettant de récupérer tous les exercices
     *
     * @return List<Exercice>, la liste des exercices
     * @throws Exception en cas d'erreur
     */
    public List<Exercice> getAllExercices() throws Exception {
        return this.daoExercice.getAllExercice();
    }

    /**
     * Méthode permettant de récupérer tous les exercices d'un coach
     *
     * @param idCoach l'id du coach
     * @return List<Exercice>, la liste des exercices
     * @throws Exception en cas d'erreur
     */
    public List<Exercice> getAllExercicesFrom(int idCoach) throws Exception {
        // return this.daoExercice.getAllExerciceFrom(idCoach);
        return null;
    }

    /**
     * Méthode permettant de créer un exercice
     *
     * @param exercice    le nom de l'exercice
     * @param description la description de l'exercice
     * @throws Exception en cas d'erreur
     */
    public void createExercice(String exercice, String description) throws Exception {
        this.daoExercice.createExercice(exercice, description);
    }

    /**
     * Méthode permettant de modifier un exercice
     *
     * @param id          l'id de l'exercice
     * @param exercice    le nom de l'exercice
     * @param description la description de l'exercice
     * @throws Exception en cas d'erreur
     */
    public void updateExercice(int id, String exercice, String description) throws Exception {
        List<Pair<String, Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("nom", exercice));
        updateList.add(new Pair<>("description", description));
        this.daoExercice.updateExercice(updateList, id);
    }

    /**
     * Méthode permettant de supprimer un exercice
     *
     * @param id l'id de l'exercice
     * @throws Exception en cas d'erreur
     */
    public void deleteExercice(int id) throws Exception {
        this.daoExercice.supprimerExercice(id);
    }

    /**
     * Méthode permettant de récupérer un exercice
     *
     * @param id l'id de l'exercice
     * @return Exercice, l'exercice
     * @throws Exception en cas d'erreur
     */
    public Exercice getExerciceById(int id) throws Exception {
        return this.daoExercice.getExerciceById(id);
    }

}
