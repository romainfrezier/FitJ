package com.fitj.controllers.notifications;

import com.fitj.controllers.Controller;
import com.fitj.facades.FacadeCommande;
import com.fitj.facades.FacadeNotification;

public abstract class ControllerNotifications extends Controller {

    FacadeNotification facadeNotification = FacadeNotification.getInstance();
    FacadeCommande facadeCommande = FacadeCommande.getInstance();

}
