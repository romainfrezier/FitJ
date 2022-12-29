package com.fitj.exceptions;

public class BannedAccountException extends Exception {
    public BannedAccountException() {
        super("Votre compte a été banni");
    }
}
