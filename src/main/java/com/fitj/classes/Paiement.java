package com.fitj.classes;

import com.fitj.interfaces.Sendable;

import java.util.*;

/**
 * Classe qui représente un paiement.
 */
public abstract class Paiement implements Sendable {

    /**
     * Default constructor
     */
    public Paiement() {
    }

    /**
     * L'id du paiement.
     */
    private int id;

    /**
     * Le montant du paiement.
     */
    private double montant;

    /**
     * @param destinataire
     * Envoie le montant du paiement au destinataire.
     */
    public void send(Client destinataire) {
        // TODO implement here
    }

}