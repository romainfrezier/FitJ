package com.fitj.controllers.coachs;

import com.fitj.classes.Client;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.util.List;

/**
 * Controller de la page des clients du coach
 * @see ControllerCoach
 * @author Romain Frezier
 */
public class ControllerCoachMesClients extends ControllerCoach {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private ListView<Client> listView;
    @FXML
    private Button seeMoreButton;
    @FXML
    private Text errorText;
    // ---------------------------------------------------------------------------------------------------------------

    private List<Client> clients;

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        initializeClientList();
    }

    /**
     * Methode permettant d'initialiser la liste des clients
     */
    private void initializeClientList() {
        try {
            clients = coachFacade.getAllClientsForACoach(Facade.currentClient.getId());
            super.initializeClientList(listView, clients);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode pour selectionner un client
     */
    @FXML
    private void selectClient() {
        setObjectSelected(listView.getSelectionModel().getSelectedItem());
    }

    /**
     * Méthode réagissant au clic sur le bouton "Voir plus"
     */
    @FXML
    private void handleSeeMoreButton() {
        try {
            hideError(errorText);
            checkSelected();
            setPreviousPageName("list-mesClient");
            goToPage(seeMoreButton, "coachs/detailClient-coach.fxml", "Detail");
        } catch (Exception e) {
            displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode qui verifie si un client est selectionné
     */
    private void checkSelected() throws UnselectedItemException {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Vous devez sélectionner un client");
        }
    }
}
