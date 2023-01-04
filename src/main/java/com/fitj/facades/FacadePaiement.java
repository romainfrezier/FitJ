package com.fitj.facades;

import com.fitj.classes.Coach;
import com.fitj.dao.DAOPaiement;
import com.fitj.dao.factory.FactoryDAO;

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
}
