package com.fitj.controllers.headers;

import com.fitj.exceptions.BadPageException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controller pour le header de la page coach
 * @see ControllerHeader
 * @author Romain Frezier
 */
public class ControllerHeaderCoach extends ControllerHeader {

    /**
     * Chemin caractérisant la page coach
     */
    private final String path = "coach";

    // Composants FXML ----------------------------------------------
    @FXML
    private Button monCompte;
    @FXML
    private Button coachs;
    @FXML
    private Button monEspace;
    @FXML
    private Button shop;
    @FXML
    private Button mesClients;
    // --------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement du header
     */
    @FXML
    private void initialize() {
        super.setPath(path);
    }

    /**
     * Methode permettant de se rendre sur la page mon compte
     * @throws BadPageException si la vue n'existe pas
     */
    @FXML
    private void goToMonCompte() throws BadPageException {
        super.goToMonCompte(monCompte);
    }

    /**
     * Methode permettant de se rendre sur la page coachs
     * @throws BadPageException si la vue n'existe pas
     */
    @FXML
    private void goToCoachs() throws BadPageException {
        super.goToCoachs(coachs);
    }

    /**
     * Methode permettant de se rendre sur la page mon espace
     * @throws BadPageException si la vue n'existe pas
     */
    @FXML
    private void goToMonEspace() throws BadPageException {
        super.goToMonEspace(monEspace);
    }

    /**
     * Methode permettant de se rendre sur la page shop
     * @throws BadPageException si la vue n'existe pas
     */
    @FXML
    private void goToShop() throws BadPageException {
        super.goToShop(shop);
    }

    /**
     * Methode permettant de se rendre sur la page des clients du coach
     * @throws BadPageException si la vue n'existe pas
     */
    @FXML
    private void goToMesClients() throws BadPageException {
        super.goToPage(mesClients, path + "s/mesClients-" + path + ".fxml", "Mes Clients");
    }
}
