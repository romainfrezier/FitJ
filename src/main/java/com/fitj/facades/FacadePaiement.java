package com.fitj.facades;

public class FacadePaiement extends Facade {

    private static FacadePaiement instance = null;
    protected FacadePaiement(){

    }

    public static FacadePaiement getInstance(){
        if (instance == null){
            instance = new FacadePaiement();
        }
        return instance;
    }
}
