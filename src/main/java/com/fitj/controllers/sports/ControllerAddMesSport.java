package com.fitj.controllers.sports;

import com.fitj.classes.Sport;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.FacadeClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.util.List;

import static com.fitj.facades.Facade.currentClient;

/**
 * Controller de la page d'ajout d'un sport
 * @see ControllerSport
 * @author Paco Munarriz
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
            super.initializeSportList(listView, sports);
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
    private void AddSport() {
        FacadeClient facadeClient = FacadeClient.getInstance();
            try {
                hideError(errorText);
                checkSelected();
                setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
                facadeClient.addSportToClient(currentClient.getId(),getIdObjectSelected());
                super.goToMonEspace(addSportButton);
            } catch (UnselectedItemException e) {
                super.displayError(errorText, e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

}
