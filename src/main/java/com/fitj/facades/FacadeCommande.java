package com.fitj.facades;

import com.fitj.classes.Commande;
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
}
