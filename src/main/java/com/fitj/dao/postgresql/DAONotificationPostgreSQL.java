package com.fitj.dao.postgresql;

import com.fitj.classes.Notification;
import com.fitj.dao.DAONotification;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.dao.tool.DaoWrapper;
import com.fitj.exceptions.DBProblemException;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe permettant d'interagir avec la base de données PostgreSQL pour les notifications
 * @see DAONotification
 * @author Romain Frezier
 */
public class DAONotificationPostgreSQL extends DAONotification {

    /**
     * Constructeur de la classe DAONotificationPostgreSQL, instancie les méthodes de la base de données PostgreSQL
     */
    public DAONotificationPostgreSQL() {
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    @Override
    public Notification createNotification(String message, int idClient, int idCommande) throws Exception {
        List<Pair<String,Object>> insertList = new ArrayList<>();
        insertList.add( new Pair<>("message", message));
        insertList.add( new Pair<>("idclient", idClient));
        insertList.add( new Pair<>("idcommande", idCommande));
        try {
            int id = ((MethodesPostgreSQL)this.methodesBD).insert(insertList, this.table);
            return this.getNotificationById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBProblemException("Erreur lors de la création de la notification");
        }
    }

    @Override
    public Notification getNotificationById(int id) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add( new Pair<>("id",id));
        try{
            DaoWrapper notificationData = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
            List<Map<String,Object>> result = notificationData.getListeData();
            if (!result.isEmpty()){
                Map<String,Object> data = result.get(0);
                return new Notification(id, (String)data.get("message"), ((Long)data.get("idclient")).intValue(), ((Long)data.get("idcommande")).intValue());
            }
            else {
                throw new DBProblemException("Aucune notification ne correspond à l'id rentré en paramètre");
            }
        } catch (Exception e) {
            throw new DBProblemException("Erreur lors de la récupération de la notification");
        }
    }

    @Override
    public List<Notification> getAllNotifications() throws Exception {
        return this.getAllNotificationsWhere(new ArrayList<>());
    }

    @Override
    public List<Notification> getAllNotificationsWhere(List<Pair<String, Object>> whereList) throws Exception {
        List<Notification> notificationsList = new ArrayList<>();
        try {
            DaoWrapper resultSet = ((MethodesPostgreSQL) this.methodesBD).selectWhere(whereList, this.table);
            List<Map<String, Object>> listData = resultSet.getListeData();
            int i = 0;
            while (i < listData.size()) {
                Map<String, Object> data = listData.get(i);
                notificationsList.add(new Notification(((Long)data.get("id")).intValue(), (String) data.get("message"), ((Long)data.get("idclient")).intValue(), ((Long)data.get("idcommande")).intValue()));
                i++;
            }
            return notificationsList;
        } catch (Exception e) {
            throw new DBProblemException("Erreur lors de la récupération des notifications");
        }
    }

    @Override
    public void supprimerNotification(int id) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add( new Pair<>("id",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList, this.table);
        } catch (Exception e) {
            throw new DBProblemException("Erreur lors de la suppression de la notification");
        }
    }

    @Override
    public Notification modifierNotification(List<Pair<String, Object>> updateList, int id) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add( new Pair<>("id",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).update(updateList, whereList, this.table);
            return this.getNotificationById(id);
        } catch (Exception e) {
            throw new DBProblemException("Erreur lors de la modification de la notification");
        }
    }

    @Override
    public List<Notification> getNotificationsByIdClient(int id) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add( new Pair<>("idclient",id));
        return this.getAllNotificationsWhere(whereList);
    }

    @Override
    public List<Notification> getNotificationsByIdCommande(int id) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add( new Pair<>("idcommande",id));
        return this.getAllNotificationsWhere(whereList);
    }
}