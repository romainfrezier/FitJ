package com.fitj.models;


import com.fitj.models.methodesBD.MethodesBD;

public abstract class Model {
    protected String table;

    protected MethodesBD methodesBD;

    public Model(String table){
        this.table = table;
    }


}
