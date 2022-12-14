package com.fitj.facades.postgresql;

import com.fitj.facades.FacadeClient;

public class FacadeClientPostgreSQL extends FacadeClient {

    private static FacadeClientPostgreSQL instance = null;
    protected FacadeClientPostgreSQL(){

    }

    public static FacadeClientPostgreSQL getInstance(){
        if (instance == null){
            instance = new FacadeClientPostgreSQL();
        }
        return instance;
    }
}
