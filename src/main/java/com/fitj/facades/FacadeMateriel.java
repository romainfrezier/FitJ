package com.fitj.facades;

import com.fitj.classes.Materiel;
import com.fitj.dao.DAOMateriel;
import com.fitj.dao.factory.FactoryDAO;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Facade utilisée pour les opérations sur les matériel
 * @see Facade
 * @author Paul Merceur
 */
public class FacadeMateriel extends Facade {

    /**
     * Instance du DAO
     */
    protected DAOMateriel daoMateriel;

    /**
     * Instance de la Facade, utilisée pour le pattern Singleton
     */
    private static FacadeMateriel instance = null;

    /**
     * Constructeur de la FacadeMateriel
     */
    protected FacadeMateriel(){
        this.daoMateriel = FactoryDAO.getInstance().getDAOMateriel();
    }

    /**
     * Méthode permettant de récupérer l'instance de la FacadeMateriel
     * @return FacadeMateriel, l'instance de la FacadeMateriel
     */
    public static FacadeMateriel getInstance(){
        if (instance == null){
            instance = new FacadeMateriel();
        }
        return instance;
    }

    /**
     * Méthode permettant de récupérer tous les matériels
     * @return List<Materiel>, la liste des matériels
     * @throws Exception en cas d'erreur
     */
    public List<Materiel> getAllMateriels() throws Exception {
        return this.daoMateriel.getAllMateriel();
    }

    /**
     * Méthode permettant de créer un matériel
     * @param materiel String, le nom du matériel
     * @throws Exception en cas d'erreur
     */
    public void createMateriel(String materiel) throws Exception {
        this.daoMateriel.createMateriel(materiel);
    }

    /**
     * Méthode permettant de supprimer un matériel
     * @param id int, l'id du matériel
     * @throws Exception en cas d'erreur
     */
    public void deleteMateriel(int id) throws Exception {
        this.daoMateriel.supprimerMateriel(id);
    }

    /**
     * Méthode permettant de modifier un matériel
     * @param id int, l'id du matériel
     * @param materiel String, le nom du matériel
     * @throws Exception en cas d'erreur
     */
    public void updateMateriel(int id, String materiel) throws Exception {
        List<Pair<String, Object>> updateValue = new ArrayList<>();
        updateValue.add(new Pair<>("nom", materiel));
        this.daoMateriel.updateMateriel(updateValue, id);
    }

    /**
     * Méthode permettant de récupérer un matériel par son id
     * @param idMateriel int, l'id du matériel
     * @return Materiel, le matériel
     * @throws Exception en cas d'erreur
     */
    public Materiel getMaterielById(int idMateriel) throws Exception {
        return this.daoMateriel.getMaterielById(idMateriel);
    }

    /**
     * Méthode permettant de récupérer la liste des matériels d'un client
     * @param idClient int, l'id du client
     * @return List<Materiel>, la liste des matériels
     * @throws Exception en cas d'erreur
     */
    public List<Materiel> getMaterielByIdClient(int idClient) throws Exception {
        return this.daoMateriel.getMaterielByIdClient(idClient);
    }
}

