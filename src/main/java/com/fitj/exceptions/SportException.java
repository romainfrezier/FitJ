package com.fitj.exceptions;

public class SportException extends Exception {
    public SportException(String nomSport, String message) {
        super("Un problème est survenu avec le sport " + nomSport + " : " + message);
    }
}
