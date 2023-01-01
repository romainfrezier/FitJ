package com.fitj.controllers.coachs;

import com.fitj.classes.Admin;
import com.fitj.classes.Coach;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Optional;

public class ControllerCoachList extends ControllerCoach {

    // Composants de la vue -----------------------------------------------
    @FXML
    private Button setToAdmin;
    @FXML
    private Button seeMoreButton;
    @FXML
    private ListView<Coach> listView;
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
        if (!(Facade.currentClient instanceof Admin)) {
            setToAdmin.setDisable(true);
        }
    }

    /**
     * Méthode permettant d'initialiser la liste des clients
     */
    private void initializeClientList() {
        try {
            List<Coach> coachs = coachFacade.getAllCoachs();
            initializeCoachList(listView, coachs);
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
                Facade.currentClient = coachFacade.clientBecomeAdmin(getIdObjectSelected());
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
            throw new UnselectedItemException("Vous devez sélectionner un coach");
        }
    }


}
