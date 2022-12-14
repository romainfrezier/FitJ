package com.fitj.classes;

import com.fitj.interfaces.DemandReceiver;
import com.fitj.interfaces.PaymentReceiver;

import java.util.*;

/**
 * Une classe qui représente un coach.
 * Un coach est un utilisateur qui peut proposer des produits.
 * @author Paco Munarriz
 */
public class Coach extends Client implements PaymentReceiver, DemandReceiver {

    /**
     * Constructeur par défaut
     */
    public Coach() {
    }

    /**
     * @param content: montant du paiement.
     */
    public void receive(Paiement content) {
        // TODO implement here
    }

    /**
     * @param content : la demande de programme personnalisé envoyée par un client.
     * Ajoute la demande a la liste de demande du coach.
     */
    public void receive(Demande content) {
        // TODO implement here
    }

    /**
     * @param content
     * Ajoute le produit à la liste de produit du client.
     */
    public void receive(Produit content) {
        // TODO implement here
    }

    /**
     * @param content
     * Ajoute la notification à la liste de notification du client.
     */
    public void receive(Notification content) {
        // TODO implement here
    }
}