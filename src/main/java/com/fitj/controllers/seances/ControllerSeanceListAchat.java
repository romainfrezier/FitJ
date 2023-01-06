package com.fitj.controllers.seances;

import com.fitj.classes.Seance;
import com.fitj.exceptions.BadPageException;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.List;

/**
 * Controller de la page de liste des séances achetées
 * @see ControllerSeance
 * @author Romain Frezier
 */
public class ControllerSeanceListAchat extends ControllerSeance{



    @FXML
    private Button detailSeanceButton;
    @FXML
    private ListView<Seance> listView;

    @FXML
    private Text errorText;

    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        initializeAlimentList();
    }

    /**
     * Methode permettant d'initialiser la liste des séances
     */
    private void initializeAlimentList() {
        try {
            List<Seance> seances = facadeSeance.getAllSeancesFromClient(Facade.currentClient.getId());
            listView.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Seance> call(ListView<Seance> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Seance item, boolean empty) {
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
            for (Seance seance : seances) {
                listView.getItems().add(seance);
            }
        } catch (Exception e) {
            e.printStackTrace();
            super.displayError(errorText, "Erreur lors de la récupération des séances");
        }
    }


    /**
     * Méthode appelée lors du clic sur une séance de la liste
     */
    @FXML
    private void selectItemSeance(){
        setObjectSelected(listView.getSelectionModel().getSelectedItem());
    }



    /**
     * Methode permettant de se rendre sur la page de modification d'une séance
     */
    @FXML
    private void goToDetailSeance() {
        try {
            super.hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            setPreviousPageName("achats");
            super.goToDetailSeance(detailSeanceButton);
        } catch (BadPageException | UnselectedItemException e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }



    /**
     * Methode permettant de verifier si une séance est selectionnée
     * @throws UnselectedItemException si aucune séance n'est selectionnée
     */
    private void checkSelected() throws UnselectedItemException {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Vous devez sélectionner une séance");
        }
    }



}
