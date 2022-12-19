package com.fitj.exceptions;

public class ExerciceException extends Exception {
    public ExerciceException(String nomExercice, String message) {
        super("Un problème est survenu avec l'exercice " + nomExercice + " : " + message);
    }
}
