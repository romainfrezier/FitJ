package com.fitj.facades;

public class FacadeAdmin extends Facade {

    private static FacadeAdmin instance = null;
    protected FacadeAdmin(){

    }

    public static FacadeAdmin getInstance(){
        if (instance == null){
            instance = new FacadeAdmin();
        }
        return instance;
    }
}
