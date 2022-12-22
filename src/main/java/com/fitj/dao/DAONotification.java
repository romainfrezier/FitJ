package com.fitj.dao;

import com.fitj.classes.Notification;
import kotlin.Pair;

import java.util.List;

/**
 * Classe parente de tous les DAO notification qui permettent d'interagir avec tout type de base de données
 * @see DAO
 * @author Romain Frezier
 */
public abstract class DAONotification extends DAO {

    /**
     * Constructeur de la classe DAONotification, définit le nom de la table
     */
    public DAONotification() {
        super("notification");
    }

    /**
     * @param message String, le message de la notification
     * @param idClient int, l'id du client concerné par la notification
     * @param idCommande int, l'id de la commande concernée par la notification
     * @return Notification, la notification créée
     * @throws Exception si une erreur SQL survient
     */
    public abstract Notification createNotification(String message, int idClient, int idCommande) throws Exception;

    /**
     * @param id int, l'id de la notification
     * @return Notification, la notification dans la base de donnée contenant l'id rentré en paramètre
     * @throws Exception si une erreur SQL survient
     */
    public abstract Notification getNotificationById(int id) throws Exception;

    /**
     * @return List<Notification>, toutes les notifications présentes dans la base de donnée dans une List
     * @throws Exception si une erreur SQL survient
     */
    public abstract List<Notification> getAllNotifications() throws Exception;

    /**
     * @return List<Notification>, toutes les notifications présentes dans la base de donnée dans une List pour liste de conditions
     * @throws Exception si une erreur SQL survient
     */
    public abstract List<Notification> getAllNotificationsWhere(List<Pair<String,Object>> whereList) throws Exception;

    /**
     * @param id int, l'id de la notification à supprimer
     * @throws Exception si une erreur SQL survient
     */
    public abstract void supprimerNotification(int id) throws Exception;

    /**
     * @param updateList List<Pair<String,Object>>, la liste des champs à mettre à jour pour la requête
     * @param id         int, l'id de la notification à modifier
     * @return Notification, la notification modifiée
     * @throws Exception si une erreur SQL survient
     */
    public abstract Notification modifierNotification(List<Pair<String, Object>> updateList, int id) throws Exception;

    /**
     * @param id int, l'id de l'utilisateur à qui appartient la notification
     * @return List<Notification>, toutes les notifications relatives à un utilisateur dans la base de donnée dans une List
     * @throws Exception si une erreur SQL survient
     */
    public abstract List<Notification> getNotificationsByUser(int id) throws Exception;

    /**
     * @param id int, l'id de l'utilisateur à qui appartient la notification
     * @return List<Notification>, toutes les notifications relatives à une commande dans la base de donnée dans une List
     * @throws Exception si une erreur SQL survient
     */
    public abstract List<Notification> getNotificationsByCommande(int id) throws Exception;
}
