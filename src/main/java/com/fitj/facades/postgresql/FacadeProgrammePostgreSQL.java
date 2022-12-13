package com.fitj.facades.postgresql;

import com.fitj.facades.FacadeProgramme;

public class FacadeProgrammePostgreSQL extends FacadeProgramme {

    private static FacadeProgrammePostgreSQL instance = null;
    protected FacadeProgrammePostgreSQL(){

    }

    public static FacadeProgrammePostgreSQL getInstance(){
        if (instance == null){
            instance = new FacadeProgrammePostgreSQL();
        }
        return instance;
    }
}
