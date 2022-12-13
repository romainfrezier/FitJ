package com.fitj.interfaces;

import com.fitj.classes.Produit;

import java.util.*;

/**
 * Interface qui définit une méthode pour recevoir un produit.
 * @see Receiver
 */
public interface ProductReceiver {

    /**
     * Reçoit le produit.
     *
     * @param content Produit, le contenu du produit
     */
    public void receive(Produit content);

}