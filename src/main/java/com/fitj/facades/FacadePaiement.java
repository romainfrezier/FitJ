package com.fitj.facades;

import com.fitj.classes.Coach;
import com.fitj.dao.DAOPaiement;
import com.fitj.dao.factory.FactoryDAO;

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
}
