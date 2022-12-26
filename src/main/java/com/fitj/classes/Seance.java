package com.fitj.classes;

import kotlin.Triple;

import java.util.*;

/**
 * Cette classe représente une Seance, qui compose un ProgrammeSportif et qui est un type de Produit.
 * @see ProgrammeNutrition
 * @see Produit
 * @author Paul Merceur, Etienne Tillier
 */
public class Seance extends Produit {

    private List<Triple<Exercice, Integer, Integer>> listeExercice;

    private Sport sport;

    /**
     * Constructeur par défaut
     */
    public Seance(int id, String nom, String description, double prix, Coach coach, Sport sport) {
        super(id, nom, description, prix, coach);
        this.listeExercice = new ArrayList<>();
        this.sport = sport;
    }

    /**
     * Constructeur pour getAll
     */
    public Seance(int id, String nom, String description, double prix, Coach coach) {
        super(id, nom, description, prix, coach);
        this.listeExercice = new ArrayList<>();
        this.sport = null;
    }

    /**
     * Constructeur avec des exercices
     */
    public Seance(int id, String nom, String description, double prix, Coach coach, Sport sport, List<Triple<Exercice, Integer, Integer>> listeExercice) {
        super(id, nom, description, prix, coach);
        this.listeExercice = listeExercice;
        this.sport = sport;
    }

    public Seance(int idseance) {
        super(idseance);
    }

    /**
     * Ajoute un exercice à la séance
     * @param exercice Exercice, l'exercice à ajouter à la séance
     */
    public void ajouterExercice(Exercice exercice, int nbSeries, int nbRepetitions) {
        this.listeExercice.add(new Triple<>(exercice, nbSeries, nbRepetitions));
    }

    /**
     * Retire un exercice de la séance
     * @param exercice Exercice, l'exercice à retirer de la séance
     */
    public void retirerExercice(Exercice exercice, int nbSeries, int nbRepetitions){
        this.listeExercice.remove(new Triple<>(exercice, nbSeries, nbRepetitions));
    }


    /**
     * @return la liste des exercices
     */
    public List<Triple<Exercice, Integer, Integer>> getListeExercice() {
        return listeExercice;
    }

    /**
     * @return le sport du la séance
     */
    public Sport getSport() {
        return sport;
    }

    /**
     * Set le sport de la séance
     * @param sport Sport, le sport de la séance
     */
    public void setSport(Sport sport){
        this.sport = sport;
    }

    /**
     * Reçoit un commentaire pour la séance.
     *
     * @param content Avis, le contenu du commentaire
     */
    @Override
    public void receive(Avis content) {

    }

    /**
     * Envoie la séance à un destinataire.
     *
     * @param destinataire Client, le destinataire à qui envoyer le programme
     */
    @Override
    public void send(Client destinataire) {

    }
}