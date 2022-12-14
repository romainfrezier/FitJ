package com.fitj.facades;

public class FacadeSport extends Facade {


    private static FacadeSport instance = null;
    protected FacadeSport(){

    }

    public static FacadeSport getInstance(){
        if (instance == null){
            instance = new FacadeSport();
        }
        return instance;
    }
}
