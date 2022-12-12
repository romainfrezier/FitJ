package com.fitj.classes;

import com.fitj.interfaces.Sendable;

import java.util.*;

/**
 * 
 */
public abstract class Paiement implements Sendable {

    /**
     * Default constructor
     */
    public Paiement() {
    }

    /**
     * 
     */
    private int id;

    /**
     * 
     */
    private double montant;

    /**
     * @param destinataire
     */
    public void send(Client destinataire) {
        // TODO implement here
    }

}