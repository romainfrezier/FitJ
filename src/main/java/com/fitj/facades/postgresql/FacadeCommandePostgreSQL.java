package com.fitj.facades.postgresql;

import com.fitj.facades.FacadeCommande;

public class FacadeCommandePostgreSQL extends FacadeCommande {

    private static FacadeCommandePostgreSQL instance = null;
    protected FacadeCommandePostgreSQL(){

    }

    public static FacadeCommandePostgreSQL getInstance(){
        if (instance == null){
            instance = new FacadeCommandePostgreSQL();
        }
        return instance;
    }
}
