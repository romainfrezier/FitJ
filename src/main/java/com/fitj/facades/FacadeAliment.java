package com.fitj.facades;

import com.fitj.classes.Aliment;
import com.fitj.classes.Aliment;
import com.fitj.dao.DAOAliment;
import com.fitj.dao.DAOAliment;
import com.fitj.dao.factory.FactoryDAO;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Facade utilisée pour les opérations sur les aliments
 * @see Facade
 * @author Paul Merceur
 */
public class FacadeAliment extends Facade {

    /**
     * Instance du DAO
     */
    protected DAOAliment daoAliment;

    /**
     * Instance de la Facade, utilisée pour le pattern Singleton
     */
    private static FacadeAliment instance = null;

    /**
     * Constructeur de la FacadeAliment
     */
    protected FacadeAliment(){
        this.daoAliment = FactoryDAO.getInstance().getDAOAliment();
    }

    /**
     * Méthode permettant de récupérer l'instance de la FacadeAliment
     * @return FacadeAliment, l'instance de la FacadeAliment
     */
    public static FacadeAliment getInstance(){
        if (instance == null){
            instance = new FacadeAliment();
        }
        return instance;
    }

    /**
     * Méthode permettant de récupérer tous les aliments
     * @return List<Aliment>, la liste des aliments
     * @throws Exception en cas d'erreur
     */
    public List<Aliment> getAllAliments() throws Exception {
        return this.daoAliment.getAllAliments();
    }

    /**
     * Méthode permettant de créer un aliment
     * @param aliment String, le nom de l'aliment
     * @throws Exception en cas d'erreur
     */
    public void createAliment(String aliment) throws Exception {
        this.daoAliment.createAliment(aliment);
    }

    /**
     * Méthode permettant de supprimer un aliment par son id
     * @param id int, l'id de l'aliment
     * @throws Exception en cas d'erreur
     */
    public void deleteAliment(int id) throws Exception {
        this.daoAliment.supprimerAliment(id);
    }

    /**
     * Méthode permettant de modifier un aliment
     * @param id int, l'id de l'aliment
     * @param aliment String, le nom de l'aliment
     * @throws Exception en cas d'erreur
     */
    public void updateAliment(int id, String aliment) throws Exception {
        List<Pair<String, Object>> updateValue = new ArrayList<>();
        updateValue.add(new Pair<>("nom", aliment));
        this.daoAliment.updateAliment(updateValue, id);
    }

    /**
     * Méthode permettant de récupérer un aliment par son id
     * @param idAliment int, l'id de l'aliment
     * @return Aliment, l'aliment
     * @throws Exception en cas d'erreur
     */
    public Aliment getAlimentById(int idAliment) throws Exception {
        return this.daoAliment.getAlimentById(idAliment);
    }
}
