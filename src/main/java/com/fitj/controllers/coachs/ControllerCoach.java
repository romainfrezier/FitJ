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
     * Chemin du dossier dans lequel se trouve les ressources pour les pages accessibles aux coachs
     */
    private final String path = "coachs/";

    /**
     * Methode permettant de se rendre sur la page mon compte
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'est pas trouvée
     */
    void goToMonCompte(Control controlEl) throws IOException {
        goToPage(controlEl, path + "monCompte-coach.fxml", "Mon Compte");
    }

    /**
     * Methode permettant de se rendre sur la page coachs
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToCoachs(Control controlEl) throws IOException {
        goToPage(controlEl, path + "coachs-coach.fxml", "Coachs");
    }

    /**
     * Methode permettant de se rendre sur la page mon espace
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToMonEspace(Control controlEl) throws IOException {
        goToPage(controlEl, path + "monEspace-coach.fxml", "Mon Espace");
    }

    /**
     * Methode permettant de se rendre sur la page shop
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToShop(Control controlEl) throws IOException {
        goToPage(controlEl, path + "shop-coach.fxml", "Shop");
    }

    /**
     * Methode permettant de se rendre sur la page mes clients
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToMesClients(Control controlEl) throws IOException {
        goToPage(controlEl, path + "mesClients-coach.fxml", "Mes Clients");
    }
}
