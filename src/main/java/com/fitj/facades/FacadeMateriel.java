package com.fitj.facades;

public class FacadeMateriel extends Facade {

    private static FacadeMateriel instance = null;
    protected FacadeMateriel(){

    }

    public static FacadeMateriel getInstance(){
        if (instance == null){
            instance = new FacadeMateriel();
        }
        return instance;
    }
}
