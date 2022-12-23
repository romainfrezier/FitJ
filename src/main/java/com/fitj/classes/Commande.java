package com.fitj.classes;


/**
 * Classe qui représente une commande.
 * Une commande est un ensemble de produits achetés par un client à un coach.
 * @author Paco Munarriz
 */
public abstract class Commande {

    /**
     * L'identifiant unique de la commande.
     */
    private int id;

    /**
     * Le client de la commande
     */
    private Client client;

    /**
     * Le coach de la commande
     */
    private Coach coach;

    /**
     * Le produit de la commande
     */
    private Produit produit;

    /**
     * La demande de la commande
     */
    private Demande demande;


    /**
     * Constructeur
     */
    public Commande(Client client, Coach coach, Produit produit, int id) {
        this.client = client;
        this.coach = coach;
        this.produit = produit;
        this.id = id;
        this.demande = null;
    }

    /**
     * Constructeur avec demande
     */
    public Commande(Client client, Coach coach, Produit produit, int id, Demande demande) {
        this.client = client;
        this.coach = coach;
        this.produit = produit;
        this.id = id;
        this.demande = demande;
    }

    /**
     * @return le client de la commande
     */
    public Client getClient() {
        return client;
    }

    /**
     * @return le coach de la commande
     */
    public Coach getCoach() {
        return coach;
    }

    /**
     * @return le produit de la commande
     */
    public Produit getProduit() {
        return produit;
    }

    /**
     * @return la demande de la commande
     */
    public Demande getDemande() {
        return demande;
    }

    /**
     * Set le client de la commande
     * @param client Client, le client de la commande
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Set le coach de la commande
     * @param coach Coach, le coach de la commande
     */
    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    /**
     * Set le produit de la commande
     * @param produit Produit, le produit de la commande
     */
    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    /**
     * Set la demande de la commande
     * @param demande Demande, la demande de la commande
     */
    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    /**
     * Set l'id de la commande
     * @param id int, l'id de la commande
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return l'id de la commande
     */
    public int getId() {
        return id;
    }
}