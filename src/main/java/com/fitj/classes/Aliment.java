package com.fitj.classes;

import com.fitj.interfaces.Ingredient;

/**
 * Une classe qui représente un aliment.
 * @author Paco Munarriz, Etienne Tillier
 */
public class Aliment implements Ingredient {


    /**
     * L'id unique de l'aliment
     */
    private int id;

    /**
     * Le nom de l'aliment
     */
    private String nom;

    /**
     * Constructeur par défaut
     */
    public Aliment(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    /**
     * @return l'id de l'aliment
     */
    public int getId() {
        return id;
    }

    /**
     * @return le nom de l'aliment
     */
    public String getNom() {
        return nom;
    }

    /**
     * Set l'id de l'aliment
     * @param id int, l'id de l'aliment
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set le nom de l'aliment
     * @param nom String, le nom de l'aliment
     */
    public void setNom(String nom) {
        this.nom = nom;
    }


}