package com.fitj.controllers.programmes.programmesSportifs;

import com.fitj.classes.*;
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

public class ControllerModifyProgrammeSportif extends ControllerProgrammeSportif{


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
    private Button updateProgrammeSportifButton;


    @FXML
    private Text errorText;

    @FXML
    private Text montantProgramme;

    private Seance seanceSelected = null;

    private Seance seanceSelectedForDelete = null;

    @FXML
    private VBox headerAdmin;

    @FXML
    private VBox headerCoach;

    private ArrayList<Seance> listeSeances;

    private ArrayList<Seance> listeSeanceProgrammeSportif;


    @FXML
    private ListView<Seance> listViewSeance;


    @FXML
    private ListView<Seance> listViewSeanceProgrammeSportif;

    private ProgrammeSportif programmeSportif;



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
            this.programmeSportif = facadeProgrammeSportif.getProgrammeSportifById(getIdObjectSelected());
            listeSeanceProgrammeSportif = new ArrayList<>();
            listeSeanceProgrammeSportif.addAll(programmeSportif.getListeSeance());
            nomProgrammeSportif.setText(this.programmeSportif.getNom());
            descriptionProgramme.setText(this.programmeSportif.getDescription());
            prixProgrammeSportif.setValue(this.programmeSportif.getPrix());
            nbMoisProgrammeSportif.setValue(this.programmeSportif.getNbMois());
            typeProgrammeSportif.setValue(this.programmeSportif.getType().toString());
            montantProgramme.setText(this.programmeSportif.getPrix() + " €");
            nbMoisValue.setText(this.programmeSportif.getNbMois() + " mois");
            setObjectSelected(null);
            initializeDifficulteProgramme();
            initializeSeanceList();
            initializeSeanceProgrammeSportifList();
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
     * Methode permettant d'initialiser la liste des séances
     */
    private void initializeSeanceList()  {
        try {
            listeSeances = (ArrayList<Seance>) FactoryDAO.getInstance().getDAOSeance().getAllSeances();
            super.initializeSeanceList(listViewSeance, listeSeances);
        } catch (Exception e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }


    /**
     * Methode permettant d'initialiser la liste des séances présentes dans le programme sportif
     */
    private void initializeSeanceProgrammeSportifList() {
        try {
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
            initializeSeanceProgrammeSportifList();
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
        initializeSeanceProgrammeSportifList();
    }


    /**
     * Méthode appelée lors du clic sur le bouton "Ajouter". Ajoute le programme sportif
     */
    @FXML
    private void updateProgrammeSportif() {
        try {
            if (verifChamps()){
                hideError(errorText);
                updateSeanceProgrammeSportif();
                facadeProgrammeSportif.updateProgrammeSportif(
                        programmeSportif.getId(),
                        nomProgrammeSportif.getText(),
                        descriptionProgramme.getText(),
                        (int) prixProgrammeSportif.getValue(),
                        ProgrammeType.getProgrammeType(typeProgrammeSportif.getValue()),
                        (int) nbMoisProgrammeSportif.getValue());
                super.goToMonEspace(updateProgrammeSportifButton);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Met à jour les séances du programme sportif
     */
    public void updateSeanceProgrammeSportif(){
        List<Seance> seancesEnMoin = programmeSportif.getListeSeance().stream().filter(seance -> !listeSeanceProgrammeSportif.contains(seance)).toList();
        for (Seance seance : seancesEnMoin) {
            try {
                facadeProgrammeSportif.removeSeanceFromProgrammeSportif(programmeSportif.getId(), seance);
            } catch (Exception e) {
                e.printStackTrace();
                displayError(errorText, e.getMessage());
            }
        }
        List<Seance> seancesEnPlus = listeSeanceProgrammeSportif.stream().filter(seance -> !programmeSportif.getListeSeance().contains(seance)).toList();
        for (Seance seance : seancesEnPlus) {
            try {
                facadeProgrammeSportif.addSeanceToProgrammeSportif(programmeSportif.getId(), seance);
            } catch (Exception e) {
                e.printStackTrace();
                displayError(errorText, e.getMessage());
            }
        }
    }

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

    @FXML
    private void updateMontantProgramme(){
        montantProgramme.setText((int) prixProgrammeSportif.getValue() + " €");
    }


    @FXML
    private void setSeanceSelectedToDelete() {
        this.seanceSelectedForDelete = listViewSeanceProgrammeSportif.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void setSeanceSelected() {
        this.seanceSelected = listViewSeance.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void updateMoisValue() {
        nbMoisValue.setText((int) nbMoisProgrammeSportif.getValue() + " mois");
    }

}
