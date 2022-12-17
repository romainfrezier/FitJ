package com.fitj.exceptions;

public class UsedPseudoException extends Exception {
    public UsedPseudoException(String message) {
        super("Le pseudo " + message + " est déjà utilisé");
    }
}
