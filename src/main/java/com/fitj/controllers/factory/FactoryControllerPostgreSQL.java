package com.fitj.controllers.factory;

import com.fitj.controllers.ControllerClientPostgreSQL;

public class FactoryControllerPostgreSQL extends FactoryController {


    @Override
    public ControllerClientPostgreSQL getControllerClient() {
        return ControllerClientPostgreSQL.getInstance();
    }

}
