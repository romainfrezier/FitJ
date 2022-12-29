package com.fitj.controllers.aliments;

import com.fitj.classes.Aliment;
import com.fitj.exceptions.BadPageException;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.FacadeAliment;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.List;
import java.util.Optional;

/**
 * Controller de la page aliment-list-view.fxml
 * @see ControllerAliment
 * @author Paco Munarriz
 */
public class ControllerAlimentList extends ControllerAliment {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private Button addAlimentButton;
    @FXML
    private ListView<Aliment> listView;
    @FXML
    private Button updateAlimentButton;
    @FXML
    private Button deleteAlimentbutton;
    @FXML
    private Text errorText;
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        initializeAlimentList();
    }

    /**
     * Methode permettant d'initialiser la liste des aliments
     */
    private void initializeAlimentList() {
        try {
            List<Aliment> aliments = alimentFacade.getAllAliments();
            listView.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Aliment> call(ListView<Aliment> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Aliment item, boolean empty) {
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
            for (Aliment aliment : aliments) {
                listView.getItems().add(aliment);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode appelée lors du clic sur un aliment de la liste
     */
    @FXML
    private void selectItem(){
        setObjectSelected(listView.getSelectionModel().getSelectedItem());
    }

    /**
     * Methode permettant de se rendre sur la page d'ajout d'un aliment
     */
    @FXML
    private void goToAddAliment() {
        try {
            super.hideError(errorText);
            super.goToAddAliment(addAlimentButton);
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de se rendre sur la page de modification d'un aliment
     */
    @FXML
    private void goToUpdateAliment() {
        try {
            super.hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            super.goToUpdateAliment(updateAlimentButton);
        } catch (BadPageException | UnselectedItemException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de supprimer un aliment
     */
    @FXML
    private void deleteAliment() {
        try {
            hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            showConfirmationDeleteAliment();
        } catch (UnselectedItemException e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de verifier si un aliment est selectionné
     * @throws UnselectedItemException si aucun aliment n'est selectionné
     */
    private void checkSelected() throws UnselectedItemException {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Vous devez selectionner un aliment");
        }
    }

    /**
     * Méthode permettant d'afficher une fenêtre de confirmation de suppression d'un aliment
     */
    private void showConfirmationDeleteAliment() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Aliment");
        alert.setHeaderText("Vous êtes sûr de vouloir supprimer ce aliment ?");
        alert.setContentText("Vous ne pourrez pas revenir en arrière");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK){
            FacadeAliment facadeAliment = FacadeAliment.getInstance();
            try {
                facadeAliment.deleteAliment(getIdObjectSelected());
                listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
            } catch (Exception e) {
                super.displayError(errorText, e.getMessage());
            }
        }

    }
}
