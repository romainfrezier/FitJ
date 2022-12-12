package com.fitj.models;

public class FactoryModelPostgreSQL extends FactoryModel{

    public FactoryModelPostgreSQL(){
        super();

    }

    public ModelClientPostgreSQL getModelClient(){
        return new ModelClientPostgreSQL();
    }



}
