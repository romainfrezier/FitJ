package com.fitj.exceptions;

public class RecetteException extends Exception {
    public RecetteException(String nomRecette, String message) {
        super("Un problème est survenu avec la recette " + nomRecette + " : " + message);
    }
}
