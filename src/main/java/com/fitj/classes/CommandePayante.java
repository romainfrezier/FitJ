package com.fitj.classes;


/**
 * Une classe qui représente une commande payante.
 * @author Paco Munarriz
 */
public class CommandePayante extends Commande {
    /**
     * Constructeur par défaut
     */
    public CommandePayante(Client client, Coach coach, Produit produit, int id) {
        super(client, coach, produit, id);
    }

    /**
     * Constructeur avec demande
     */
    public CommandePayante(Client client, Coach coach, Produit produit, int id, Demande demande) {
        super(client, coach, produit, id, demande);
    }

}