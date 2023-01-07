package com.fitj.facades;

import com.fitj.classes.Admin;
import com.fitj.classes.Client;
import com.fitj.classes.Coach;
import com.fitj.dao.DAOClient;
import com.fitj.dao.factory.FactoryDAO;

import java.util.List;

/**
 * Facade utilisée pour les opérations sur les admins
 * @see Facade
 * @author Paul Merceur
 */
public class FacadeAdmin extends Facade {

    /**
     * Instance du DAO
     */
    protected DAOClient daoClient;

    /**
     * Instance de la Facade, utilisée pour le pattern Singleton
     */
    private static FacadeAdmin instance = null;

    /**
     * Constructeur de la FacadeAdmin
     */
    protected FacadeAdmin(){
        this.daoClient = FactoryDAO.getInstance().getDAOClient();
    }

    /**
     * Méthode permettant de récupérer l'instance de la FacadeAdmin
     * @return FacadeAdmin, l'instance de la FacadeAdmin
     */
    public static FacadeAdmin getInstance(){
        if (instance == null){
            instance = new FacadeAdmin();
        }
        return instance;
    }

    /**
     * Méthode permettant de récupérer tous les clients
     * @return List<Client>, la liste des clients
     * @throws Exception en cas d'erreur
     */
    public List<Client> getAllClients() throws Exception {
        return this.daoClient.getAllClient();
    }

    /**
     * Méthode permettant de transformer un coach en admin
     * @param idObjectSelected int, l'id du coach à transformer
     * @return Admin, l'admin créé
     * @throws Exception en cas d'erreur
     */
    public Admin clientBecomeAdmin(int idObjectSelected) throws Exception {
        return this.daoClient.coachBecomeAdmin(idObjectSelected);
    }

    /**
     * Méthode permettant de transformer un client en coach
     * @param idObjectSelected int, l'id du client à transformer
     * @return Coach, le coach créé
     * @throws Exception en cas d'erreur
     */
    public Coach clientBecomeCoach(int idObjectSelected) throws Exception {
        return this.daoClient.clientBecomeCoach(idObjectSelected);
    }

    /**
     * Méthode permettant de bannir un client
     * @param idObjectSelected int, l'id du client à bannir
     * @return Client, le client banni
     * @throws Exception en cas d'erreur
     */
    public Client banClient(int idObjectSelected) throws Exception {
        Client client = this.daoClient.getClientById(idObjectSelected);
        return this.daoClient.banClient(client);
    }
}
