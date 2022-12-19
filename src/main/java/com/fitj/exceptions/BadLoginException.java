package com.fitj.exceptions;

public class BadLoginException extends Exception {
    public BadLoginException(String message) {
        super("Le login est incorrect : " + message);
    }
}
