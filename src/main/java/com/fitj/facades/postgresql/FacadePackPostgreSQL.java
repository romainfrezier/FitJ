package com.fitj.facades.postgresql;

import com.fitj.facades.FacadePack;

public class FacadePackPostgreSQL extends FacadePack {


    private static FacadePackPostgreSQL instance = null;
    protected FacadePackPostgreSQL(){

    }

    public static FacadePackPostgreSQL getInstance(){
        if (instance == null){
            instance = new FacadePackPostgreSQL();
        }
        return instance;
    }
}
