package com.fitj.classes;

import com.fitj.interfaces.NotifReceiver;
import com.fitj.interfaces.ProductReceiver;

import java.util.*;

/**
 * Une classe qui représente un client.
 */
public class Client extends Utilisateur implements ProductReceiver, NotifReceiver {

    /**
     * Default constructor
     */
    public Client() {
    }

    /**
     * L'identifiant du client.
     */
    private int id;

    /**
     * Le mail du client.
     */
    private String mail;

    /**
     * Le mot de passe du client.
     */
    private String mdp;

    /**
     * Le poids du client.
     */
    private double poid;

    /**
     * La taille du client.
     */
    private int taille;

    /**
     * Le pseudo du client.
     */
    private String pseudo;

    /**
     * La photo du client.
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
     * Ajoute le produit a la liste de produit du client.
     */
    public void receiveProduit(Produit content) {
        // TODO implement here
    }

    /**
     * @param content
     * Ajoute la notification a la liste de notification du client.
     */
    public void receiveNotification(Notification content) {
        // TODO implement here
    }

    /**
     * Constructeur de la classe Client.
     */
    public Client(int id, String mail, String mdp, double poid, int taille, String pseudo, String photo) {
        this.id = id;
        this.mail = mail;
        this.mdp = mdp;
        this.poid = poid;
        this.taille = taille;
        this.pseudo = pseudo;
        this.photo = photo;

}