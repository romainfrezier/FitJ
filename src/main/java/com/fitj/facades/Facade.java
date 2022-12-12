package com.fitj.facades;

import com.fitj.controllers.Controller;
import com.fitj.models.factory.FactoryModel;

public abstract class Facade {

    protected Controller controller;

    protected FactoryModel factoryModel = FactoryModel.getInstance();


}
