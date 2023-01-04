package com.fitj.controllers.seances;

import com.fitj.classes.*;
import com.fitj.dao.factory.FactoryDAO;
import com.fitj.facades.Facade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import kotlin.Triple;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Controller de la page détail d'une séance
 * @see ControllerSeance
 * @author Etienne Tillier, Romain Frezier
 */
public class ControllerDetailSeance extends ControllerSeance{



    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private Text nomSeanceTxt;

    @FXML
    private Button deleteSeanceButton;

    @FXML
    private Button updateSeanceButton;

    @FXML
    private Text sportTxt;

    @FXML
    private Text montantSeance;

    @FXML
    private TextArea descriptionSeance;

    @FXML
    private Text errorText;


    @FXML
    private VBox headerAdmin;

    @FXML
    private VBox headerCoach;
    @FXML
    private Button buyButton;
    @FXML
    private VBox headerClient;

    private ArrayList<Triple<Exercice,Integer,Integer>> listeExerciceSeance;


    @FXML
    private ListView<Triple<Exercice,Integer,Integer>> listViewExerciceSeance;

    private Seance seance;



    // ---------------------------------------------------------------------------------------------------------------

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
        try {
            listeExerciceSeance = (ArrayList<Triple<Exercice, Integer, Integer>>) facadeSeance.getExercices(getIdObjectSelected());
            this.seance = facadeSeance.getSeance(getIdObjectSelected());
            checkCurrentCoach();
            setObjectSelected(seance);
            nomSeanceTxt.setText(seance.getNom());
            descriptionSeance.setText(seance.getDescription());
            montantSeance.setText(String.valueOf(seance.getPrix()) + " €" );
            sportTxt.setText(String.valueOf(seance.getSport()));
            initializeExerciceSeanceList();
        }
        catch (Exception e) {
            super.displayError(errorText, e.getMessage());
            e.printStackTrace();
        }

    }

    public void checkCurrentCoach(){
        if (!(Facade.currentClient instanceof Admin) && this.seance.getCoach().getId() != Facade.currentClient.getId()){
            updateSeanceButton.setVisible(false);
            deleteSeanceButton.setVisible(false);
            if (getPreviousPageName().equals("shop")){
                buyButton.setVisible(true);
            }
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
     * Méthode permettant d'afficher une fenêtre de confirmation de suppression d'une séance
     */
    private void showConfirmationDeleteRecette() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Séance");
        alert.setHeaderText("Vous êtes sûr de vouloir supprimer cette séance : " + seance.getNom() + " ?");
        alert.setContentText("Vous ne pourrez pas revenir en arrière");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK){
            try {
                facadeSeance.deleteSeance(seance.getId());
            } catch (Exception e) {
                super.displayError(errorText, e.getMessage());
            }
        }

    }

    /**
     * Méthode permettant de supprimer la recette actuelle
     */
    @FXML
    private void deleteSeance(){
        try {
            showConfirmationDeleteRecette();
            this.goToMonEspace(deleteSeanceButton);
        }
        catch (Exception e){
            super.displayError(errorText, e.getMessage());
        }
    }


    /**
     * Permet d'aller sur la page de modification de la recette actuelle
     */
    @FXML
    private void updateSeance() {
        try {
            setObjectSelected(this.seance.getId());
            this.goToUpdateSeance(updateSeanceButton);
        }
        catch (Exception e){
            super.displayError(errorText, e.getMessage());
        }
    }


    @FXML
    private void handleBuyButton() {
        try {
            setObjectSelected(this.seance);
            goToPage(buyButton, "paiements/paiement.fxml", "Acheter la séance" + this.seance.getNom());
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }
}

