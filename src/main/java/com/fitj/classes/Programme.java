package com.fitj.classes;

import com.fitj.enums.ProgrammeType;

import java.util.*;

/**
 * Cette classe représente un Programme, qui est un type de Produit.
 * Il a un type et une durée (en mois).
 * @see Produit
 */
public abstract class Programme extends Produit {

    /**
     * Constructeur par défaut.
     */
    public Programme() {
    }

    /**
     * Le type du programme.
     */
    private ProgrammeType type;

    /**
     * La durée du programme, en mois.
     */
    private int nbMois;

}