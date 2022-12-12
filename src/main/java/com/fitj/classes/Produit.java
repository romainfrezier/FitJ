package com.fitj.classes;

import com.fitj.interfaces.CommentReceiver;
import com.fitj.interfaces.Sendable;

import java.util.*;

/**
 * Une classe abstraite qui représente un produit.
 * Cette classe implémente les interfaces `Sendable` et `CommentReceiver`.
 */
public abstract class Produit implements Sendable, CommentReceiver {

    /**
     * Constructeur par défaut
     */
    public Produit() {
    }

    /**
     * L'identifiant unique du produit.
     */
    private int id;

    /**
     * Le nom du produit.
     */
    private String nom;

    /**
     * La description du produit.
     */
    private String description;

    /**
     * Le prix du produit.
     */
    private double prix;

    /**
     * Envoie le produit à un destinataire.
     *
     * @param destinataire Client, le destinataire à qui envoyer le produit
     */
    public void send(Client destinataire) {
        // TODO implement here
    }

    /**
     * Reçoit un commentaire pour le produit.
     *
     * @param content Avis, le contenu du commentaire
     */
    public void receive(Avis content) {
        // TODO implement here
    }

}