package com.fitj.controllers.coachs;

import com.fitj.controllers.Controller;
import com.fitj.facades.FacadeCoach;
import javafx.scene.control.Control;

import java.io.IOException;

/**
 * Controller générique pour les pages accessibles aux coachs
 * @see Controller
 * @author Paco Munnariz
 */
public abstract class ControllerCoach extends Controller {

    /**
     * Facade pour les coachs
     */
    final FacadeCoach coachFacade = FacadeCoach.getInstance();

    /**
     * Methode permettant de se rendre sur la page mon compte
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'est pas trouvée
     */
    void goToMonCompte(Control controlEl) throws IOException {
        goToPage(controlEl, "monCompte-coach.fxml", "MonCompte");
    }

    /**
     * Methode permettant de se rendre sur la page coachs
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToCoachs(Control controlEl) throws IOException {
        goToPage(controlEl, "coachs-coach.fxml", "Coachs");
    }

    /**
     * Methode permettant de se rendre sur la page mon espace
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToMonEspace(Control controlEl) throws IOException {
        goToPage(controlEl, "monEspace-coach.fxml", "MonEspace");
    }

    /**
     * Methode permettant de se rendre sur la page shop
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToShop(Control controlEl) throws IOException {
        goToPage(controlEl, "shop-coach.fxml", "Shop");
    }

    /**
     * Methode permettant de se rendre sur la page mes clients
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToMesClients(Control controlEl) throws IOException {
        goToPage(controlEl, "mesClients-coach.fxml", "Mesclients");
    }
}
