package com.fitj.facades;

public class FacadeCommande extends Facade {

    private static FacadeCommande instance = null;
    protected FacadeCommande(){

    }

    public static FacadeCommande getInstance(){
        if (instance == null){
            instance = new FacadeCommande();
        }
        return instance;
    }
}
