package com.fitj.classes;

import java.util.*;

/**
 * Une classe qui représente une demande de programme personnalisé d'un client à un coach.
 */
public class Demande {

    /**
     * Default constructor
     */
    public Demande() {
    }

    /**
     * L'id de la demande.
     */
    private int id;

    /**
     * Le nombre de mois du programme.
     */
    private int nbMois;

    /**
     * La description de la demande.
     */
    private String description;

    /**
     * Est-ce que cest un programme sportif?
     */
    private boolean programmeSportif;

    /**
     * Est-ce que cest un programme nutritionnek?
     */
    private boolean programmeNutrition;

    /**
     * Nombre de séances par semaine
     */
    private int nbSeanceSemaine;

    /**
     * Nombre de recette par semaine
     */
    private int nbRecetteSemaine;

}