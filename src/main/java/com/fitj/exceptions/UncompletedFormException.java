package com.fitj.exceptions;

public class UncompletedFormException extends Exception {
    public UncompletedFormException(String message) {
        super("Le formulaire n'est pas complet : " + message);
    }
}
