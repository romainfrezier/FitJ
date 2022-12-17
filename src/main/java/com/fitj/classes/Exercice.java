package com.fitj.classes;

import java.util.*;

/**
 * Classe qui représente un exercice.
 * @author Paco Munarriz, Etienne Tillier
 */
public class Exercice {


    /**
     * L'id unique de l'exercice.
     */
    private int id;

    /**
     * Le nom de l'exercice.
     */
    private String nom;

    /**
     * La description de l'exercice.
     */
    private String description;

    /**
     * Constructeur par défaut
     */
    public Exercice(int id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
    }

    /**
     * @return l'id de l'exercice
     */
    public int getId() {
        return id;
    }

    /**
     * @return le nom de l'exercice
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return la description de l'exercice
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set l'id de l'exercice
     * @param id int, l'id de l'exercice
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set le nom de l'exercice
     * @param nom String, le nom de l'exercice
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Set la description de l'exercice
     * @param description String, la description de l'exercice
     */
    public void setDescription(String description) {
        this.description = description;
    }

}