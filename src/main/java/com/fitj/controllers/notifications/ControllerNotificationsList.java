package com.fitj.controllers.notifications;

import com.fitj.classes.Notification;
import com.fitj.comparators.NotificationComparator;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.List;

/**
 * Controller de la page notification-list-view.fxml
 * @see ControllerNotifications
 * @author Romain Frezier
 */
public class ControllerNotificationsList extends ControllerNotifications {
    @FXML
    private ListView<Notification> notificationsListView;
    @FXML
    private Button deleteNotification;
    @FXML
    private Text errorTextNotif;

    @FXML
    private void initialize() {
        super.hideError(errorTextNotif);
        initializeNotifications();
    }

    private void initializeNotifications() {
        try {
            List<Notification> notifications = facadeNotification.getAllNotificationsByIdClient(Facade.currentClient.getId());
            notifications.sort(new NotificationComparator());
            super.initializeList(notificationsListView, notifications, new Callback<ListView<Notification>, ListCell<Notification>>() {
                @Override
                public ListCell<Notification> call(ListView<Notification> param) {
                    return new ListCell<Notification>() {
                        @Override
                        protected void updateItem(Notification item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                setText(item.getMessage());
                            }
                        }
                    };
                }
            });
        } catch (Exception e) {
            super.displayError(errorTextNotif, e.getMessage());
        }
    }

    @FXML
    private void deleteNotification() {
        try {
            checkSelectedNotification();
            Notification notification = notificationsListView.getSelectionModel().getSelectedItem();
            facadeNotification.deleteNotification(notification);
            notificationsListView.getItems().remove(notification);
        } catch (Exception e) {
            super.displayError(errorTextNotif, e.getMessage());
        }
    }

    private void checkSelectedNotification() throws UnselectedItemException {
        if (notificationsListView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Vous devez sélectionner une notification");
        }
    }
}
