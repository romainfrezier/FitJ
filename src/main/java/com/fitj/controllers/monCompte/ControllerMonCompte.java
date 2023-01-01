package com.fitj.controllers.monCompte;

import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.FacadeMateriel;
import javafx.scene.control.Control;

/**
 * Controller générique des pages materiel
 */
public abstract class ControllerMonCompte extends Controller {

    /**
     * Methode permettant de se rendre sur la page upload
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToUpload(Control controlEl) throws BadPageException {
        goToPage(controlEl, "monCompte/update-monComte.fxml", "Upload");
    }

}


