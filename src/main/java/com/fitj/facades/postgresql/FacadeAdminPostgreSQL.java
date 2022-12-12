package com.fitj.facades.postgresql;

import com.fitj.facades.FacadeAdmin;

public class FacadeAdminPostgreSQL extends FacadeAdmin {

    private static FacadeAdminPostgreSQL instance = null;
    protected FacadeAdminPostgreSQL(){

    }

    public static FacadeAdminPostgreSQL getInstance(){
        if (instance == null){
            instance = new FacadeAdminPostgreSQL();
        }
        return instance;
    }
}
