package com.fitj.exceptions;

public class IngredientException extends Exception {
    public IngredientException(String nomIngredient, String message) {
        super("Un problème est survenu avec l'ingrédient " + nomIngredient + " : " + message);
    }
}
