package com.fitj.facades;

public class FacadeProgramme extends Facade {

    private static FacadeProgramme instance = null;
    protected FacadeProgramme(){

    }

    public static FacadeProgramme getInstance(){
        if (instance == null){
            instance = new FacadeProgramme();
        }
        return instance;
    }
}
