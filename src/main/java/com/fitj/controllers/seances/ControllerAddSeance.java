package com.fitj.controllers.seances;

import com.fitj.classes.*;
import com.fitj.dao.factory.FactoryDAO;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;
import kotlin.Triple;


import java.util.ArrayList;
import java.util.List;

/**
 * Controller de la page d'ajout d'une séance
 * @see ControllerSeance
 * @author Etienne Tillier, Romain Frezier
 */
public class ControllerAddSeance extends ControllerSeance{


    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private TextField nomSeance;
    @FXML
    private Button deleteExerciceButton;
    @FXML
    private ComboBox<Sport> sportSeance;

    @FXML
    private Text montantSeance;

    @FXML
    private TextArea descriptionSeance;

    @FXML
    private Spinner<Integer> nbSerieModifButton;

    @FXML
    private Spinner<Integer> nbRepetModifButton;

    @FXML
    private Spinner<Integer> nbSerieButton;

    @FXML
    private Spinner<Integer> nbRepetButton;

    @FXML
    private Slider prixSeance;


    @FXML
    private Button ajouterExerciceButton;


    @FXML
    private Button addSeance;

    @FXML
    private Button updateExerciceButton;

    @FXML
    private Text errorText;

    @FXML
    private Text nbRepetUpdateTxt;

    @FXML
    private Text nbSerieUpdateTxt;


    private Exercice exerciceSelected = null;

    private Triple<Exercice,Integer,Integer> exerciceSelectedForDelete = null;

    private ArrayList<Exercice> listeExercice;

    private ArrayList<Triple<Exercice,Integer,Integer>> listeExerciceSeance;


    @FXML
    private ListView<Exercice> listViewExercice;


