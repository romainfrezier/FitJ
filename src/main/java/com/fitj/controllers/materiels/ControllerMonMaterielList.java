package com.fitj.controllers.materiels;

import com.fitj.classes.Materiel;
import com.fitj.exceptions.BadPageException;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.FacadeClient;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.List;
import java.util.Optional;

import static com.fitj.facades.Facade.currentClient;

/**
 * Controller de la page materiel-list-view.fxml
 * @see ControllerMateriel
 * @author Paco Munarriz
 */
public class ControllerMonMaterielList extends ControllerMateriel {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private Button addMaterielButton;
    @FXML
    private ListView<Materiel> listView;

    @FXML
    private Button deleteMaterielbutton;

    @FXML
    private Text errorText;


    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        initializeMaterielList();

    }

    /**
     * Methode permettant d'initialiser la liste du materiels
     */
    private void initializeMaterielList() {
        try {
            List<Materiel> materiels = materielFacade.getMaterielByIdClient(currentClient.getId());
            listView.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Materiel> call(ListView<Materiel> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Materiel item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                setText(item.getNom());
                            } else {
                                setText("");
                            }
                        }
                    };
                }
            });
            for (Materiel materiel : materiels) {
                listView.getItems().add(materiel);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de se rendre sur la page d'ajout d'un materiel
     */
    @FXML
    private void goToAddMyMateriel() {
        try {
            super.hideError(errorText);
            super.goToAddMyMateriel(addMaterielButton);
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }


    /**
     * Methode permettant de supprimer un materiel
     */
    @FXML
    private void deleteMateriel() {
        try {
            hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            showConfirmationDeleteMateriel();
        } catch (UnselectedItemException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de verifier si un materiel est selectionné
     * @throws UnselectedItemException si aucun materiel n'est selectionné
     */
    private void checkSelected() throws UnselectedItemException {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Vous devez selectionner un materiel");
        }
    }

    /**
     * Méthode permettant d'afficher une fenêtre de confirmation de suppression d'un materiel
     */
    private void showConfirmationDeleteMateriel() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Materiel");
        alert.setHeaderText("Vous êtes sûr de vouloir supprimer ce materiel ?");
        alert.setContentText("Vous ne pourrez pas revenir en arrière");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK){
            FacadeClient facadeClient = FacadeClient.getInstance();
            try {
                facadeClient.deleteMaterielToClient(currentClient.getId(), getIdObjectSelected());
                listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
            } catch (Exception e) {
                super.displayError(errorText, e.getMessage());
            }
        }

    }
}