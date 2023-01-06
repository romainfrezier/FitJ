package com.fitj.facades;

import com.fitj.classes.Coach;
import com.fitj.classes.Commande;
import com.fitj.classes.Produit;
import com.fitj.dao.DAOPaiement;
import com.fitj.dao.factory.FactoryDAO;
import com.fitj.enums.PaiementType;

/**
 * Facade utilisée pour les opérations sur les paiements
 * @see Facade
 * @author Paul Merceur
 */
public class FacadePaiement extends Facade {

    /**
     * Instance du DAO
     */
    protected DAOPaiement daoPaiement;

    /**
     * Instance de la Facade, utilisée pour le pattern Singleton
     */
    private static FacadePaiement instance = null;

    /**
     * Constructeur de la FacadePaiement
     */
    protected FacadePaiement(){
        this.daoPaiement = FactoryDAO.getInstance().getDAOPaiement();
    }

    /**
     * Méthode permettant de récupérer l'instance de la FacadePaiement
     * @return FacadePaiement, l'instance de la FacadePaiement
     */
    public static FacadePaiement getInstance(){
        if (instance == null){
            instance = new FacadePaiement();
        }
        return instance;
    }

    /**
     * Méthode permettant de diminuer le solder du Client créditeur
     * @param id int, l'id du Client créditeur
     * @return Coach, le Client créditeur
     * @throws Exception en cas d'erreur
     */
    public Coach retirerArgent(int id) throws Exception {
        return FactoryDAO.getInstance().getDAOClient().resetSoldeCoach(id);
    }

    /**
     *
     * @param id Methode permettant d'acheter un produit
     * @param produit Produit, le produit à acheter
     * @param paiementType PaiementType, le type de paiement
     * @throws Exception
     */
    public void acheterProduit(int id, Produit produit, PaiementType paiementType) throws Exception {
        Commande commande = FactoryDAO.getInstance().getDAOCommande().createCommande(id, produit.getCoach().getId(), produit, paiementType);
        FactoryDAO.getInstance().getDAONotification().createNotification("Vous avez acheté le produit \"" + produit.getNom() +"\"", id, commande.getId());
        FactoryDAO.getInstance().getDAONotification().createNotification("Vous avez vendu le produit \"" + produit.getNom() +"\"", produit.getCoach().getId(), commande.getId());
        this.daoPaiement.createPaiement(commande.getId(), produit.getCoach().getId(), paiementType);
        FactoryDAO.getInstance().getDAOClient().incrementeSoldeCoach(produit.getCoach().getId(), produit.getPrix());
    }
}
