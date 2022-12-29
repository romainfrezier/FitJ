package com.fitj.controllers.admins;

import com.fitj.classes.Client;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.List;
import java.util.Optional;

/**
 * Classe qui gère la vue de la liste des clients
 * @author Romain Frezier
 */
public class ControllerClients extends ControllerAdmin {

    // Composants de la vue -----------------------------------------------
    @FXML
    private Button setToAdmin;
    @FXML
    private ListView<Client> listView;
    @FXML
    private Text errorMessage;
    @FXML
    private Button setToCoach;
    @FXML
    private Text errorText;
    // --------------------------------------------------------------------

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
            List<Client> clients = adminFacade.getAllClients();
            listView.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Client> call(ListView<Client> o) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Client item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                setText(item.getId() + ". " + item.getPseudo());
                            } else {
                                setText("");
                            }
                        }
                    };
                }
            });
            for (Client client : clients) {
                listView.getItems().add(client);
            }
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
            throw new UnselectedItemException("Vous devez selectionner un sport");
        }
    }


}
