package com.fitj.controllers.programmes.programmesNutritions;

import com.fitj.classes.Admin;
import com.fitj.classes.Coach;
import com.fitj.classes.ProgrammeNutrition;
import com.fitj.classes.Recette;
import com.fitj.enums.ProgrammeType;
import com.fitj.facades.Facade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControllerDetailProgrammeNutrition extends ControllerProgrammeNutrition {


    @FXML
    private Button buyButton;
    @FXML
    private Button detailProgrammeNutritionButton;

    @FXML
    private Button deleteProgrammeNutritionButton;

    @FXML
    private Button updateProgrammeNutritionButton;

    @FXML
    private ListView<Recette> listViewRecetteProgrammeNutrition;

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
    private Text nomProgrammeNutrition;

    @FXML
    private Text errorText;

    private List<Recette> listeRecettes = new ArrayList<>();

    private ProgrammeNutrition programmeNutrition;

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
        detailProgrammeNutritionButton.setVisible(false);
        try {
            this.programmeNutrition = facadeProgrammeNutrition.getProgrammeNutritionById(getIdObjectSelected());
            checkCurrentCoach();
            this.listeRecettes.addAll(programmeNutrition.getListeRecette());
            this.nomProgrammeNutrition.setText(this.programmeNutrition.getNom());
            this.nomCoach.setText(this.programmeNutrition.getCoach().getPseudo());
            this.programmeType.setText(ProgrammeType.getProgrammeType(this.programmeNutrition.getType()));
            this.nbMoisProgramme.setText(this.programmeNutrition.getNbMois() + " mois");
            this.descriptionProgramme.getChildren().add(new Text(this.programmeNutrition.getDescription()));
        }
        catch (Exception e){
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
        setObjectSelected(null);
        initializeRecetteList();

    }

    /**
     * Methode permettant d'initialiser la liste des recettes présentes dans le programme nutrition
     */
    private void initializeRecetteList() {
        listViewRecetteProgrammeNutrition.getItems().clear();
        try {
            super.initializeRecetteList(listViewRecetteProgrammeNutrition, listeRecettes);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    public void checkCurrentCoach(){
        if (!(Facade.currentClient instanceof Admin) && this.programmeNutrition.getCoach().getId() != Facade.currentClient.getId()){
            updateProgrammeNutritionButton.setVisible(false);
            deleteProgrammeNutritionButton.setVisible(false);
            buyButton.setVisible(true);
        }
    }

    /**
     * Méthode permettant de supprimer le programme nutrition actuel
     */
    @FXML
    private void deleteProgrammeNutrition(){
        try {
            showConfirmationDeleteProgrammeNutrition();
        }
        catch (Exception e){
            super.displayError(errorText, e.getMessage());
        }
    }


    /**
     * Permet d'aller sur la page de modification du programme nutrition actuel
     */
    @FXML
    private void updateProgrammeNutrition() {
        try {
            setIdObjectSelected(this.programmeNutrition.getId());
            this.goToUpdateProgrammeNutrition(updateProgrammeNutritionButton);
        }
        catch (Exception e){
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Permet d'aller sur la page de détail de la recette cliqué dans la liste des recettes du programme nutrition
     */
    @FXML
    private void goToDetailRecette() {
        try {
            setIdObjectSelected(this.listViewRecetteProgrammeNutrition.getSelectionModel().getSelectedItem().getId());
            this.goToDetailRecette(detailProgrammeNutritionButton);
        }
        catch (Exception e){
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Permet d'afficher le bouton détail de la recette que lorsqu'il s'agit d'une recette de sélectionnée dans la liste
     */
    @FXML
    private void handlerViewRecette(){
            setIdObjectSelected(listViewRecetteProgrammeNutrition.getSelectionModel().getSelectedItem().getId());
            this.detailProgrammeNutritionButton.setVisible(true);
    }



    /**
     * Méthode permettant d'afficher une fenêtre de confirmation de suppression d'un programme nutrition
     */
    private void showConfirmationDeleteProgrammeNutrition() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Recette");
        alert.setHeaderText("Vous êtes sûr de vouloir supprimer ce programme nutrition " + programmeNutrition.getNom() + " ?");
        alert.setContentText("Vous ne pourrez pas revenir en arrière");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK){
            try {
                facadeProgrammeNutrition.deleteProgrammeNutrition(programmeNutrition.getId());
                setObjectSelected(null);
                this.goToMonEspace(deleteProgrammeNutritionButton);
            } catch (Exception e) {
                super.displayError(errorText, e.getMessage());
            }
        }

    }

    @FXML
    private void handleBuyButton() {
        try {
            setObjectSelected(this.programmeNutrition);
            goToPage(buyButton, "paiements/paiement.fxml", "Acheter le programme " + this.programmeNutrition.getNom());
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }
}
