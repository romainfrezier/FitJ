package com.fitj.classes;

import com.fitj.interfaces.isIngredient;

import java.util.*;

/**
 * Une classe qui représente un aliment.
 * @author Paco Munarriz
 */
public class Aliment implements isIngredient {

    /**
     * Constructeur par défaut
     */
    public Aliment() {
    }

    /**
     * L'id unique de l'aliment
     */
    private int id;

    /**
     * Le nom de l'aliment
     */
    private String nom;

}