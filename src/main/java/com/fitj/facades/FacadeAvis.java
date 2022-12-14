package com.fitj.facades;

public class FacadeAvis extends Facade {

    private static FacadeAvis instance = null;
    protected FacadeAvis(){

    }

    public static FacadeAvis getInstance(){
        if (instance == null){
            instance = new FacadeAvis();
        }
        return instance;
    }
}
