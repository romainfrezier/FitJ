package com.fitj.controllers.headers;

import com.fitj.classes.Admin;
import com.fitj.classes.Coach;
import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.Facade;
import com.fitj.facades.FacadeNotification;
import javafx.scene.control.Control;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * Controller générique pour les headers
 * @see Controller
 * @author Romain Frezier
 */
public abstract class ControllerHeader extends Controller {

    /**
     * Facade pour les notifications
     */
    FacadeNotification facadeNotification = FacadeNotification.getInstance();

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages accessibles aux clients avec un certain role
     */
    private String path;

    /**
     * Getter pour le chemin du dossier dans lequel se trouve les ressources pour les pages accessibles aux clients avec un certain role
     * @return Chemin du dossier dans lequel se trouve les ressources pour les pages accessibles aux clients avec un certain role
     */
    public String getPath() {
        return path;
    }

    /**
     * Setter pour le chemin du dossier dans lequel se trouve les ressources pour les pages accessibles aux clients avec un certain role
     * @param path Chemin du dossier dans lequel se trouve les ressources pour les pages accessibles aux clients avec un certain role
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Methode permettant de se rendre sur la page mon compte
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToMonCompte(Control controlEl) throws BadPageException {
        goToPage(controlEl, path + "s/monCompte-" + path + ".fxml", "Mon Compte");
    }

    /**
     * Methode permettant de se rendre sur la page coachs
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToCoachs(Control controlEl) throws BadPageException {
        goToPage(controlEl, path + "s/coachs-" + path + ".fxml", "Coachs");
    }

    /**
     * Methode permettant de se rendre sur la page mon espace
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToMonEspace(Control controlEl) throws BadPageException {
        goToPage(controlEl, path + "s/monEspace-" + path + ".fxml", "Mon Espace");
    }

    /**
     * Methode permettant de se rendre sur la page shop
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToShop(Control controlEl) throws BadPageException {
        goToPage(controlEl, "shop/shop.fxml", "Shop");
    }

    /**
     * Methode permettant de se rendre sur la page des notifications et des commandes
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToNotification(Control controlEl) throws BadPageException {
        goToPage(controlEl, "notifications/notifications-commandes-list.fxml", "Notifications & Commandes");
    }

    /**
     * Méthode pour récupérer les notifications et les commandes
     * @param notifIcon ImageView, icône de notification
     * @param newNotifIcon ImageView, icône de nouvelle notification
     * @param errorText Text, texte d'erreur
     */
    protected void getNotifIcon(ImageView notifIcon, ImageView newNotifIcon, Text errorText) {
        if (Facade.currentClient != null) {
            super.hideError(errorText);
            this.setPath(path);
            try {
                if (facadeNotification.getAllNotificationsByIdClient(Facade.currentClient.getId()).size() > 0) {
                    newNotifIcon.setVisible(true);
                    notifIcon.setVisible(false);
                } else {
                    newNotifIcon.setVisible(false);
                    notifIcon.setVisible(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                super.displayError(errorText, "Impossible de récupérer les notifications");
            }
        }
    }


    /**
     * Méthode pour récupérer le chemin correspondant au bon role de l'utilisateur
     * @return String, chemin
     */
    private String getCurrentPath() {
        if (Facade.currentClient instanceof Admin) {
            return "admin";
        } else if (Facade.currentClient instanceof Coach) {
            return "coach";
        } else {
            return "client";
        }
    }
}

}
