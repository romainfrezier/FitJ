package com.fitj.controllers.clients;

import com.fitj.controllers.Controller;
import com.fitj.facades.FacadeClient;
import javafx.scene.control.Control;
import java.io.IOException;

/**
 * Controller générique pour les pages accessibles aux clients
 * @see Controller
 * @author Paco Munnariz
 */
public abstract class ControllerClient extends Controller {

    /**
     * Facade pour les clients
     */
    final FacadeClient clientFacade = FacadeClient.getInstance();

    /**
     * Methode permettant de se rendre sur la page mon compte
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'est pas trouvée
     */
    void goToMonCompte(Control controlEl) throws IOException {
        goToPage(controlEl, "monCompte-client.fxml", "MonCompte");
    }

    /**
     * Methode permettant de se rendre sur la page coachs
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToCoachs(Control controlEl) throws IOException {
        goToPage(controlEl, "coachs-client.fxml", "Coachs");
    }

    /**
     * Methode permettant de se rendre sur la page mon espace
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToMonEspace(Control controlEl) throws IOException {
        goToPage(controlEl, "monEspace-client.fxml", "MonEspace");
    }

    /**
     * Methode permettant de se rendre sur la page shop
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToShop(Control controlEl) throws IOException {
        goToPage(controlEl, "shop-client.fxml", "Shop");
    }
}
