package com.fitj.controllers.programmes.programmesNutritions;

import com.fitj.classes.Coach;
import com.fitj.classes.Recette;
import com.fitj.dao.factory.FactoryDAO;
import com.fitj.enums.ProgrammeType;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.ArrayList;

/**
 * Controller de la page pour ajouter un programme nutrition
 * @see ControllerProgrammeNutrition
 * @author Paco Munarriz
 */
public class ControllerAddProgrammeNutrition extends ControllerProgrammeNutrition {



    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private TextField nomProgrammeNutrition;

    @FXML
    private TextArea descriptionProgramme;

    @FXML
    private  Text nbMoisValue;

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
    private Button addProgrammeNutrition;


    @FXML
    private Text errorText;

    @FXML
    private Text montantProgramme;

    private Recette recetteSelected = null;

    private Recette recetteSelectedForDelete = null;


    private ArrayList<Recette> listeRecettes;

    private ArrayList<Recette> listeRecetteProgrammeNutrition;


    @FXML
    private ListView<Recette> listViewRecette;


    @FXML
    private ListView<Recette> listViewRecetteProgrammeNutrition;



    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        listeRecetteProgrammeNutrition = new ArrayList<>();
        setObjectSelected(null);
        initializeDifficulteProgramme();
        initializeRecetteList();
        initializeRecetteProgrammeNutritionList();
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
        listViewRecette.getItems().clear();
        try {
            listeRecettes = (ArrayList<Recette>) FactoryDAO.getInstance().getDAORecette().getAllRecettes();
            initializeRecetteList(listViewRecette, listeRecettes);
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
            listViewRecetteProgrammeNutrition.getItems().clear();
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
    private void addProgrammeNutrition() {
        try {
            if (verifChamps()){
                hideError(errorText);
                facadeProgrammeNutrition.createProgrammeNutrition(nomProgrammeNutrition.getText(),
                                                                    descriptionProgramme.getText(),
                                                                    (int) prixProgrammeNutrition.getValue(),
                                                                    ProgrammeType.getProgrammeType(typeProgrammeNutrition.getValue()),
                                                                    (int) nbMoisProgrammeNutrition.getValue(),
                                                                    (Coach)Facade.currentClient,
                                                                    listeRecetteProgrammeNutrition);
                super.goToMonEspace(addProgrammeNutrition);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode permettant de vérifier que tous les champs sont remplis
     * @return true si tous les champs sont remplis, false sinon
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
     * Méthode pour update le montant du programme nutrition
     */
    @FXML
    private void updateMontantProgramme(){
        montantProgramme.setText((int) prixProgrammeNutrition.getValue() + " €");
    }

    /**
     * Méthode pour selectionner une recette a supprimer
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
        this.nbMoisValue.setText((int) nbMoisProgrammeNutrition.getValue() + " mois");
    }
}
