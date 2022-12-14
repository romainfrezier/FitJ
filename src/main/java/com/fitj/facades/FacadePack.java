package com.fitj.facades;

public class FacadePack extends Facade {


    private static FacadePack instance = null;
    protected FacadePack(){

    }

    public static FacadePack getInstance(){
        if (instance == null){
            instance = new FacadePack();
        }
        return instance;
    }
}
