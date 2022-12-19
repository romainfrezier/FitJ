package com.fitj.exceptions;

public class ProduitException extends Exception {
    public ProduitException(String nomProduit, String message) {
        super("Un problème est survenu avec le produit " + nomProduit + " : " + message);
    }
}
