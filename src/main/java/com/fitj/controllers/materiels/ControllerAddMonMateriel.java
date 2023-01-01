package com.fitj.controllers.materiels;

import com.fitj.classes.Materiel;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.FacadeClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.util.List;

import static com.fitj.facades.Facade.currentClient;

/**
 * Controller de la page d'ajout d'un materiel
 * @see ControllerMateriel
 * @author Paco Munarriz
 */
public class ControllerAddMonMateriel extends ControllerMateriel {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private ListView<Materiel> listView;
    @FXML
    private Button addMaterielButton;
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
     * Methode permettant d'initialiser la liste des materiels
     */
    private void initializeMaterielList() {
        try {
            List<Materiel> materiels = materielFacade.getAllMateriels();
            super.initializeMaterielList(listView, materiels);
        } catch (Exception e) {
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
     * Méthode appelée lors du clic sur le bouton "Ajouter". Ajoute le materiel
     */
    @FXML
    private void AddMateriel() {
        FacadeClient facadeClient = FacadeClient.getInstance();
        try {
            hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            facadeClient.addMaterielToClient(currentClient.getId(),getIdObjectSelected());
            super.goToMonEspace(addMaterielButton);
        } catch (UnselectedItemException e) {
            super.displayError(errorText, e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
