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
     * @param content: montant du paiement.
     */
    public void receivePaiement(Paiement content) {
        // TODO implement here
    }

    /**
     * @param content : la demande de programme personnalisé envoyée par un client.
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