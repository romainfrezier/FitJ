package com.fitj.facades;

import com.fitj.classes.Notification;
import com.fitj.dao.DAONotification;
import com.fitj.dao.factory.FactoryDAO;

import java.util.List;

/**
 * Facade utilisée pour les opérations sur les notifications
 * @see Facade
 * @author Paul Merceur
 */
public class FacadeNotification extends Facade {

    /**
     * Instance du DAO
     */
    protected DAONotification daoNotification;

    /**
     * Instance de la Facade, utilisée pour le pattern Singleton
     */
    private static FacadeNotification instance = null;

    /**
     * Constructeur de la FacadeNotification
     */
    protected FacadeNotification(){
        this.daoNotification = FactoryDAO.getInstance().getDAONotification();
    }

    /**
     * Méthode permettant de récupérer l'instance de la FacadeNotification
     * @return FacadeNotification, l'instance de la FacadeNotification
     */
    public static FacadeNotification getInstance(){
        if (instance == null){
            instance = new FacadeNotification();
        }
        return instance;
    }

    /**
     * Méthode permettant de récupérer toutes les notifications pour un client
     * @param id int, l'id du client
     * @return List<Notification>, la liste des notifications
     * @throws Exception en cas d'erreur
     */
    public List<Notification> getAllNotificationsByIdClient(int id) throws Exception {
        return this.daoNotification.getNotificationsByIdClient(id);
    }

    /**
     * Méthode permettant de supprimer une notification
     */
    public void deleteNotification(Notification notification) throws Exception {
        this.daoNotification.supprimerNotification(notification.getId());
    }
}
