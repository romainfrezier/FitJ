package com.fitj.classes;

/**
 * Cette classe représente un Sport
 * @author Paul Merceur, Etienne Tillier, Romain Frezier
 */
public class Sport {


    /**
     * L'identifiant unique du sport.
     */
    private int id;

    /**
     * Le nom du sport.
     */
    private String nom;

    /**
     * Constructeur par défaut
     */
    public Sport(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    /**
     * Set l'id du sport
     * @param id int, l'id du sport
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set le nom du sport
     * @param nom String, le nom du sport
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return l'id du sport
     */
    public int getId() {
        return id;
    }

    /**
     * @return le nom du sport
     */
    public String getNom() {
        return nom;
    }
}