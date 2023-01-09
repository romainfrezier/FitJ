package com.fitj.controllers.notifications;

import com.fitj.controllers.Controller;
import com.fitj.facades.FacadeCommande;
import com.fitj.facades.FacadeNotification;

/**
 * Controller de la page notification
 * @see Controller
 * @author Romain Frezier, Paco Munarriz
 */
public abstract class ControllerNotifications extends Controller {

    /**
     * Instance de FacadeNotification
     */
    FacadeNotification facadeNotification = FacadeNotification.getInstance();
    /**
     * Instance de FacadeCommande
     */
    FacadeCommande facadeCommande = FacadeCommande.getInstance();

}
