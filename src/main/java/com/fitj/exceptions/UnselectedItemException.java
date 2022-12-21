package com.fitj.exceptions;

public class UnselectedItemException extends Exception {
    public UnselectedItemException(String message) {
        super("Aucun élément a été sélectionné : " + message);
    }
}
