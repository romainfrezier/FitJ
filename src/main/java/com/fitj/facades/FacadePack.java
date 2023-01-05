package com.fitj.facades;

import com.fitj.classes.*;
import com.fitj.dao.DAOPack;
import com.fitj.dao.DAOProgrammeNutrition;
import com.fitj.dao.factory.FactoryDAO;
import com.fitj.enums.ProgrammeType;

import java.util.ArrayList;
import java.util.List;

public class FacadePack extends Facade {

    private static FacadePack instance = null;
    private static DAOPack packDAO;
    public FacadePack(){
        packDAO = FactoryDAO.getInstance().getDAOPack();
    }

    public static FacadePack getInstance(){
        if (instance == null){
            instance = new FacadePack();
        }
        return instance;
    }


    /**
     * @return la liste des packs de la base de données
     * @throws Exception si la requête échoue
     */
    public List<Pack> getListePack() throws Exception{
        return packDAO.getAllPack();
    }

    /**
     * Supprime un pack de la base de données
     * @param idPack l'id du pack à récupérer
     * @throws Exception si la requête échoue
     */

    public void deletePack(int idPack) throws Exception{
        packDAO.deletePack(idPack);
    }

    /**
     * @param idPack l'id du pack à récupérer
     * @return le programme nutrition correspondant à l'id
     * @throws Exception si la requête échoue
     */
    public Pack getPackById(int idPack) throws Exception{
        return packDAO.getPackById(idPack);
    }

    /**
     * @param coach le coach qui a créé les packs
     * @return la liste des packs créées par le coach
     * @throws Exception si la requête échoue
     */
    public List<Pack> getProgrammeNutritionByCoach(Coach coach) throws Exception{
        return packDAO.getAllPackByCoach(coach.getId());
    }


    /**
     * @param idPack l'id du pack à récupérer
     * @param nom le nouveau nom du pack
     * @param description la nouvelle description du pack
     * @param prix le nouveau prix du pack
     * @param type le nouveau type du pack
     * @param nbMois le nouveau nombre de mois du pack
     * @return le pack modifié
     * @throws Exception si la requête échoue
     */
    public Pack updateProgrammeNutrition(int idPack, String nom, String description, double prix, ProgrammeType type, int nbMois) throws Exception{
        return packDAO.updatePack(idPack, nom, description, prix, type, nbMois);
    }


    /**
     * @param nom le nom du pack
     * @param description la description du pack
     * @param prix le prix du pack
     * @param type le type du pack
     * @param nbMois le nombre de mois du pack
     * @param coach le coach qui a créé le pack
     * @param listeRecettes la liste des recettes du pack
     * @return le pack créé
     * @throws Exception si la requête échoue
     */
    public Pack createPack(String nom, String description, double prix, ProgrammeType type, int nbMois, Coach coach, ArrayList<Recette> listeRecettes) throws Exception{
        return packDAO.createPack(nom, description, prix, coach);
    }


    /**
     * Supprime un produit d'un pack
     * @param idPack l'id du pack à récupérer
     * @param produit le produit à supprimer du pack
     * @throws Exception si la requête échoue
     */
    public void removeProduitFromPack(int idPack, Produit produit) throws Exception{
        packDAO.supprimerProduit(produit, idPack);
    }


    /**
     * Ajoute un produit à un pack
     * @param idPack l'id du pack à récupérer
     * @param produit le produit à ajouter au pack
     * @throws Exception si la produit est déjà dans le pack
     */
    public void addProduitToPack(int idPack, Produit produit) throws Exception{
        packDAO.ajouterProduit(produit, idPack);
    }

    /**
     * @param idClient l'id du client à récupérer
     * @return la liste des packs achetés par le client
     * @throws Exception
     */
    public List<Pack> getAllPacksByClient(int idClient) throws Exception{
        return packDAO.getAllPackByClient(idClient);
    }

}
