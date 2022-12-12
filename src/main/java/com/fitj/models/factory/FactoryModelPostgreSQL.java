package com.fitj.models.factory;

import com.fitj.models.factory.FactoryModel;
import com.fitj.models.postgresql.ModelClientPostgreSQL;

public class FactoryModelPostgreSQL extends FactoryModel {

    public FactoryModelPostgreSQL(){
        super();

    }

    public ModelClientPostgreSQL getModelClient(){
        return new ModelClientPostgreSQL();
    }



}
