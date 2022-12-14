package com.fitj.classes;

import com.fitj.interfaces.Sendable;

import java.util.*;

/**
 * Classe qui représente une notification.
 * @author Paco Munarriz
 */
public class Notification implements Sendable {

    /**
     * Constructeur par défaut
     */
    public Notification() {
    }

    /**
     * Message de la notification.
     */
    private String Message;

    /**
     * @param destinataire : Le client qui reçoit la notification.
     * Envoie la notification au destinataire.
     */
    public void send(Client destinataire) {
        // TODO implement here
    }

}