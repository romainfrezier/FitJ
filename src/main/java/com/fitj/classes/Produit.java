package com.fitj.classes;

import com.fitj.interfaces.CommentReceiver;
import com.fitj.interfaces.Sendable;


/**
 * Une classe abstraite qui représente un produit.
 * Cette classe implémente les interfaces `Sendable` et `CommentReceiver`.
 * @see Sendable
 * @see CommentReceiver
 * @author Paul Merceur
 */
public abstract class Produit implements Sendable, CommentReceiver {

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

    private Coach coach;

    /**
     * Constructeur par défaut
     */
    public Produit(int id, String nom, String description, double prix, Coach coach) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.coach = coach;
    }

    /**
     * Constructeur avec un id
     */
    public Produit(int id) {
        this.id = id;
    }

    public Produit(int idseance, String nom) {
        this.id = idseance;
        this.nom = nom;
    }

    /**
     * @return le coach qui vend le produit
     */
    public Coach getCoach(){
        return this.coach;
    }

    /**
     * Set le coach vendeur du produit
     * @param coach Coach, le coach vendu du produit
     */
    public void setCoach(Coach coach){
        this.coach = coach;
    }

    /**
     * Set l'id du produit
     * @param id int, l'id du produit
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set le nom du produit
     * @param nom String, le nom du produit
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Set la description du produit
     * @param description String, la description du produit
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set le prix du produit
     * @param prix double, le prix du produit
     */
    public void setPrix(double prix) {
        this.prix = prix;
    }

    /**
     * @return l'id du produit
     */
    public int getId() {
        return id;
    }

    /**
     * @return le nom du produit
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return la description du produit
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return le prix du produit
     */
    public double getPrix() {
        return prix;
    }

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