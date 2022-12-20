package com.fitj.classes;

import java.util.*;

/**
 * Cette classe représente une Seance, qui compose un ProgrammeSportif et qui est un type de Produit.
 * @see ProgrammeNutrition
 * @see Produit
 * @author Paul Merceur, Etienne Tillier
 */
public class Seance extends Produit {

    private List<Exercice> listeExercice;

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
     * Constructeur avec des exercices
     */
    public Seance(int id, String nom, String description, double prix, Coach coach, Sport sport, ArrayList<Exercice> listeExercice) {
        super(id, nom, description, prix, coach);
        this.listeExercice = listeExercice;
        this.sport = sport;
    }

    /**
     * Ajoute un exercice à la séance
     * @param exercice Exercice, l'exercice à ajouter à la séance
     */
    public void ajouterExercice(Exercice exercice){
        this.listeExercice.add(exercice);
    }

    /**
     * Retire un exercice de la séance
     * @param exercice Exercice, l'exercice à retirer de la séance
     */
    public void retirerExercice(Exercice exercice){
        this.listeExercice.remove(exercice);
    }


    /**
     * @return la liste des exercices
     */
    public List<Exercice> getListeExercice() {
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