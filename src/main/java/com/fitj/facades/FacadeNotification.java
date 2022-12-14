package com.fitj.facades;

public class FacadeNotification extends Facade {

    private static FacadeNotification instance = null;
    protected FacadeNotification(){

    }

    public static FacadeNotification getInstance(){
        if (instance == null){
            instance = new FacadeNotification();
        }
        return instance;
    }
}
