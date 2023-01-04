package com.fitj.controllers.programmes.programmesNutritions;

import com.fitj.classes.Admin;
import com.fitj.classes.ProgrammeNutrition;
import com.fitj.classes.Recette;
import com.fitj.dao.factory.FactoryDAO;
import com.fitj.enums.ProgrammeType;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller de la page pour modifier un programme nutrition
 * @see ControllerProgrammeNutrition
 * @author Romain Frezier
 */
public class ControllerModifyProgrammeNutrition extends ControllerProgrammeNutrition {


    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private TextField nomProgrammeNutrition;

    @FXML
    private TextArea descriptionProgramme;

    @FXML
    private Text nbMoisValue;

    @FXML
    private Slider prixProgrammeNutrition;

    @FXML
    private Slider nbMoisProgrammeNutrition;

    @FXML
    private ComboBox<String> typeProgrammeNutrition;



    @FXML
    private Button ajouterRecetteButton;

    @FXML
    private Button supprimerRecetteButton;

    @FXML
    private Button updateProgrammeNutritionButton;


    @FXML
    private Text errorText;

    @FXML
    private Text montantProgramme;

    private Recette recetteSelected = null;

    private Recette recetteSelectedForDelete = null;

    @FXML
    private VBox headerAdmin;

    @FXML
    private VBox headerCoach;

    private ArrayList<Recette> listeRecettes;

    private ArrayList<Recette> listeRecetteProgrammeNutrition;


    @FXML
    private ListView<Recette> listViewRecette;


    @FXML
    private ListView<Recette> listViewRecetteProgrammeNutrition;

    private ProgrammeNutrition programmeNutrition;



