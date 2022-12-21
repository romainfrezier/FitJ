package com.fitj.classes;

import com.fitj.interfaces.IsIngredient;

import java.util.*;

/**
 * Cette classe représente une Recette, qui compose un ProgrammeNutrition.
 * @see ProgrammeNutrition
 * @author Paul Merceur, Etienne Tillier
 */
public class Recette implements IsIngredient {

    /**
     * La liste des ingrédients
     */
    private List<IsIngredient> ingredients;


    /**
     * L'identifiant unique de la recette.
     */
    private int id;

    /**
     * Le nom de la recette.
     */
    private String nom;

    /**
     * Le coach de la recette
     */
    private Coach coach;

    /**
     * Constructeur par défaut pour getAll
     */
    public Recette(int id, String nom, Coach coach) {
        this.id = id;
        this.nom = nom;
        this.coach = coach;
        this.ingredients = new ArrayList<>();
    }

    /**
     * Constructeur avec recette remplie d'ingrédients
     */
    public Recette(int id, String nom, Coach coach, ArrayList<IsIngredient> ingredients) {
        this.id = id;
        this.nom = nom;
        this.coach = coach;
        this.ingredients = ingredients;
    }

    /**
     * Ajoute un ingrédient à la recette
     * @param ingredient isIngredient, l'ingrédient à ajouter à la recette
     */
    public void ajouterIngredient(IsIngredient ingredient){
        this.ingredients.add(ingredient);
    }

    /**
     * Retire un ingrédient de la recette
     * @param ingredient isIngredient, l'ingrédient à retirer de la recette
     */
    public void retirerIngredient(IsIngredient ingredient){
        this.ingredients.remove(ingredient);
    }

    /**
     * @return la liste des ingrédients de la recette
     */
    public List<IsIngredient> getIngredients() {
        return ingredients;
    }

    /**
     * @return l'id de la recette
     */
    public int getId() {
        return id;
    }

    /**
     * @return le nom de la recette
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return le coach de la recette
     */
    public Coach getCoach() {
        return coach;
    }

    /**
     * Set la liste des ingrédients de la recette
     * @param ingredients List<isIngredient>, la liste des ingrédients
     */
    public void setIngredients(List<IsIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Set l'id de la recette
     * @param id int, l'id de la recette
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set le nom de la recette
     * @param nom String, le nom de la recette
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Set le coach de la recette
     * @param coach Coach, le coach de la recette
     */
    public void setCoach(Coach coach) {
        this.coach = coach;
    }
}