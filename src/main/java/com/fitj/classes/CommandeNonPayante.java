package com.fitj.classes;

import java.util.*;

/**
 * Classe qui représente une commande gratuite.
 * @author Paco Munarriz
 */
public class CommandeNonPayante extends Commande {

    /**
     * Constructeur par défaut
     */
    public CommandeNonPayante(Client client, Coach coach, Produit produit, int id, Date date) {
        super(client, coach, produit, id, date);
    }


}