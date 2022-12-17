package com.fitj.classes;

import com.fitj.interfaces.isIngredient;
import kotlin.Pair;

import java.util.*;

/**
 * Cette classe représente une Recette, qui compose un ProgrammeNutrition.
 * @see ProgrammeNutrition
 * @author Paul Merceur, Etienne Tillier
 */
public class Recette implements isIngredient {

    /**
     * La liste des ingrédients
     */
    private List<isIngredient> ingredients;


    /**
     * L'identifiant unique de la recette.
     */
    private int id;

    /**
     * Le nom de la recette.
     */
    private String nom;

    /**
     * Constructeur par défaut
     */
    public Recette(int id, String nom) {
        this.id = id;
        this.nom = nom;
        this.ingredients = new ArrayList<>();
    }

    /**
     * Constructeur avec recette remplie d'ingrédients
     */
    public Recette(int id, String nom, ArrayList<isIngredient> ingredients) {
        this.id = id;
        this.nom = nom;
        this.ingredients = ingredients;
    }

    /**
     * Ajoute un ingrédient à la recette
     * @param ingredient isIngredient, l'ingrédient à ajouter à la recette
     */
    public void ajouterIngredient(isIngredient ingredient){
        this.ingredients.add(ingredient);
    }

    /**
     * Retire un ingrédient de la recette
     * @param ingredient isIngredient, l'ingrédient à retirer de la recette
     */
    public void retirerIngredient(isIngredient ingredient){
        this.ingredients.remove(ingredient);
    }

}