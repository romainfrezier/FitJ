package com.fitj.controllers.commandes;

import com.fitj.controllers.Controller;
import com.fitj.facades.FacadeCommande;

public abstract class ControllerCommande extends Controller {

    FacadeCommande facadeCommande = FacadeCommande.getInstance();

}