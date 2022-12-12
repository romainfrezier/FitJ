package com.fitj.classes;

import com.fitj.interfaces.CommentReceiver;
import com.fitj.interfaces.Sendable;

import java.util.*;

/**
 * 
 */
public abstract class Produit implements Sendable, CommentReceiver {

    /**
     * Default constructor
     */
    public Produit() {
    }

    /**
     * 
     */
    private int id;

    /**
     * 
     */
    private String nom;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private double prix;

    /**
     * @param destinataire
     */
    public void send(Client destinataire) {
        // TODO implement here
    }

    /**
     * @param content
     */
    public void receive(Avis content) {
        // TODO implement here
    }

}