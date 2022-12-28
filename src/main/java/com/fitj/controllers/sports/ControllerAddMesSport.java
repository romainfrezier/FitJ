package com.fitj.controllers.sports;

import com.fitj.classes.Sport;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.FacadeClient;
import com.fitj.facades.FacadeSport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.List;

import static com.fitj.facades.Facade.currentClient;

/**
 * Controller de la page d'ajout d'un sport
 * @see ControllerSport
 * @author Romain Frezier
 */
public class ControllerAddMesSport extends ControllerSport {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private ListView<Sport> listView;
    @FXML
    private Button addSportButton;
    @FXML
    private Text errorText;
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        initializeSportList();
    }

    /**
     * Methode permettant d'initialiser la liste des sports
     */
    private void initializeSportList() {
        try {
            List<Sport> sports = sportFacade.getAllSports();
            listView.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Sport> call(ListView<Sport> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Sport item, boolean empty) {
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
            for (Sport sport : sports) {
                listView.getItems().add(sport);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant de verifier si un sport est selectionné
     * @throws UnselectedItemException si aucun sport n'est selectionné
     */
    private void checkSelected() throws UnselectedItemException {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Vous devez selectionner un sport");
        }
    }

    /**
     * Méthode appelée lors du clic sur le bouton "Ajouter". Ajoute le sport
     */
    @FXML
    private void addSport() {
            try {
                hideError(errorText);
                checkSelected();
                setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
                FacadeClient.getInstance().addSportToClient(currentClient.getId(),getIdObjectSelected());
                super.goToMonEspace(addSportButton);
            } catch (UnselectedItemException e) {
                super.displayError(errorText, e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

}
