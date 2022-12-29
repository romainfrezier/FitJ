package com.fitj.facades;

import com.fitj.classes.Admin;
import com.fitj.classes.Client;
import com.fitj.classes.Coach;
import com.fitj.dao.DAOClient;
import com.fitj.dao.factory.FactoryDAO;

import java.util.List;

public class FacadeAdmin extends Facade {

    protected DAOClient daoClient;

    private static FacadeAdmin instance = null;
    protected FacadeAdmin(){
        this.daoClient = FactoryDAO.getInstance().getDAOClient();
    }

    public static FacadeAdmin getInstance(){
        if (instance == null){
            instance = new FacadeAdmin();
        }
        return instance;
    }

    public List<Client> getAllClients() throws Exception {
        return this.daoClient.getAllClient();
    }

    public Admin clientBecomeAdmin(int idObjectSelected) throws Exception {
        return this.daoClient.clientBecomeAdmin(idObjectSelected);
    }

    public Coach clientBecomeCoach(int idObjectSelected) throws Exception {
        return this.daoClient.clientBecomeCoach(idObjectSelected);
    }

    public Client banClient(int idObjectSelected) throws Exception {
        Client client = this.daoClient.getClientById(idObjectSelected);
        return this.daoClient.banClient(client);
    }
}
