package com.fitj.dao;

import com.fitj.classes.Notification;
import com.fitj.dao.postgresql.DAONotificationPostgreSQL;
import com.fitj.exceptions.DBProblemException;
import kotlin.Pair;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestDAONotificationPostgreSQL {

    private static DAONotificationPostgreSQL daoNotificationPostgreSQL;

    private static Notification notification;

    private static Notification notificationBD;

    @BeforeAll
    public static void init() throws Exception {
        daoNotificationPostgreSQL = new DAONotificationPostgreSQL();
        notification = new Notification(1,"Votre commande est prête",1,1);
        notificationBD = daoNotificationPostgreSQL.createNotification(notification.getMessage(),notification.getIdClient(),notification.getIdCommande());
    }

    @AfterAll
    public static void clean() throws Exception {
        daoNotificationPostgreSQL.supprimerNotification(notificationBD.getId());
    }

    @Test
    void createNotificationTest() {
        Assertions.assertEquals(notification.getMessage(),notificationBD.getMessage());
    }

    @Test
    void getNotificationByIdTest() throws Exception {
        Assertions.assertEquals(notificationBD,daoNotificationPostgreSQL.getNotificationById(notificationBD.getId()));
    }

    @Test
    void getAllNotificationsTest() throws Exception {
        Notification notification2 = new Notification(2,"Votre commande est prête",1,1);
        Notification notificationBD2 = daoNotificationPostgreSQL.createNotification(notification2.getMessage(),notification2.getIdClient(),notification2.getIdCommande());
        int size = daoNotificationPostgreSQL.getAllNotifications().size();
        daoNotificationPostgreSQL.supprimerNotification(notificationBD2.getId());
        Assertions.assertEquals(size,daoNotificationPostgreSQL.getAllNotifications().size()+1);
    }

    @Test
    void getAllNotificationsWhereTest() throws Exception {
        List<Pair<String, Object>> where = new ArrayList<>();
        where.add(new Pair<>("message",String.valueOf(notificationBD.getMessage())));
        Notification notification = daoNotificationPostgreSQL.getAllNotificationsWhere(where).get(0);
        Assertions.assertEquals(notificationBD,notification);
    }

    @Test
    void supprimerNotificationTest() throws Exception {
        Notification notification = new Notification(3,"Votre commande est prête",1,1);
        Notification notificationBD1 = daoNotificationPostgreSQL.createNotification(notification.getMessage(),notification.getIdClient(),notification.getIdCommande());
        daoNotificationPostgreSQL.supprimerNotification(notificationBD1.getId());
        Assertions.assertThrows(DBProblemException.class, () -> daoNotificationPostgreSQL.getNotificationById(notificationBD1.getId()));
    }

    @Test
    void modifierNotificationTest() throws Exception {
        List<Pair<String, Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("message", notification.getMessage() + " suite du message"));
        notificationBD = daoNotificationPostgreSQL.modifierNotification(updateList, notificationBD.getId());
        Assertions.assertEquals(notification.getMessage() + " suite du message", notificationBD.getMessage());
    }

    @Test
    void getNotificationsByUserTest() throws Exception {
        Assertions.assertEquals(1,daoNotificationPostgreSQL.getNotificationsByUser(1).size());
    }

    @Test
    void getNotificationsByCommandeTest() throws Exception {
        Assertions.assertEquals(1,daoNotificationPostgreSQL.getNotificationsByCommande(1).size());
    }
}
