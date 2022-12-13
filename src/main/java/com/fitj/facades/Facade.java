package com.fitj.facades;

import com.fitj.controllers.Controller;
import com.fitj.models.FactoryModel;
import com.fitj.models.Model;

/**
 * Facade générique
 */
public abstract class Facade {

    /**
     * Modèle de la facade
     * @see Model
     */
    protected Model model;

    /**
     * Controlleur de la facade
     * @see Controller
     */
    protected Controller controller;

    /**
     * Fabrique de modèle, permet de récupérer le modèle correspondant à la facade
     * @see FactoryModel
     */
    protected FactoryModel factoryModel = FactoryModel.getInstance();
}
