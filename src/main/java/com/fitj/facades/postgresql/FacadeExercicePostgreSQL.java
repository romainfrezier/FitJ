package com.fitj.facades.postgresql;

import com.fitj.facades.FacadeExercice;

public class FacadeExercicePostgreSQL extends FacadeExercice {

    private static FacadeExercicePostgreSQL instance = null;
    protected FacadeExercicePostgreSQL(){

    }

    public static FacadeExercicePostgreSQL getInstance(){
        if (instance == null){
            instance = new FacadeExercicePostgreSQL();
        }
        return instance;
    }
}
