package com.fitj.facades;

import com.fitj.classes.*;
import com.fitj.dao.DAOCommande;
import com.fitj.dao.factory.FactoryDAO;

import java.util.List;

/**
 * FacadeCommande, classe qui permet de faire le lien entre la DAO et le Controller
 * @see Facade
 * @author Paul Merceur
 */
public class FacadeCommande extends Facade {

    /**
     * Instance du DAO
     */
    protected DAOCommande daoCommande;

    /**
     * Instance de la Facade, utilisée pour le pattern Singleton
     */
    private static FacadeCommande instance = null;

    /**
     * Constructeur de la FacadeCommande
     */
    protected FacadeCommande(){
        this.daoCommande = FactoryDAO.getInstance().getDAOCommande();
    }

    /**
     * Méthode permettant de récupérer l'instance de la FacadeCommande
     * @return FacadeCommande, l'instance de la FacadeCommande
     */
    public static FacadeCommande getInstance(){
        if (instance == null){
            instance = new FacadeCommande();
        }
        return instance;
    }

    /**
     * Méthode permettant de récupérer toutes les commandes d'un client
     * @param currentClient int, l'id du client
     * @return List<Commande>, la liste des commandes du client
     * @throws Exception en cas d'erreur
     */
    public List<Commande> getAllCommandesByIdClient(int currentClient) throws Exception {
        return this.daoCommande.getCommandeByIdClient(currentClient);
    }

    /**
     * Méthode permettant de récupérer toutes les commandes d'un coach
     * @param coachId int, l'id du coach
     * @return List<Commande>, la liste des commandes du coach
     * @throws Exception en cas d'erreur
     */
    public List<Commande> getAllCommandesByIdCoach(int coachId) throws Exception {
        return this.daoCommande.getCommandeByIdCoach(coachId);
    }

    /**
     * Méthode permettant de récupérer une commande par son id
     * @param idObjectSelected int, l'id de la commande
     * @return Commande, la commande
     * @throws Exception en cas d'erreur
     */
    public Commande getCommandeById(int idObjectSelected) throws Exception {
        return this.daoCommande.getCommandeById(idObjectSelected);
    }

    /**
     * Méthode permettant de récupérer l'id d'un produit
     * @param produit String, le nom du produit
     * @return Produit, l'id du produit
     * @throws Exception
     */
    public Produit getProduitById(Produit produit) throws Exception {
        if (produit instanceof Pack) {
            return FactoryDAO.getInstance().getDAOPack().getPackById(produit.getId());
        } else if (produit instanceof ProgrammePersonnalise) {
            return FactoryDAO.getInstance().getDAOProgrammePersonnalise().getProgrammePersonnaliseById(produit.getId());
        } else if (produit instanceof ProgrammeSportif) {
            return FactoryDAO.getInstance().getDAOProgrammeSportif().getProgrammeSportifById(produit.getId());
        } else if (produit instanceof Seance) {
            return FactoryDAO.getInstance().getDAOSeance().getSeanceById(produit.getId());
        } else if (produit instanceof ProgrammeNutrition) {
            return FactoryDAO.getInstance().getDAOProgrammeNutrition().getProgrammeNutritionById(produit.getId());
        } else {
            return null;
        }
    }
}
