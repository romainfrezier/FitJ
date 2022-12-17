package com.fitj.classes;

import java.util.*;

/**
 * Une classe qui représente une commande payante.
 * @author Paco Munarriz
 */
public class CommandePayante extends Commande {

    /**
     * Prix de la commande
     */
    private double prix;

    /**
     * Constructeur par défaut
     */
    public CommandePayante(Client client, Coach coach, Produit produit, int id, double prix) {
        super(client, coach, produit, id);
        this.prix = prix;
    }

    /**
     * Set le prix de la commande
     * @param prix int, le prix de la commande
     */
    public void setPrix(double prix) {
        this.prix = prix;
    }

    /**
     * @return le prix de la commande
     */
    public double getPrix() {
        return prix;
    }
}