package com.fitj.facades.postgresql;

import com.fitj.facades.FacadeDemande;

public class FacadeDemandePostgreSQL extends FacadeDemande {

    private static FacadeDemandePostgreSQL instance = null;
    protected FacadeDemandePostgreSQL(){

    }

    public static FacadeDemandePostgreSQL getInstance(){
        if (instance == null){
            instance = new FacadeDemandePostgreSQL();
        }
        return instance;
    }
}
