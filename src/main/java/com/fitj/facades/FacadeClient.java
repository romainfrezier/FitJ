package com.fitj.facades;

import com.fitj.dao.DAOClient;
import com.fitj.dao.factory.FactoryDAO;

public class FacadeClient extends Facade {

    protected DAOClient daoClient;

    private static FacadeClient instance = null;
    protected FacadeClient(){
        daoClient = FactoryDAO.getInstance().getDAOClient();
    }

    public static FacadeClient getInstance(){
        if (instance == null){
            instance = new FacadeClient();
        }
        return instance;
    }

    //ajoute un sport à un client
    public void addSportToClient(int idClient, int idSport) throws Exception {
        this.daoClient.addSportToClient(idClient, idSport);
    }

    public void deleteSportToClient(int idClient, int idSport) throws Exception {
        this.daoClient.deleteSportToClient(idClient, idSport);
    }

    public void deleteMaterielToClient(int idClient, int idMateriel) throws Exception {
        this.daoClient.deleteMaterielToClient(idClient, idMateriel);
    }

    public void addMaterielToClient(int idClient, int idMateriel) throws Exception {
        this.daoClient.addMaterielToClient(idClient, idMateriel);
    }



}
