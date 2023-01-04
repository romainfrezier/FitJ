package com.fitj.facades;

import com.fitj.classes.Coach;
import com.fitj.classes.Commande;
import com.fitj.classes.Produit;
import com.fitj.dao.DAOPaiement;
import com.fitj.dao.factory.FactoryDAO;
import com.fitj.enums.PaiementType;

public class FacadePaiement extends Facade {

    protected DAOPaiement daoPaiement;
    private static FacadePaiement instance = null;
    protected FacadePaiement(){
        this.daoPaiement = FactoryDAO.getInstance().getDAOPaiement();
    }

    public static FacadePaiement getInstance(){
        if (instance == null){
            instance = new FacadePaiement();
        }
        return instance;
    }

    public Coach retirerArgent(int id) throws Exception {
        return FactoryDAO.getInstance().getDAOClient().resetSoldeCoach(id);
    }

    public void acheterProduit(int id, Produit produit, PaiementType paiementType) throws Exception {
        Commande commande = FactoryDAO.getInstance().getDAOCommande().createCommande(id, produit.getCoach().getId(), produit, paiementType);
        FactoryDAO.getInstance().getDAONotification().createNotification("Vous avez acheté le produit \"" + produit.getNom() +"\"", id, commande.getId());
        FactoryDAO.getInstance().getDAONotification().createNotification("Vous avez vendu le produit \"" + produit.getNom() +"\"", produit.getCoach().getId(), commande.getId());
        this.daoPaiement.createPaiement(commande.getId(), produit.getCoach().getId(), paiementType);
        FactoryDAO.getInstance().getDAOClient().incrementeSoldeCoach(produit.getCoach().getId(), produit.getPrix());
    }
}
