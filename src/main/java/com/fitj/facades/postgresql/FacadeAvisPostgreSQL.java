package com.fitj.facades.postgresql;

import com.fitj.facades.FacadeAvis;

public class FacadeAvisPostgreSQL extends FacadeAvis {

    private static FacadeAvisPostgreSQL instance = null;
    protected FacadeAvisPostgreSQL(){

    }

    public static FacadeAvisPostgreSQL getInstance(){
        if (instance == null){
            instance = new FacadeAvisPostgreSQL();
        }
        return instance;
    }
}
