package com.fitj.classes;


import java.util.Date;

/**
 * Une classe qui représente une commande payante.
 * @author Paco Munarriz
 */
public class CommandePayante extends Commande {
    /**
     * Constructeur par défaut
     */
    public CommandePayante(Client client, Coach coach, Produit produit, int id, Date date) {
        super(client, coach, produit, id, date);
    }

    /**
     * Constructeur avec demande
     */
    public CommandePayante(Client client, Coach coach, Produit produit, int id, Date date, Demande demande) {
        super(client, coach, produit, id, date, demande);
    }

}