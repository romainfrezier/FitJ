package com.fitj.controllers.headers;

import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.FacadeNotification;
import javafx.scene.control.Control;

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
        goToPage(controlEl, path + "s/notifications-" +  path + ".fxml", "Notifications & Commandes");
    }
}
