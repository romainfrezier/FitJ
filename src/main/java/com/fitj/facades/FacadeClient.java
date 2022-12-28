package com.fitj.facades;

import com.fitj.dao.DAOClient;

public class FacadeClient extends Facade {

    protected DAOClient daoClient;

    private static FacadeClient instance = null;
    protected FacadeClient(){

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

    //supprime un sport d'un client
    public void deleteSportToClient(int idClient, int idSport) throws Exception {
        this.daoClient.deleteSportToClient(idClient, idSport);
    }
}
