package com.fitj.controllers.commandes;

import com.fitj.controllers.Controller;
import com.fitj.facades.FacadeCommande;

/**
 * Controller pour la page de commande
 * @see Controller
 * @author Romain Frezier
 */
public abstract class ControllerCommande extends Controller {

    /**
     * Implémentation de la facade de commande
     */
    FacadeCommande facadeCommande = FacadeCommande.getInstance();



}