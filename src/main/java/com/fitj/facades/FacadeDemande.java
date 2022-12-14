package com.fitj.facades;

public class FacadeDemande extends Facade {

    private static FacadeDemande instance = null;
    protected FacadeDemande(){

    }

    public static FacadeDemande getInstance(){
        if (instance == null){
            instance = new FacadeDemande();
        }
        return instance;
    }
}