    // ---------------------------------------------------------------------------------------------------------------

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
        try {
            this.programmeNutrition = facadeProgrammeNutrition.getProgrammeNutritionById(getIdObjectSelected());
            listeRecetteProgrammeNutrition = new ArrayList<>();
            listeRecetteProgrammeNutrition.addAll(programmeNutrition.getListeRecette());
            nomProgrammeNutrition.setText(this.programmeNutrition.getNom());
            descriptionProgramme.setText(this.programmeNutrition.getDescription());
            prixProgrammeNutrition.setValue(this.programmeNutrition.getPrix());
            nbMoisProgrammeNutrition.setValue(this.programmeNutrition.getNbMois());
            typeProgrammeNutrition.setValue(this.programmeNutrition.getType().toString());
            montantProgramme.setText(this.programmeNutrition.getPrix() + " €");
            nbMoisValue.setText(this.programmeNutrition.getNbMois() + " mois");
            setObjectSelected(null);
            initializeDifficulteProgramme();
            initializeRecetteList();
            initializeRecetteProgrammeNutritionList();
        }
        catch (Exception e) {
            super.displayError(errorText, "Erreur lors de la récupération des données");
            e.printStackTrace();
        }
    }

    /**
     * Initialise la liste des difficultés pour la comboBox
     */
    private void initializeDifficulteProgramme() {
        typeProgrammeNutrition.getItems().clear();
        typeProgrammeNutrition.getItems().add(ProgrammeType.FACILE.toString());
        typeProgrammeNutrition.getItems().add(ProgrammeType.MOYEN.toString());
        typeProgrammeNutrition.getItems().add(ProgrammeType.DIFFICILE.toString());
        try {
            typeProgrammeNutrition.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                @Override
                public ListCell<String> call(ListView<String> param) {
                    return new ListCell<String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                setText(item);
                            }
                        }
                    };
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
            super.displayError(errorText, "Erreur lors de l'initialisation de la liste des difficultés");
        }
    }


    /**
     * Methode permettant d'initialiser la liste des recettes
     */
    private void initializeRecetteList()  {
        try {
            listeRecettes = (ArrayList<Recette>) FactoryDAO.getInstance().getDAORecette().getAllRecettes();
            super.initializeRecetteList(listViewRecette, listeRecettes);
        } catch (Exception e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }


    /**
     * Methode permettant d'initialiser la liste des recettes présentes dans le programme nutrition
     */
    private void initializeRecetteProgrammeNutritionList() {
        try {
            initializeRecetteList(listViewRecetteProgrammeNutrition, listeRecetteProgrammeNutrition);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Ajoute une recette à la liste des recettes du programme nutrition pour sa création
     */
    @FXML
    private void addRecette() {
        if (recetteSelected != null){
            listeRecetteProgrammeNutrition.add(recetteSelected);
            recetteSelected = null;
            initializeRecetteProgrammeNutritionList();
        } else {
            super.displayError(errorText, "Aucune recette sélectionnée");
        }
    }

    /**
     * Supprime une recette du programme nutrition pour sa création
     */
    @FXML
    private void supprimerRecette() {
        listeRecetteProgrammeNutrition.remove(this.recetteSelectedForDelete);
        this.recetteSelectedForDelete = null;
        initializeRecetteProgrammeNutritionList();
    }


    /**
     * Méthode appelée lors du clic sur le bouton "Ajouter". Ajoute le programme nutrition
     */
    @FXML
    private void updateProgrammeNutrition() {
        try {
            if (verifChamps()){
                hideError(errorText);
                updateRecetteProgrammeNutrition();
                facadeProgrammeNutrition.updateProgrammeNutrition(
                        programmeNutrition.getId(),
                        nomProgrammeNutrition.getText(),
                        descriptionProgramme.getText(),
                        (int) prixProgrammeNutrition.getValue(),
                        ProgrammeType.getProgrammeType(typeProgrammeNutrition.getValue()),
                        (int) nbMoisProgrammeNutrition.getValue());
                super.goToMonEspace(updateProgrammeNutritionButton);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Met à jour les recettes du programme nutrition
     */
    public void updateRecetteProgrammeNutrition(){
        List<Recette> recettesEnMoin = programmeNutrition.getListeRecette().stream().filter(recette -> !listeRecetteProgrammeNutrition.contains(recette)).toList();
        for (Recette recette : recettesEnMoin) {
            try {
                facadeProgrammeNutrition.removeRecetteFromProgrammeNutrition(programmeNutrition.getId(), recette);
            } catch (Exception e) {
                e.printStackTrace();
                displayError(errorText, e.getMessage());
            }
        }
        List<Recette> recettesEnPlus = listeRecetteProgrammeNutrition.stream().filter(recette -> !programmeNutrition.getListeRecette().contains(recette)).toList();
        for (Recette recette : recettesEnPlus) {
            try {
                facadeProgrammeNutrition.addRecetteToProgrammeNutrition(programmeNutrition.getId(), recette);
            } catch (Exception e) {
                e.printStackTrace();
                displayError(errorText, e.getMessage());
            }
        }
    }

    /**
     * Méthode pour vérifier que les champs sont bien remplis
     */
    public boolean verifChamps() {
        if (nomProgrammeNutrition.getText().isEmpty()) {
            super.displayError(errorText, "Veuillez renseigner le nom du programme nutrition");
            return false;
        }
        if (typeProgrammeNutrition.getSelectionModel().getSelectedItem() == null) {
            super.displayError(errorText, "Veuillez renseigner le type du programme nutrition");
            return false;
        }
        if (listeRecetteProgrammeNutrition.isEmpty()) {
            super.displayError(errorText, "Veuillez renseigner au moins une recette pour le programme nutrition");
            return false;
        }
        if (descriptionProgramme.getText().isEmpty()) {
            super.displayError(errorText, "Veuillez renseigner la description du programme nutrition");
            return false;
        }
        if (nbMoisProgrammeNutrition.getValue() == 0) {
            super.displayError(errorText, "Veuillez renseigner la durée du programme nutrition");
            return false;
        }
        return true;
    }

    /**
     * Méthode pour modifier le montant du prix du programme nutrition
     */
    @FXML
    private void updateMontantProgramme(){
        montantProgramme.setText((int) prixProgrammeNutrition.getValue() + " €");
    }


    /**
     * Méthode pour selectionner une recette à supprimer
     */
    @FXML
    private void setRecetteSelectedToDelete() {
        this.recetteSelectedForDelete = listViewRecetteProgrammeNutrition.getSelectionModel().getSelectedItem();
    }

    /**
     * Méthode pour selectionner une recette
     */
    @FXML
    private void setRecetteSelected() {
        this.recetteSelected = listViewRecette.getSelectionModel().getSelectedItem();
    }

    /**
     * Méthode pour modifier le nombre de mois du programme nutrition
     */
    @FXML
    private void updateMoisValue() {
        nbMoisValue.setText((int) nbMoisProgrammeNutrition.getValue() + " mois");
    }
}
