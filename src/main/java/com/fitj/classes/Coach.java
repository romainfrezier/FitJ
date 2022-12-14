package com.fitj.classes;

import com.fitj.interfaces.DemandReceiver;
import com.fitj.interfaces.PaymentReceiver;

import java.util.*;

/**
 * Une classe qui représente un coach.
 * Un coach est un utilisateur qui peut proposer des produits.
 */
public class Coach extends Client implements PaymentReceiver, DemandReceiver {

    /**
     * Default constructor
     */
    public Coach() {
    }

    /**
     * @param content
     */
    public void receive(Paiement content) {
        // TODO implement here
    }

    /**
     * @param content
     * Ajoute la demande a la liste de demande du coach.
     */
    public void receiveDemande(Demande content) {
        // TODO implement here
    }

    @Override
    public void receiveNotification(Notification content) {

    }

    @Override
    public void receiveProduit(Produit content) {

    }
}