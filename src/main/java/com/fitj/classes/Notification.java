package com.fitj.classes;

import com.fitj.interfaces.Sendable;

import java.util.*;

/**
 * Classe qui représente une notification.
 * @author Paco Munarriz
 */
public class Notification implements Sendable {

    /**
     * Identifiant de la notification
     */
    private int id;

    /**
     * Message de la notification
     */
    private String message;

    /**
     * Id du client concerné par la notification
     */
    private int idClient;

    /**
     * Id de la commande concernée par la notification
     */
    private int idCommande;

    public Notification(int id, String message, int idClient, int idCommande) {
        this.id = id;
        this.message = message;
        this.idClient = idClient;
        this.idCommande = idCommande;
    }

    /**
     * @param destinataire : Le client qui reçoit la notification.
     * Envoie la notification au destinataire.
     */
    public void send(Client destinataire) {
        // TODO implement here
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }
}