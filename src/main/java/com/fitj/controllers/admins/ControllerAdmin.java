package com.fitj.controllers.admins;

import com.fitj.controllers.Controller;
import com.fitj.facades.FacadeAdmin;
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
    final FacadeAdmin adminFacade = FacadeAdmin.getInstance();

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages accessibles aux admins
     */
    private final String path = "admins/";

    /**
     * Methode permettant de se rendre sur la page mon compte
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'est pas trouvée
     */
    void goToMonCompte(Control controlEl) throws IOException {
        goToPage(controlEl, path + "monCompte-admin.fxml", "MonCompte");
    }

    /**
     * Methode permettant de se rendre sur la page coachs
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToCoachs(Control controlEl) throws IOException {
        goToPage(controlEl, path + "coachs-admin.fxml", "Coachs");
    }

    /**
     * Methode permettant de se rendre sur la page mon espace
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToMonEspace(Control controlEl) throws IOException {
        goToPage(controlEl, path + "monEspace-admin.fxml", "MonEspace");
    }

    /**
     * Methode permettant de se rendre sur la page shop
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToShop(Control controlEl) throws IOException {
        goToPage(controlEl, path + "shop-admin.fxml", "Shop");
    }

    /**
     * Methode permettant de se rendre sur la page clients
     * @param controlEl Control, élément de contrôle de la page
     * @throws IOException si la page n'existe pas
     */
    void goToClients(Control controlEl) throws IOException {
        goToPage(controlEl, path + "clients-admin.fxml", "Clients");
    }
}
