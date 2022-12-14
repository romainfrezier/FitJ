package com.fitj.facades;

public class FacadeExercice extends Facade {

    private static FacadeExercice instance = null;
    protected FacadeExercice(){

    }

    public static FacadeExercice getInstance(){
        if (instance == null){
            instance = new FacadeExercice();
        }
        return instance;
    }
}
