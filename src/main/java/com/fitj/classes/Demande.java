package com.fitj.classes;

import java.util.*;

/**
 * Une classe qui représente une demande de programme personnalisé d'un client à un coach.
 * @author Paco Munarriz, Etienne Tillier
 */
public class Demande {


    /**
     * L'id unique de la demande.
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


    /**
     * Constructeur par défaut
     */
    public Demande(int id, int nbMois, String description, boolean programmeSportif, boolean programmeNutrition, int nbSeanceSemaine, int nbRecetteSemaine) {
        this.id = id;
        this.nbMois = nbMois;
        this.description = description;
        this.programmeSportif = programmeSportif;
        this.programmeNutrition = programmeNutrition;
        this.nbSeanceSemaine = nbSeanceSemaine;
        this.nbRecetteSemaine = nbRecetteSemaine;
    }

    /**
     * @return l'id de la demande
     */
    public int getId() {
        return id;
    }

    /**
     * @return le nombre de mois du programme demandé
     */
    public int getNbMois() {
        return nbMois;
    }

    /**
     * @return la description de la demande
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return si l'on veut un programme sportif
     */
    public boolean isProgrammeSportif() {
        return programmeSportif;
    }

    /**
     * @return si l'on veut un programme de nutrition
     */
    public boolean isProgrammeNutrition() {
        return programmeNutrition;
    }

    /**
     * @return le nombre de séance par semaine
     */
    public int getNbSeanceSemaine() {
        return nbSeanceSemaine;
    }

    /**
     * @return le nombre de recette semaine
     */
    public int getNbRecetteSemaine() {
        return nbRecetteSemaine;
    }

    /**
     * Set l'id de la demande
     * @param id int, l'id de la demande
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set le nombre de mois du programme demandé
     * @param nbMois int, le nombre de mois du programme demandé
     */
    public void setNbMois(int nbMois) {
        this.nbMois = nbMois;
    }

    /**
     * Set la description de la demande
     * @param description String, la description de la demande
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set si c'est un programme sportif ou non
     * @param programmeSportif boolean, true si la demande est pour un programme sportif
     */
    public void setProgrammeSportif(boolean programmeSportif) {
        this.programmeSportif = programmeSportif;
    }

    /**
     * Set si c'est un programme nutritionnel ou non
     * @param programmeNutrition boolean, true si la demande est pour un programme nutritionnel
     */
    public void setProgrammeNutrition(boolean programmeNutrition) {
        this.programmeNutrition = programmeNutrition;
    }

    /**
     * Set le nombre de séance par semaine du programme demandé
     * @param nbSeanceSemaine int, le nombre de séance par semaine du programme demandé
     */
    public void setNbSeanceSemaine(int nbSeanceSemaine) {
        this.nbSeanceSemaine = nbSeanceSemaine;
    }

    /**
     * Set le nombre de recette par semaine du programme demandé
     * @param nbRecetteSemaine int, le nombre de recette par semaine du programme demandé
     */
    public void setNbRecetteSemaine(int nbRecetteSemaine) {
        this.nbRecetteSemaine = nbRecetteSemaine;
    }
}