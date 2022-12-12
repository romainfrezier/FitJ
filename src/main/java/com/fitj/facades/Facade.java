package com.fitj.facades;

import com.fitj.controllers.Controller;
import com.fitj.models.FactoryModel;
import com.fitj.models.Model;

public abstract class Facade {

    protected Model model;
    protected Controller controller;

    protected FactoryModel factoryModel = FactoryModel.getInstance();


}
