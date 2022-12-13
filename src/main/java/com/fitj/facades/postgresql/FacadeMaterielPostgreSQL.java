package com.fitj.facades.postgresql;

import com.fitj.facades.FacadeMateriel;

public class FacadeMaterielPostgreSQL extends FacadeMateriel {

    private static FacadeMaterielPostgreSQL instance = null;
    protected FacadeMaterielPostgreSQL(){

    }

    public static FacadeMaterielPostgreSQL getInstance(){
        if (instance == null){
            instance = new FacadeMaterielPostgreSQL();
        }
        return instance;
    }
}
