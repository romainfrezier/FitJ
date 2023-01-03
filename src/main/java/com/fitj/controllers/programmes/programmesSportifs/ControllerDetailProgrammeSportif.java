package com.fitj.controllers.programmes.programmesSportifs;

import com.fitj.classes.*;
import com.fitj.enums.ProgrammeType;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControllerDetailProgrammeSportif extends ControllerProgrammeSportif{

    @FXML
    private Button detailSeanceButton;

    @FXML
    private Button deleteProgrammeSportifButton;

    @FXML
    private Button updateProgrammeSportifButton;

    @FXML
    private ListView<Seance> listViewSeanceProgrammeSportif;

    @FXML
    private Text nbMoisProgramme;

    @FXML
    private TextFlow descriptionProgramme;

    @FXML
    private Text programmeType;

    @FXML
    private Text nomCoach;


    @FXML
    private VBox headerAdmin;

    @FXML
    private VBox headerClient;

    @FXML
    private VBox headerCoach;

    @FXML
    private Text nomProgrammeSportif;

    @FXML
    private Text errorText;

    private List<Seance> listeSeances = new ArrayList<>();

    private ProgrammeSportif programmeSportif;

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        if (Facade.currentClient instanceof Admin) {
            headerAdmin.setVisible(true);
        } else if (Facade.currentClient instanceof Coach){
            headerCoach.setVisible(true);
        }
        else {
            headerClient.setVisible(true);
        }
        detailSeanceButton.setVisible(false);
        try {
            this.programmeSportif = facadeProgrammeSportif.getProgrammeSportifById(getIdObjectSelected());
            checkCurrentCoach();
            this.listeSeances.addAll(programmeSportif.getListeSeance());
            this.nomProgrammeSportif.setText(this.programmeSportif.getNom());
            this.nomCoach.setText(this.programmeSportif.getCoach().getPseudo());
            this.programmeType.setText(ProgrammeType.getProgrammeType(this.programmeSportif.getType()));
            this.nbMoisProgramme.setText(this.programmeSportif.getNbMois() + " mois");
            this.descriptionProgramme.getChildren().add(new Text(this.programmeSportif.getDescription()));
        }
        catch (Exception e){
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
        setObjectSelected(null);
        initializeSeanceList();

    }

    /**
     * Methode permettant d'initialiser la liste des séances présentes dans le programme sportif
     */
    private void initializeSeanceList() {
        listViewSeanceProgrammeSportif.getItems().clear();
        try {
            super.initializeSeanceList(listViewSeanceProgrammeSportif, listeSeances);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    public void checkCurrentCoach(){
        if (!(Facade.currentClient instanceof Admin) && this.programmeSportif.getCoach().getId() != Facade.currentClient.getId()){
            updateProgrammeSportifButton.setVisible(false);
            deleteProgrammeSportifButton.setVisible(false);
        }
    }

    /**
     * Méthode permettant de supprimer le programme sportif actuel
     */
    @FXML
    private void deleteProgrammeSportif(){
        try {
            showConfirmationDeleteProgrammeSportif();
        }
        catch (Exception e){
            super.displayError(errorText, e.getMessage());
        }
    }


    /**
     * Permet d'aller sur la page de modification du programme sportif actuel
     */
    @FXML
    private void updateProgrammeSportif() {
        try {
            setIdObjectSelected(this.programmeSportif.getId());
            this.goToUpdateProgrammeSportif(updateProgrammeSportifButton);
        }
        catch (Exception e){
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Permet d'aller sur la page de détail de la séance cliqué dans la liste des séances du programme sportif
     */
    @FXML
    private void goToDetailSeance() {
        try {
            setIdObjectSelected(this.listViewSeanceProgrammeSportif.getSelectionModel().getSelectedItem().getId());
            this.goToDetailSeance(detailSeanceButton);
        }
        catch (Exception e){
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Permet d'afficher le bouton détail de la séance que lorsqu'il s'agit d'une séance de sélectionnée dans la liste
     */
    @FXML
    private void handlerViewSeance(){
        setIdObjectSelected(listViewSeanceProgrammeSportif.getSelectionModel().getSelectedItem().getId());
        this.detailSeanceButton.setVisible(true);
    }



    /**
     * Méthode permettant d'afficher une fenêtre de confirmation de suppression d'un programme sportif
     */
    private void showConfirmationDeleteProgrammeSportif() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete programme sportif");
        alert.setHeaderText("Vous êtes sûr de vouloir supprimer ce programme sportif " + programmeSportif.getNom() + " ?");
        alert.setContentText("Vous ne pourrez pas revenir en arrière");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK){
            try {
                facadeProgrammeSportif.deleteProgrammeSportif(programmeSportif.getId());
                setObjectSelected(null);
                this.goToMonEspace(deleteProgrammeSportifButton);
            } catch (Exception e) {
                super.displayError(errorText, e.getMessage());
            }
        }

    }

}
