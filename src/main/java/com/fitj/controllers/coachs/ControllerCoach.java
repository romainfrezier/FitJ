package com.fitj.controllers.coachs;

import com.fitj.controllers.Controller;
import com.fitj.facades.FacadeCoach;

/**
 * Controller générique pour les pages accessibles aux coachs
 * @see Controller
 * @author Paco Munnariz, Romain Frezier
 */
public abstract class ControllerCoach extends Controller {

    /**
     * Facade pour les coachs
     */
    final FacadeCoach coachFacade = FacadeCoach.getInstance();
}
