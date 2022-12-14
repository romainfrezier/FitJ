package com.fitj.classes;

import com.fitj.interfaces.Sendable;

import java.util.*;

/**
 * Classe qui représente un paiement.
 * @author Paco Munarriz
 */
public abstract class Paiement implements Sendable {

    /**
     * Constructeur par défaut
     */
    public Paiement() {
    }

    /**
     * L'id unique du paiement.
     */
    private int id;

    /**
     * Le montant du paiement.
     */
    private double montant;

    /**
     * @param destinataire: Le coach qui reçoit le paiement.
     * Envoie le montant du paiement au destinataire.
     */
    public void send(Client destinataire) {
        // TODO implement here
    }

}