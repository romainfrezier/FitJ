package com.fitj.controllers.clients;

import com.fitj.controllers.Controller;
import com.fitj.facades.FacadeClient;

/**
 * Controller générique pour les pages accessibles aux clients
 * @see Controller
 * @author Paco Munnariz, Romain Frezier
 */
public abstract class ControllerClient extends Controller {

    /**
     * Facade pour les clients
     */
    final FacadeClient clientFacade = FacadeClient.getInstance();
}
