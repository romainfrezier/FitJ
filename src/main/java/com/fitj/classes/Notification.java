package com.fitj.classes;

import com.fitj.interfaces.Sendable;

import java.util.*;

/**
 * Classe qui représente une notification.
 */
public class Notification implements Sendable {

    /**
     * Default constructor
     */
    public Notification() {
    }

    /**
     * Message de la notification.
     */
    private String Message;

    /**
     * @param destinataire
     * Envoie la notification au destinataire.
     */
    public void send(Client destinataire) {
        // TODO implement here
    }

}