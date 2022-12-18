package com.fitj.exceptions;

public class BadPasswordException extends Exception {
    public BadPasswordException(String message) {
        super("Le mot de passe est incorrect : " + message);
    }
}

