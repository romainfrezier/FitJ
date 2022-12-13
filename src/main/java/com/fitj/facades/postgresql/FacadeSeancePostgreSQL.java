package com.fitj.facades.postgresql;

import com.fitj.facades.FacadeSeance;

public class FacadeSeancePostgreSQL extends FacadeSeance {

    private static FacadeSeancePostgreSQL instance = null;
    protected FacadeSeancePostgreSQL(){

    }

    public static FacadeSeancePostgreSQL getInstance(){
        if (instance == null){
            instance = new FacadeSeancePostgreSQL();
        }
        return instance;
    }
}
