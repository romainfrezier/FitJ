package com.fitj.controllers.programmes.programmesSportifs;

import com.fitj.classes.Coach;
import com.fitj.classes.Seance;
import com.fitj.dao.factory.FactoryDAO;
import com.fitj.enums.ProgrammeType;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.ArrayList;

/**
 * Controller de la page pour ajouter un programme sportif
 * @see ControllerProgrammeSportif
 * @author Paco Munarriz
 */
public class ControllerAddProgrammeSportif extends ControllerProgrammeSportif{

    @FXML
    private TextField nomProgrammeSportif;

    @FXML
    private TextArea descriptionProgramme;

    @FXML
    private Text nbMoisValue;

    @FXML
    private Slider prixProgrammeSportif;

    @FXML
    private Slider nbMoisProgrammeSportif;

    @FXML
    private ComboBox<String> typeProgrammeSportif;



    @FXML
    private Button ajouterSeanceButton;

    @FXML
    private Button supprimerSeanceButton;

    @FXML
    private Button addProgrammeSportif;


    @FXML
    private Text errorText;

    @FXML
    private Text montantProgramme;

    private Seance seanceSelected = null;

    private Seance seanceSelectedForDelete = null;
    private ArrayList<Seance> listeSeances;

    private ArrayList<Seance> listeSeanceProgrammeSportif;


    @FXML
    private ListView<Seance> listViewSeance;


    @FXML
    private ListView<Seance> listViewSeanceProgrammeSportif;



    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        listeSeanceProgrammeSportif = new ArrayList<>();
        setObjectSelected(null);
        initializeDifficulteProgramme();
        initializeSeanceList();
        initializeSeanceProgrammeNutritionList();
    }

    /**
     * Initialise la liste des difficultés pour la comboBox
     */
    private void initializeDifficulteProgramme() {
        typeProgrammeSportif.getItems().clear();
        typeProgrammeSportif.getItems().add(ProgrammeType.FACILE.toString());
        typeProgrammeSportif.getItems().add(ProgrammeType.MOYEN.toString());
        typeProgrammeSportif.getItems().add(ProgrammeType.DIFFICILE.toString());
        try {
            typeProgrammeSportif.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
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
    private void initializeSeanceList()  {
        listViewSeance.getItems().clear();
        try {
            listeSeances = (ArrayList<Seance>) FactoryDAO.getInstance().getDAOSeance().getAllSeances();
            initializeSeanceList(listViewSeance, listeSeances);
        } catch (Exception e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }


    /**
     * Methode permettant d'initialiser la liste des recettes présentes dans le programme nutrition
     */
    private void initializeSeanceProgrammeNutritionList() {
        try {
            listViewSeanceProgrammeSportif.getItems().clear();
            initializeSeanceList(listViewSeanceProgrammeSportif, listeSeanceProgrammeSportif);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Ajoute une séance à la liste des séances du programme sportif pour sa création
     */
    @FXML
    private void addSeance() {
        if (seanceSelected != null){
            listeSeanceProgrammeSportif.add(seanceSelected);
            seanceSelected = null;
            initializeSeanceProgrammeNutritionList();
        } else {
            super.displayError(errorText, "Aucune séance sélectionnée");
        }
    }

    /**
     * Supprime une séance du programme sportif pour sa création
     */
    @FXML
    private void supprimerSeance() {
        listeSeanceProgrammeSportif.remove(this.seanceSelectedForDelete);
        this.seanceSelectedForDelete = null;
        initializeSeanceProgrammeNutritionList();
    }


    /**
     * Méthode appelée lors du clic sur le bouton "Ajouter". Ajoute le programme sportif
     */
    @FXML
    private void addProgrammeSportif() {
        try {
            if (verifChamps()){
                hideError(errorText);
                facadeProgrammeSportif.createProgrammeSportif(nomProgrammeSportif.getText(),
                        descriptionProgramme.getText(),
                        (int) prixProgrammeSportif.getValue(),
                        ProgrammeType.getProgrammeType(typeProgrammeSportif.getValue()),
                        (int) nbMoisProgrammeSportif.getValue(),
                        (Coach)Facade.currentClient,
                        listeSeanceProgrammeSportif);
                super.goToMonEspace(addProgrammeSportif);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode pour verifier que les champs sont remplies, retourne true si c'est le cas
     */
    public boolean verifChamps() {
        if (nomProgrammeSportif.getText().isEmpty()) {
            super.displayError(errorText, "Veuillez renseigner le nom du programme sportif");
            return false;
        }
        if (typeProgrammeSportif.getSelectionModel().getSelectedItem() == null) {
            super.displayError(errorText, "Veuillez renseigner le type du programme sportif");
            return false;
        }
        if (listeSeanceProgrammeSportif.isEmpty()) {
            super.displayError(errorText, "Veuillez renseigner au moins une séance pour le programme sportif");
            return false;
        }
        if (descriptionProgramme.getText().isEmpty()) {
            super.displayError(errorText, "Veuillez renseigner la description du programme sportif");
            return false;
        }
        if (nbMoisProgrammeSportif.getValue() == 0) {
            super.displayError(errorText, "Veuillez renseigner la durée du programme sportif");
            return false;
        }
        return true;
    }

    /**
     * Méthode pour modifier le prix du programme sportif
     */
    @FXML
    private void updateMontantProgramme(){
        montantProgramme.setText((int) prixProgrammeSportif.getValue() + " €");
    }

    /**
     * Méthode selectionner la recette a supprimer
     */
    @FXML
    private void setSeanceSelectedToDelete() {
        this.seanceSelectedForDelete = listViewSeanceProgrammeSportif.getSelectionModel().getSelectedItem();
    }

    /**
     * Méthode selectionner une seance
     */
    @FXML
    private void setSeanceSelected() {
        this.seanceSelected = listViewSeance.getSelectionModel().getSelectedItem();
    }

    /**
     * Méthode pour le nombre de mois du programme sportif
     */
    @FXML
    private void updateMoisValue() {
        this.nbMoisValue.setText((int) nbMoisProgrammeSportif.getValue() + " mois");
    }
}
