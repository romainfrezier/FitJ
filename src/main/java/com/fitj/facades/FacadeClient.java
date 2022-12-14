package com.fitj.facades;

public class FacadeClient extends Facade {

    private static FacadeClient instance = null;
    protected FacadeClient(){

    }

    public static FacadeClient getInstance(){
        if (instance == null){
            instance = new FacadeClient();
        }
        return instance;
    }
}
