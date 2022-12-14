package com.fitj.facades;

public class FacadeCoach extends Facade {
    private static FacadeCoach instance = null;
    protected FacadeCoach(){

    }

    public static FacadeCoach getInstance(){
        if (instance == null){
            instance = new FacadeCoach();
        }
        return instance;
    }
}
