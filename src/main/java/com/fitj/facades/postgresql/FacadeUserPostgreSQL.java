package com.fitj.facades.postgresql;

import com.fitj.facades.FacadeUser;

public class FacadeUserPostgreSQL extends FacadeUser {


    private static FacadeUserPostgreSQL instance = null;
    protected FacadeUserPostgreSQL(){

    }

    public static FacadeUserPostgreSQL getInstance(){
        if (instance == null){
            instance = new FacadeUserPostgreSQL();
        }
        return instance;
    }
}
