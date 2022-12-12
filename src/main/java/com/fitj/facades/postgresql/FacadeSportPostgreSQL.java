package com.fitj.facades.postgresql;

import com.fitj.facades.FacadeSport;

public class FacadeSportPostgreSQL extends FacadeSport {


    private static FacadeSportPostgreSQL instance = null;
    protected FacadeSportPostgreSQL(){

    }

    public static FacadeSportPostgreSQL getInstance(){
        if (instance == null){
            instance = new FacadeSportPostgreSQL();
        }
        return instance;
    }
}
