package com.fitj.controllers.notifications;

import com.fitj.classes.Coach;
import com.fitj.classes.Commande;
import com.fitj.classes.Notification;
import com.fitj.comparators.CommandeComparator;
import com.fitj.comparators.NotificationComparator;
import com.fitj.controllers.commandes.ControllerCommandeDetail;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.List;

public class ControllerNotificationsList extends ControllerNotifications {
    @FXML
    private ListView<Notification> notificationsListView;
    @FXML
    private Button deleteNotification;
    @FXML
    private Text errorTextNotif;
    @FXML
    private ListView<Commande> commandesListView;
    @FXML
    private Button seeMoreButton;
    @FXML
    private Text errorTextCommandes;

    @FXML
    private void initialize() {
        super.hideError(errorTextNotif);
        super.hideError(errorTextCommandes);
        initializeNotifications();
        initializeCommandes();
    }

    private void initializeCommandes() {
        try {
            List<Commande> commandes;
            if (Facade.currentClient instanceof Coach){
                commandes = facadeCommande.getAllCommandesByIdCoach(Facade.currentClient.getId());
            } else {
                commandes = facadeCommande.getAllCommandesByIdClient(Facade.currentClient.getId());
            }
            commandes.sort(new CommandeComparator());
            super.initializeList(commandesListView, commandes, new Callback<ListView<Commande>, ListCell<Commande>>() {
                @Override
                public ListCell<Commande> call(ListView<Commande> param) {
                    return new ListCell<Commande>() {
                        @Override
                        protected void updateItem(Commande item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                if (Facade.currentClient instanceof Coach) {
                                    setText(item.getId() + " - " + item.getProduit().getNom());
                                } else {
                                    setText(item.getId() + " - " + item.getCoach().getPseudo() + " - " + item.getProduit().getNom());
                                }

                            }
                        }
                    };
                }
            });
        } catch (Exception e) {
            super.displayError(errorTextNotif, e.getMessage());
        }
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

    @FXML
    private void handleClickListCommande() {
        setIdObjectSelected(commandesListView.getSelectionModel().getSelectedItem().getId());
    }

    @FXML
    private void handleSeeMoreButton() {
        try {
            checkSelectedCommande();
            goToPage(seeMoreButton, "commandes/detail-commande.fxml", "Détail de la commande");
        } catch (Exception e) {
            e.printStackTrace();
            super.displayError(errorTextCommandes, e.getMessage());
        }
    }

    private void checkSelectedNotification() throws UnselectedItemException {
        if (notificationsListView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Vous devez sélectionner une notification");
        }
    }

    private void checkSelectedCommande() throws UnselectedItemException {
        if (commandesListView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Vous devez sélectionner une commande");
        }
    }
}