    @FXML
    private ListView<Triple<Exercice,Integer,Integer>> listViewExerciceSeance;



    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        updateButtonExerciceDisplay(false);
        try {
            listeExerciceSeance = new ArrayList<>();
            setObjectSelected(null);
            initializeExerciceList();
            initializeSportList();
            initializeExerciceSeanceList();
            SpinnerValueFactory<Integer> valueFactorySerie = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 6,1);
            SpinnerValueFactory<Integer> valueFactoryRepet = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20,1);
            SpinnerValueFactory<Integer> valueFactoryModifSerie = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 6,1);
            SpinnerValueFactory<Integer> valueFactoryModifRepet = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20,1);
            nbSerieButton.setValueFactory(valueFactorySerie);
            nbRepetButton.setValueFactory(valueFactoryRepet);
            nbSerieModifButton.setValueFactory(valueFactoryModifSerie);
            nbRepetModifButton.setValueFactory(valueFactoryModifRepet);
            montantSeance.setText(prixSeance.getValue() + " €");
        }
        catch (Exception e) {
            super.displayError(errorText, e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Méthode pour modifier l'affichage des boutons d'exercice
     */
    public void updateButtonExerciceDisplay(boolean display) {
        nbRepetModifButton.setVisible(display);
        nbSerieModifButton.setVisible(display);
        updateExerciceButton.setVisible(display);
        deleteExerciceButton.setVisible(display);
        nbRepetUpdateTxt.setVisible(display);
        nbSerieUpdateTxt.setVisible(display);
    }

    /**
     * Methode permettant d'initialiser la liste des exercices
     */
    private void initializeExerciceList()  {
        listViewExercice.getItems().clear();
        try {
            listeExercice = (ArrayList<Exercice>) FactoryDAO.getInstance().getDAOExercice().getAllExercice();
            listViewExercice.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Exercice> call(ListView<Exercice> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Exercice item, boolean empty) {
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
            for (Exercice exercice : listeExercice) {
                listViewExercice.getItems().add(exercice);
            }
        } catch (Exception e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }


    /**
     * Methode permettant d'initialiser la liste des exercices présents dans la séance
     */
    private void initializeExerciceSeanceList() {
        try {
            listViewExerciceSeance.getItems().clear();
            listViewExerciceSeance.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Triple<Exercice,Integer,Integer>> call(ListView<Triple<Exercice,Integer,Integer>> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Triple<Exercice,Integer,Integer> item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                setText(item.getFirst().getNom() + " : " + item.getSecond() + " séries de " + item.getThird() + " répétitions");
                            } else {
                                setText("");
                            }
                        }
                    };
                }
            });
            for (Triple<Exercice,Integer,Integer> exercice : listeExerciceSeance) {
                listViewExerciceSeance.getItems().add(exercice);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Ajoute un exercice à la liste des exercices de la séance pour sa création
     */
    @FXML
    private void addExercice() {
        if (exerciceSelected != null){
            if (nbSerieButton.getValue() > 0 && nbRepetButton.getValue() > 0) {
                listeExerciceSeance.add(new Triple<>(exerciceSelected, nbSerieButton.getValue(), nbRepetButton.getValue()));
                nbSerieButton.getValueFactory().setValue(1);
                nbRepetButton.getValueFactory().setValue(1);
                exerciceSelected = null;
                initializeExerciceSeanceList();
            }
            else {
                super.displayError(errorText, "Le nombre de séries et de répétitions doit être supérieur à 0");
            }
        } else {
            super.displayError(errorText, "Aucun exercice sélectionné");
        }
    }

    /**
     *Méthode pour modifier un exercice
     */
    public void updateExercice(){
        if (exerciceSelectedForDelete != null){
            if (nbSerieModifButton.getValue() > 0 && nbRepetModifButton.getValue() > 0) {
                int index = listeExerciceSeance.indexOf(exerciceSelectedForDelete);
                if (index >= 0){
                    listeExerciceSeance.set(index, new Triple<>(exerciceSelectedForDelete.getFirst(), nbSerieModifButton.getValue(), nbRepetModifButton.getValue()));
                    initializeExerciceSeanceList();
                    updateButtonExerciceDisplay(false);
                }
            }
            else {
                super.displayError(errorText, "Le nombre de séries et de répétitions doit être supérieur à 0");
            }
        } else {
            super.displayError(errorText, "Aucun exercice sélectionné");
        }
    }


    /**
     * Initialise la liste des sports pour la comboBox
     */
    private void initializeSportList() {
        try {
            List<Sport> listeSport = FactoryDAO.getInstance().getDAOSport().getAllSport();
            sportSeance.getItems().addAll(listeSport);
            sportSeance.setCellFactory(new Callback<ListView<Sport>, ListCell<Sport>>() {
                @Override
                public ListCell<Sport> call(ListView<Sport> param) {
                    return new ListCell<Sport>() {
                        @Override
                        protected void updateItem(Sport item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                setText(item.getNom());
                            }
                        }
                    };
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
            super.displayError(errorText, "Erreur lors de l'initialisation de la liste des sports");
        }
    }




    /**
     * Supprime un exercice de la séance
     */
    @FXML
    private void supprimerExercice() {
        listeExerciceSeance.remove(this.exerciceSelectedForDelete);
        this.exerciceSelectedForDelete = null;
        initializeExerciceSeanceList();
    }


    /**
     * Méthode appelée lors du clic sur le bouton "Ajouter". Ajoute la séance
     */
    @FXML
    private void addSeance() {
        try {
            if (verifChamps()){
                hideError(errorText);
                facadeSeance.createSeance(nomSeance.getText(),
                        descriptionSeance.getText(),
                        (int) prixSeance.getValue(),
                        (Coach)Facade.currentClient,
                        sportSeance.getValue(),
                        listeExerciceSeance);
                super.goToMonEspace(addSeance);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode pour vérifier que tous les champs sont remplis
     * @return true si tous les champs sont remplis, false sinon
     */
    public boolean verifChamps() {
        if (nomSeance.getText().isEmpty()) {
            super.displayError(errorText, "Veuillez renseigner le nom de la séance");
            return false;
        }
        if (listeExerciceSeance.isEmpty()) {
            super.displayError(errorText, "Veuillez renseigner au moins un exercice pour la séance");
            return false;
        }
        if (descriptionSeance.getText().isEmpty()) {
            super.displayError(errorText, "Veuillez renseigner la description du la séance");
            return false;
        }
        if (sportSeance.getValue() == null) {
            super.displayError(errorText, "Veuillez renseigner le sport de la séance");
            return false;
        }
        return true;
    }

    /**
     * Méthode pour modifier le prix de la séance
     */
    @FXML
    public void updateMontantSeance(){
        montantSeance.setText((int) prixSeance.getValue() + " €");
    }

    /**
     * Méthode pour recupérer l'execice de la seance à supprimer
     */
    public Triple<Exercice, Integer, Integer> getExerciceSelectedToDelete() {
        return exerciceSelectedForDelete;
    }

    /**
     * Méthode pour selectionner l'exercice de la seance à supprimer
     */
    @FXML
    public void setExerciceSelectedToDelete() {
        this.exerciceSelectedForDelete = listViewExerciceSeance.getSelectionModel().getSelectedItem();
        if (this.exerciceSelectedForDelete != null) {
            nbSerieModifButton.getValueFactory().setValue(this.exerciceSelectedForDelete.getSecond());
            nbRepetModifButton.getValueFactory().setValue(this.exerciceSelectedForDelete.getThird());
        }
        updateButtonExerciceDisplay(true);
    }

    /**
     * Méthode pour selectionner un exercice
     */
    @FXML
    public void setExerciceSelected() {
        this.exerciceSelected = listViewExercice.getSelectionModel().getSelectedItem();
    }

}
