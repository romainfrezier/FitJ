package com.fitj.dao;

import com.fitj.classes.*;
import com.fitj.dao.postgresql.*;
import com.fitj.enums.PaiementType;
import com.fitj.exceptions.DBProblemException;
import kotlin.Pair;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de test de la classe DAONotificationPostgreSQL
 * @see DAONotificationPostgreSQL
 * @author Romain Frezier
 */
public class DAONotificationPostgreSQLTest {

    /**
     * Objet utilisé pour les tests
     */
    private static DAONotificationPostgreSQL daoNotificationPostgreSQL;

    /**
     * Objet utilisé pour les tests
     */
    private static Notification notification;

    /**
     * Objet utilisé pour les tests
     */
    private static Notification notificationBD;

    /**
     * Objet utilisé pour les tests
     */
    private static int idClient;

    /**
     * Objet utilisé pour les tests
     */
    private static Commande commande;

    /**
     * Objet utilisé pour les tests
     */
    private static Client client;

    /**
     * Objet utilisé pour les tests
     */
    private static Coach coach;

    /**
     * Objet utilisé pour les tests
     */
    private static Seance seance;

    /**
     * Objet utilisé pour les tests
     */
    private static Sport sport;

    /**
     * Méthode d'initialisation réalisée avant que tous les tests soient effectués
     * @throws Exception si la requête SQL échoue
     */
    @BeforeAll
    public static void init() throws Exception {
        daoNotificationPostgreSQL = new DAONotificationPostgreSQL();
        client = new DAOClientPostgreSQL().getAllClient().get(0);
        coach = new DAOClientPostgreSQL().getAllCoach().get(0);
        sport = new DAOSportPostgreSQL().createSport("Test");
        seance = new DAOSeancePostgreSQL().createSeance("seance", "description", 1, coach, sport, new ArrayList<>());
        commande = new DAOCommandePostgreSQL().createCommande(client.getId(), coach.getId(), seance, PaiementType.CARTE_BANCAIRE);
        notification = new Notification(1,"Votre commande est prête",commande.getClient().getId(), commande.getId());
        idClient = notification.getIdClient();
        notificationBD = daoNotificationPostgreSQL.createNotification(notification.getMessage(), idClient, commande.getId());
    }

    /**
     * Méthode effectuée après tous les tests pour supprimer la notification créée
     * @throws Exception si la requête SQL échoue
     */
    @AfterAll
    public static void clean() throws Exception {
        daoNotificationPostgreSQL.supprimerNotification(notificationBD.getId());
        new DAOCommandePostgreSQL().deleteCommande(commande.getId());
        new DAOSeancePostgreSQL().supprimerSeance(seance.getId());
        new DAOSportPostgreSQL().supprimerSport(sport.getId());
    }

    /**
     * Test de la méthode createNotification
     */
    @Test
    void createNotificationTest() {
        Assertions.assertEquals(notification.getMessage(),notificationBD.getMessage());
    }

    /**
     * Test de la méthode getNotificationById
     * @throws Exception si la requête SQL échoue
     */
    @Test
    void getNotificationByIdTest() throws Exception {
        Assertions.assertEquals(notificationBD.getId(),daoNotificationPostgreSQL.getNotificationById(notificationBD.getId()).getId());
    }

    /**
     * Test de la méthode getAllNotifications
     * @throws Exception si la requête SQL échoue
     */
    @Test
    void getAllNotificationsTest() throws Exception {
        Notification notificationBD2 = daoNotificationPostgreSQL.createNotification("Votre commande est prête", idClient, commande.getId());
        int size = daoNotificationPostgreSQL.getAllNotifications().size();
        daoNotificationPostgreSQL.supprimerNotification(notificationBD2.getId());
        Assertions.assertEquals(size,daoNotificationPostgreSQL.getAllNotifications().size()+1);
    }

    /**
     * Test de la méthode getNotificationsWhere
     * @throws Exception si la requête SQL échoue
     */
    @Test
    void getAllNotificationsWhereTest() throws Exception {
        List<Pair<String, Object>> where = new ArrayList<>();
        where.add(new Pair<>("message",String.valueOf(notificationBD.getMessage())));
        Notification notification = daoNotificationPostgreSQL.getAllNotificationsWhere(where).get(0);
        Assertions.assertEquals(notificationBD.getId(),notification.getId());
    }

    /**
     * Test de la méthode supprimerNotification
     * @throws Exception si la requête SQL échoue
     */
    @Test
    void supprimerNotificationTest() throws Exception {
        Notification notificationBD1 = daoNotificationPostgreSQL.createNotification("Votre commande est prête", idClient, commande.getId());
        daoNotificationPostgreSQL.supprimerNotification(notificationBD1.getId());
        Assertions.assertThrows(DBProblemException.class, () -> daoNotificationPostgreSQL.getNotificationById(notificationBD1.getId()));
    }

    /**
     * Test de la méthode modifierNotification
     * @throws Exception si la requête SQL échoue
     */
    @Test
    void modifierNotificationTest() throws Exception {
        List<Pair<String, Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("message", notification.getMessage() + " suite du message"));
        notificationBD = daoNotificationPostgreSQL.modifierNotification(updateList, notificationBD.getId());
        Assertions.assertEquals(notification.getMessage() + " suite du message", notificationBD.getMessage());
    }

    /**
     * Test de la méthode getNotificationsByIdClient
     * @throws Exception si la requête SQL échoue
     */
    @Test
    void getNotificationsByIdClientTest() throws Exception {
        Assertions.assertEquals(idClient,daoNotificationPostgreSQL.getNotificationsByIdClient(idClient).get(0).getIdClient());
    }

    /**
     * Test de la méthode getNotificationsByIdCommande
     * @throws Exception si la requête SQL échoue
     */
    @Test
    void getNotificationsByIdCommandeTest() throws Exception {
        Assertions.assertEquals(commande.getId(),daoNotificationPostgreSQL.getNotificationsByIdCommande(commande.getId()).get(0).getIdCommande());
    }
}
