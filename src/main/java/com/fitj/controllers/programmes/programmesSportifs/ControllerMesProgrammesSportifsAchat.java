package com.fitj.controllers.programmes.programmesSportifs;

import com.fitj.classes.Client;
import com.fitj.classes.ProgrammeSportif;
import com.fitj.exceptions.BadPageException;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Optional;

public class ControllerMesProgrammesSportifsAchat extends ControllerProgrammeSportif{

    @FXML
    private Button detailProgrammeSportifButton;
    @FXML
    private ListView<ProgrammeSportif> listView;
    @FXML
    private Text errorText;

    private List<ProgrammeSportif> programmeSportifs;

    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        try {
            programmeSportifs = facadeProgrammeSportif.getAllProgrammesSportifsByClient(Facade.currentClient.getId());
            initializeProgrammeSportifList();
        }
        catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant d'initialiser la liste des programmes sportifs
     */
    private void initializeProgrammeSportifList() {
        try {

            super.initializeProgrammeSportifList(listView, programmeSportifs);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }


    /**
     * Méthode appelée lors du clic sur un programme sportif de la liste
     */
    @FXML
    private void selectItem(){
        setObjectSelected(listView.getSelectionModel().getSelectedItem());
    }




    /**
     * Methode permettant de se rendre sur la page de modification d'un programme sportif
     */
    @FXML
    private void goToDetailProgrammeSportif() {
        try {
            super.hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            super.goToDetailProgrammeSportif(detailProgrammeSportifButton);
        } catch (BadPageException | UnselectedItemException e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }



    /**
     * Methode permettant de verifier si un programme sportif est selectionné
     * @throws UnselectedItemException si aucun programme sportif n'est selectionné
     */
    private void checkSelected() throws UnselectedItemException {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Vous devez sélectionner un programme sportif");
        }
    }




}
