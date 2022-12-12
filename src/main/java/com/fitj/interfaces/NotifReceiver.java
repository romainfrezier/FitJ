package com.fitj.interfaces;

import com.fitj.classes.Notification;

import java.util.*;

/**
 * Interface qui définit une méthode pour recevoir une notification.
 * @see Receiver
 */
public interface NotifReceiver {

    /**
     * Reçoit la notification.
     *
     * @param content Notification, le contenu de la notification
     */
    public void receive(Notification content);

}