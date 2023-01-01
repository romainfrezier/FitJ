package com.fitj.classes;

/**
 * Classe qui représente un matériel.
 * @author Paco Munarriz, Etienne Tillier, Romain Frezier
 */
public class Materiel {


    /**
     * L'id unique du matériel.
     */
    private int id;

    /**
     * Le nom du matériel.
     */
    private String nom;

    /**
     * Constructeur par défaut
     */
    public Materiel(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }


    /**
     * @return l'id du matériel
     */
    public int getId() {
        return id;
    }

    /**
     * @return le nom du matériel
     */
    public String getNom() {
        return nom;
    }

    /**
     * Set l'id du matériel
     * @param id, l'id du matériel
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set le nom du matériel
     * @param nom, le nom du matériel
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
}