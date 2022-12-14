package com.fitj.classes;

import com.fitj.interfaces.NotifReceiver;
import com.fitj.interfaces.ProductReceiver;

import java.util.*;

/**
 * Une classe qui représente un client.
 * @author Paco Munarriz
 */
public class Client extends Utilisateur implements ProductReceiver, NotifReceiver {

    /**
     * Constructeur par défaut
     */
    public Client() {
    }

    /**
     * L'identifiant unique du client.
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
    private double poids;

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
     * Constructeur de la classe Client.
     */
    public Client(int id, String mail, String mdp, double poids, int taille, String pseudo, String photo) {
        this.id = id;
        this.mail = mail;
        this.mdp = mdp;
        this.poids = poids;
        this.taille = taille;
        this.pseudo = pseudo;
        this.photo = photo;
    }

    /**
     * @param content
     * Ajoute le produit à la liste de produit du client.
     */
    public void receive(Produit content) {
        // TODO implement here
    }

    /**
     * @param content
     * Ajoute la notification à la liste de notification du client.
     */
    public void receive(Notification content) {
        // TODO implement here
    }

}