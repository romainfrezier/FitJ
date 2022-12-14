package com.fitj.facades;

public class FacadeRecette extends Facade {

    private static FacadeRecette instance = null;
    protected FacadeRecette(){

    }

    public static FacadeRecette getInstance(){
        if (instance == null){
            instance = new FacadeRecette();
        }
        return instance;
    }
}
