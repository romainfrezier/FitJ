package com.fitj.facades;

import com.fitj.classes.Admin;
import com.fitj.classes.Client;
import com.fitj.classes.Coach;
import com.fitj.dao.DAOClient;
import com.fitj.dao.factory.FactoryDAO;

import java.util.List;

public class FacadeCoach extends Facade {

    protected DAOClient daoClient;
    private static FacadeCoach instance = null;
    protected FacadeCoach(){
        this.daoClient = FactoryDAO.getInstance().getDAOClient();
    }

    public static FacadeCoach getInstance(){
        if (instance == null){
            instance = new FacadeCoach();
        }
        return instance;
    }

    public List<Coach> getAllCoachs() throws Exception {
        return this.daoClient.getAllCoach();
    }

    public Admin clientBecomeAdmin(int idObjectSelected) throws Exception {
        return this.daoClient.clientBecomeAdmin(idObjectSelected);
    }

    public List<Client> getAllClientsForACoach(int coachId) throws Exception {
        return this.daoClient.getAllClientForACoach(coachId);
    }
}
