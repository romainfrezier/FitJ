package com.fitj.facades;

public class FacadeSeance extends Facade {

    private static FacadeSeance instance = null;
    protected FacadeSeance(){

    }

    public static FacadeSeance getInstance(){
        if (instance == null){
            instance = new FacadeSeance();
        }
        return instance;
    }
}
