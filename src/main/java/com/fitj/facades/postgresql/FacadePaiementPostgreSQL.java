package com.fitj.facades.postgresql;

import com.fitj.facades.FacadePaiement;

public class FacadePaiementPostgreSQL extends FacadePaiement {

    private static FacadePaiementPostgreSQL instance = null;
    protected FacadePaiementPostgreSQL(){

    }

    public static FacadePaiementPostgreSQL getInstance(){
        if (instance == null){
            instance = new FacadePaiementPostgreSQL();
        }
        return instance;
    }
}
