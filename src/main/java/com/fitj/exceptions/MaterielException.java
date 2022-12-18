package com.fitj.exceptions;

public class MaterielException extends Exception {
    public MaterielException(String nomMateriel, String message) {
        super("Un problème est survenu avec le materiel " + nomMateriel + " : " + message);
    }
}
