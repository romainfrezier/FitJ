package com.fitj.controllers.admins;

import com.fitj.controllers.Controller;
import com.fitj.facades.FacadeAdmin;

/**
 * Controller générique pour les pages accessibles aux admins
 * @see Controller
 * @author Paco Munnariz, Romain Frezier
 */
public abstract class ControllerAdmin extends Controller {

    /**
     * Facade pour les admins
     */
    final FacadeAdmin adminFacade = FacadeAdmin.getInstance();

}
