package com.fitj.facades;

import com.fitj.classes.Client;
import com.fitj.dao.DAOClient;
import com.fitj.dao.factory.FactoryDAO;

/**
 * Facade utilisée pour les opérations sur les clients
 * @see Facade
 * @author Paul Merceur
 */
public class FacadeClient extends Facade {

    /**
     * Instance du DAO
     */
    protected DAOClient daoClient;

    /**
     * Instance de la Facade, utilisée pour le pattern Singleton
     */
    private static FacadeClient instance = null;

    /**
     * Constructeur de la FacadeClient
     */
    protected FacadeClient(){
        daoClient = FactoryDAO.getInstance().getDAOClient();
    }

    /**
     * Méthode permettant de récupérer l'instance de la FacadeClient
     * @return FacadeClient, l'instance de la FacadeClient
     */
    public static FacadeClient getInstance(){
        if (instance == null){
            instance = new FacadeClient();
        }
        return instance;
    }

    /**
     * Méthode permettant d'ajouter un sport à un client
     * @param idClient int, l'id du client
     * @param idSport int, l'id du sport
     * @throws Exception en cas d'erreur
     */
    public void addSportToClient(int idClient, int idSport) throws Exception {
        this.daoClient.addSportToClient(idClient, idSport);
    }

    /**
     * Méthode permettant de supprimer un sport à un client
     * @param idClient int, l'id du client
     * @param idSport int, l'id du sport
     * @throws Exception en cas d'erreur
     */
    public void deleteSportToClient(int idClient, int idSport) throws Exception {
        this.daoClient.deleteSportToClient(idClient, idSport);
    }

    /**
     * Méthode permettant de supprimer un matériel à un client
     * @param idClient int, l'id du client
     * @param idMateriel int, l'id du matériel
     * @throws Exception en cas d'erreur
     */
    public void deleteMaterielToClient(int idClient, int idMateriel) throws Exception {
        this.daoClient.deleteMaterielToClient(idClient, idMateriel);
    }

    /**
     * Méthode permettant d'ajouter un matériel à un client
     * @param idClient int, l'id du client
     * @param idMateriel int, l'id du matériel
     * @throws Exception en cas d'erreur
     */
    public void addMaterielToClient(int idClient, int idMateriel) throws Exception {
        this.daoClient.addMaterielToClient(idClient, idMateriel);
    }

    /**
     * Méthode permettant de modifier la photo d'un client
     * @param photo String, la photo du client
     * @param idClient int, l'id du client
     * @throws Exception en cas d'erreur
     */
    public void updateClientPhoto(String photo, int idClient) throws Exception {
        this.daoClient.updateClientPhoto(photo, idClient);
    }

    /**
     * Méthode permettant de modifier le pseudo d'un client
     * @param pseudo String, le nom du client
     * @param id int, l'id du client
     * @throws Exception en cas d'erreur
     */
    public void updateClientPseudo(String pseudo, int id) throws Exception{
        this.daoClient.updateClientPseudo(pseudo, id);
    }

    /**
     * Méthode permettant de modifier le mot de passe d'un client
     * @param password String, le mot de passe du client
     * @param id int, l'id du client
     * @throws Exception en cas d'erreur
     */
    public void updateClientPassword(String password, int id) throws Exception{
        this.daoClient.updateClientPassword(password, id);
    }

    /**
     * Méthode permettant de modifier le mail d'un client
     * @param mail String, le mail du client
     * @param id int, l'id du client
     * @throws Exception en cas d'erreur
     */
    public void updateClientMail(String mail, int id) throws Exception{
        this.daoClient.updateClientMail(mail, id);
    }

    /**
     * Méthode permettant de récupérer un client à partir de son id
     * @param idObjectSelected int, l'id du client
     * @return Client, le client
     * @throws Exception en cas d'erreur
     */
    public Client getClientById(int idObjectSelected) throws Exception {
        return this.daoClient.getClientById(idObjectSelected);
    }
}
