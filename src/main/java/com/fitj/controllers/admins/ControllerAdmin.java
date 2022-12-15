package com.fitj.controllers.admins;

import com.fitj.controllers.Controller;
import com.fitj.facades.FacadeAdmin;
import com.fitj.facades.FacadeClient;
import com.fitj.facades.factory.FactoryFacade;
import javafx.scene.control.Control;

import java.io.IOException;

/**
 * Controller générique pour les pages accessibles aux admins
 * @see Controller
 * @author Paco Munnariz
 */
public abstract class ControllerAdmin extends Controller {

    /**
     * Facade pour les admins
     */
    final FacadeAdmin adminFacade = FactoryFacade.getInstance().getFacadeAdmin();

    /**
     * Methode permettant de se rendre sur la page mon compte
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'est pas trouvée
     */
    void goToMonCompte(Control controlEl) throws IOException {
        goToPage(controlEl, "monCompte-admin.fxml", "MonCompte");
    }

    /**
     * Methode permettant de se rendre sur la page coachs
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToCoachs(Control controlEl) throws IOException {
        goToPage(controlEl, "coachs-admin.fxml", "Coachs");
    }

    /**
     * Methode permettant de se rendre sur la page mon espace
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToMonEspace(Control controlEl) throws IOException {
        goToPage(controlEl, "monEspace-admin.fxml", "MonEspace");
    }

    /**
     * Methode permettant de se rendre sur la page shop
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToShop(Control controlEl) throws IOException {
        goToPage(controlEl, "shop-admin.fxml", "Shop");
    }

    /**
     * Methode permettant de se rendre sur la page clients
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToClients(Control controlEl) throws IOException {
        goToPage(controlEl, "clients-admin.fxml", "Clients");
    }
}
