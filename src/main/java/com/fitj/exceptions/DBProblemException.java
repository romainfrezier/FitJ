package com.fitj.exceptions;

public class DBProblemException extends Exception {
    public DBProblemException(String message) {
        super("Problème de base de données : " + message);
    }
}
