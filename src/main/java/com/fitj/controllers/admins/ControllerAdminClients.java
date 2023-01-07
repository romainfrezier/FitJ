package com.fitj.controllers.admins;

import com.fitj.classes.Client;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Optional;

/**
 * Classe qui gère la vue de la liste des clients
 * @author Romain Frezier
 */
public class ControllerAdminClients extends ControllerAdmin {


    // Composants de la vue -----------------------------------------------
    @FXML
    private ListView<Client> listView;
    @FXML
    private Button setToCoach;
    @FXML
    private Button seeMoreButton;
    @FXML
    private Button setToAdmin;
    @FXML
    private Button banButton;
    @FXML
    private Text errorText;
    // --------------------------------------------------------------------

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
     * Méthode permettant d'initialiser la liste des clients
     */
    private void initializeClientList() {
        try {
            clients = adminFacade.getAllClients();
            super.initializeClientList(listView, clients);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode réagissant au clic sur le bouton "Nouvel admin"
     */
    @FXML
    private void handleAdminPassage() {
        try {
            hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            showConfirmationAdminPassage();
        } catch (UnselectedItemException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode réagissant au clic sur le bouton "Nouveau coach"
     */
    @FXML
    private void handleCoachPassage() {
        try {
            hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            showConfirmationCoachPassage();
        } catch (UnselectedItemException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode réagissant au clic sur le bouton "Voir plus"
     */
    @FXML
    private void handleSeeMoreButton() {
        super.displayError(errorText, "Fonctionnalité non implémentée");
    }

    /**
     * Méthode permettant d'afficher une fenêtre de confirmation pour le passage en admin
     */
    private void showConfirmationAdminPassage() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Passage en admin");
        alert.setHeaderText("Vous êtes sûr de vouloir rendre cet utilisateur admin ?");
        alert.setContentText("Vous ne pourrez pas revenir en arrière");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK){
            try {
                Facade.currentClient = adminFacade.clientBecomeAdmin(getIdObjectSelected());
            } catch (Exception e) {
                super.displayError(errorText, e.getMessage());
            }
        }
    }

    /**
     * Méthode permettant d'afficher une fenêtre de confirmation pour le passage en coach
     */
    private void showConfirmationCoachPassage() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Passage en coach");
        alert.setHeaderText("Vous êtes sûr de vouloir rendre cet utilisateur coach ?");
        alert.setContentText("Vous ne pourrez pas revenir en arrière");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK){
            try {
                Facade.currentClient = adminFacade.clientBecomeCoach(getIdObjectSelected());
            } catch (Exception e) {
                super.displayError(errorText, e.getMessage());
            }
        }
    }

    /**
     * Méthode permettant de vérifier si un élément est sélectionné dans la liste
     * @throws UnselectedItemException levée si aucun élément n'est sélectionné
     */
    private void checkSelected() throws UnselectedItemException {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Vous devez sélectionner un client");
        }
    }

    /**
     * Méthode réagissant au clic sur le bouton "Bannir".
     */
    @FXML
    private void handleBan() {
        try {
            hideError(errorText);
            checkSelected();
            showConfirmationBan();
        } catch (UnselectedItemException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode permettant d'afficher une fenêtre de confirmation pour le bannissement
     */
    private void showConfirmationBan() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bannir ce client");
        alert.setHeaderText("Vous êtes sûr de vouloir bannir cet utilisateur ?");
        alert.setContentText("Vous pourrez modifier ça plus tard");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK){
            try {
                Client clientBan = adminFacade.banClient(getIdObjectSelected());
                listView.getItems().set(listView.getSelectionModel().getSelectedIndex(), clientBan);
                listView.refresh();
                Optional<Client> result = clients.stream()
                        .filter(client -> client.getId() == getIdObjectSelected())
                        .findFirst();
                if (result.isPresent()){
                    Client client = result.get();
                    client.setBanni();
                    clients.set(clients.indexOf(client), client);
                }
            } catch (Exception e) {
                e.printStackTrace();
                super.displayError(errorText, e.getMessage());
            }
        }
    }


    /**
     * Méthode qui met à jour le texte du bouton "Bannir" en fonction de l'état de bannissement du client sélectionné.
     */
    public void setTextBanButton() {
        super.hideError(errorText);
        setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
        try {
            Optional<Client> result = clients.stream()
                    .filter(client -> client.getId() == getIdObjectSelected())
                    .findFirst();
            if (result.isPresent()){
                Client client = result.get();
                if (client.isBanni()){
                    banButton.setText("Débannir");
                } else {
                    banButton.setText("Bannir");
                }
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }
}
