package com.fitj.classes;

import com.fitj.enums.ProgrammeType;


/**
 * Cette classe représente un Programme, qui est un type de Produit.
 * Il a un type et une durée (en mois).
 * @see Produit
 * @author Paul Merceur
 */
public abstract class Programme extends Produit {


    /**
     * Le type du programme.
     */
    private ProgrammeType type;

    /**
     * La durée du programme, en mois.
     */
    private int nbMois;

    /**
     * Constructeur par défaut.
     */
    public Programme(int id, String nom, String description, double prix, ProgrammeType type, int nbMois, Coach coach) {
        super(id, nom, description, prix, coach);
        this.type = type;
        this.nbMois = nbMois;
    }

    public Programme(int idProduit) {
        super(idProduit);
    }

    public Programme(int idProduit, String nom) {
        super(idProduit, nom);
    }

    /**
     * Set le type du programme
     * @param type ProgrammeType, le type du programme
     */
    public void setType(ProgrammeType type) {
        this.type = type;
    }

    /**
     * Set le nombre de mois du programme
     * @param nbMois int, le nombre de mois du programme
     */
    public void setNbMois(int nbMois) {
        this.nbMois = nbMois;
    }

    /**
     * @return le type du programme
     */
    public ProgrammeType getType() {
        return type;
    }

    /**
     * @return le nombre de mois du programme
     */
    public int getNbMois() {
        return nbMois;
    }
}