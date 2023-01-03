package com.fitj.facades;

import com.fitj.classes.*;
import com.fitj.dao.DAOCommande;
import com.fitj.dao.factory.FactoryDAO;

import java.util.List;

/**
 * FacadeCommande, classe qui permet de faire le lien entre la DAO et le Controller
 * @see Facade
 * @author Romain Frezier
 */
public class FacadeCommande extends Facade {

    protected DAOCommande daoCommande;
    private static FacadeCommande instance = null;
    protected FacadeCommande(){
        this.daoCommande = FactoryDAO.getInstance().getDAOCommande();
    }

    public static FacadeCommande getInstance(){
        if (instance == null){
            instance = new FacadeCommande();
        }
        return instance;
    }

    public List<Commande> getAllCommandesByIdClient(int currentClient) throws Exception {
        return this.daoCommande.getCommandeByIdClient(currentClient);
    }

    public List<Commande> getAllCommandesByIdCoach(int coachId) throws Exception {
        return this.daoCommande.getCommandeByIdCoach(coachId);
    }

    public Commande getCommandeById(int idObjectSelected) throws Exception {
        return this.daoCommande.getCommandeById(idObjectSelected);
    }

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
