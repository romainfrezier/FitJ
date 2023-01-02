package com.fitj.classes;

import com.fitj.enums.ProgrammeType;

import java.util.*;

/**
 * Cette classe représente un ProgrammeNutrition, qui est un type de Programme.
 * @see Programme
 * @author Paul Merceur, Etienne Tillier
 */
public class ProgrammeNutrition extends Programme {

    private List<Recette> listeRecette;

    /**
     * Constructeur par défaut
     */
    public ProgrammeNutrition(int id, String nom, String description, double prix, ProgrammeType type, int nbMois, Coach coach) {
        super(id, nom, description, prix, type, nbMois, coach);
        this.listeRecette = new ArrayList<>();
    }

    /**
     * Constructeur avec recettes
     */
    public ProgrammeNutrition(int id, String nom, String description, double prix, ProgrammeType type, int nbMois, Coach coach, ArrayList<Recette> listeRecette) {
        super(id, nom, description, prix, type, nbMois, coach);
        this.listeRecette = listeRecette;
    }

    public ProgrammeNutrition(int idProduit) {
        super(idProduit);
    }

    public ProgrammeNutrition(int idProduit, String nom, String description, double prix) {
        super(idProduit, nom, description, prix);
    }

    /**
     * @return la liste des recettes du programme
     */
    public List<Recette> getListeRecette() {
        return listeRecette;
    }

    /**
     * Ajoute une recette au programme
     * @param recette Recette, la recette à ajouter
     */
    public void ajouterRecette(Recette recette){
        this.listeRecette.add(recette);
    }

    /**
     * Retire une recette du programme
     * @param recette Recette, la recette à retirer
     */
    public void retirerRecette(Recette recette){
        this.listeRecette.remove(recette);
    }

    /**
     * Reçoit un commentaire pour le programme nutrition.
     *
     * @param content Avis, le contenu du commentaire
     */
    @Override
    public void receive(Avis content) {

    }

    /**
     * Envoie le programme nutrition à un destinataire.
     *
     * @param destinataire Client, le destinataire à qui envoyer le programme
     */
    @Override
    public void send(Client destinataire) {

    }
}