package com.fitj.classes;

import com.fitj.interfaces.NotifReceiver;
import com.fitj.interfaces.ProductReceiver;

import java.util.*;

/**
 * 
 */
public class Client extends Utilisateur implements ProductReceiver, NotifReceiver {

    /**
     * Default constructor
     */
    public Client() {
    }

    /**
     * 
     */
    private int id;

    /**
     * 
     */
    private String mail;

    /**
     * 
     */
    private String mdp;

    /**
     * 
     */
    private double poid;

    /**
     * 
     */
    private int taille;

    /**
     * 
     */
    private String pseudo;

    /**
     * 
     */
    private String photo;

    /**
     * 
     */
    public void Operation1() {
        // TODO implement here
    }

    /**
     * @param content
     */
    public void receive(Produit content) {
        // TODO implement here
    }

    /**
     * @param content
     */
    public void receive(Notification content) {
        // TODO implement here
    }

}