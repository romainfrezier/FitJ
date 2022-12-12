package com.fitj.facades.postgresql;

import com.fitj.facades.FacadeRecette;

public class FacadeRecettePostgreSQL extends FacadeRecette {

    private static FacadeRecettePostgreSQL instance = null;
    protected FacadeRecettePostgreSQL(){

    }

    public static FacadeRecettePostgreSQL getInstance(){
        if (instance == null){
            instance = new FacadeRecettePostgreSQL();
        }
        return instance;
    }
}
