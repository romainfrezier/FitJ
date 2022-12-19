package com.fitj.exceptions;

public class BadPageException extends Exception {
    public BadPageException(String message) {
        super("La page n'existe pas : " + message);
    }
}
