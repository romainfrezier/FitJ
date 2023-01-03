package com.fitj.facades;

import com.fitj.classes.Notification;
import com.fitj.dao.DAONotification;
import com.fitj.dao.factory.FactoryDAO;

import java.util.List;

public class FacadeNotification extends Facade {

    protected DAONotification daoNotification;
    private static FacadeNotification instance = null;
    protected FacadeNotification(){
        this.daoNotification = FactoryDAO.getInstance().getDAONotification();
    }

    public static FacadeNotification getInstance(){
        if (instance == null){
            instance = new FacadeNotification();
        }
        return instance;
    }

    public List<Notification> getAllNotificationsByIdClient(int id) throws Exception {
        return this.daoNotification.getNotificationsByIdClient(id);
    }

    public void deleteNotification(Notification notification) throws Exception {
        this.daoNotification.supprimerNotification(notification.getId());
    }
}
