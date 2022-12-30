package com.fitj.controllers.programmes.programmesNutritions;

import com.fitj.classes.Admin;
import com.fitj.classes.ProgrammeNutrition;
import com.fitj.classes.Recette;
import com.fitj.enums.ProgrammeType;
import com.fitj.facades.Facade;
import com.fitj.interfaces.Ingredient;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControllerDetailProgrammeNutrition extends ControllerProgrammeNutrition {


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
        } else {
            headerCoach.setVisible(true);
        }
        detailProgrammeNutritionButton.setVisible(false);
        try {
            this.programmeNutrition = facadeProgrammeNutrition.getProgrammeNutritionById(getIdObjectSelected());
            this.listeRecettes.addAll(programmeNutrition.getListeRecette());
            this.nomProgrammeNutrition.setText(this.programmeNutrition.getNom());
            this.nomCoach.setText(this.programmeNutrition.getCoach().getPseudo());
            this.programmeType.setText(ProgrammeType.getProgrammeType(this.programmeNutrition.getType()));
            this.nbMoisProgramme.setText(String.valueOf(this.programmeNutrition.getNbMois()));
            this.descriptionProgramme.getChildren().add(new Text(this.programmeNutrition.getDescription()));
        }
        catch (Exception e){
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
        setObjectSelected(null);
        initializeIngredientList();

    }

    /**
     * Methode permettant d'initialiser la liste des ingrédients présents dans la recette
     */
    private void initializeIngredientList() {
        listViewRecetteProgrammeNutrition.getItems().clear();
        try {
            listViewRecetteProgrammeNutrition.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Recette> call(ListView<Recette> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Recette item, boolean empty) {
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
            for (Recette recette : listeRecettes) {
                listViewRecetteProgrammeNutrition.getItems().add(recette);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
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
            setObjectSelected(this.programmeNutrition.getId());
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
            setObjectSelected(this.listViewRecetteProgrammeNutrition.getSelectionModel().getSelectedItem().getId());
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
    private void handlerViewIngredient(){
        if (listViewRecetteProgrammeNutrition.getSelectionModel().getSelectedItem() instanceof Recette){
            setIdObjectSelected(listViewRecetteProgrammeNutrition.getSelectionModel().getSelectedItem().getId());
            this.detailProgrammeNutritionButton.setVisible(true);
        }
        else {
            this.deleteProgrammeNutritionButton.setVisible(false);
        }
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
}
