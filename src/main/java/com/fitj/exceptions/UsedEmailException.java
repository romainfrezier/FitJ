package com.fitj.exceptions;

public class UsedEmailException extends Exception {
    public UsedEmailException(String message) {
        super("L'email est déjà relié à un compte : " + message);
    }
}
