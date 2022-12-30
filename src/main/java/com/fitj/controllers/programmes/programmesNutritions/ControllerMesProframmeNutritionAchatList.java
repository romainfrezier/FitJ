package com.fitj.controllers.programmes.programmesNutritions;

import com.fitj.classes.Coach;
import com.fitj.classes.ProgrammeNutrition;
import com.fitj.dao.factory.FactoryDAO;
import com.fitj.exceptions.BadPageException;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.List;

public class ControllerMesProframmeNutritionAchatList extends ControllerProgrammeNutrition {


    @FXML
    private Button detailProgrammeNutritionButton;
    @FXML
    private ListView<ProgrammeNutrition> listView;
    @FXML
    private Text errorText;

    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        initializeProgrammeNutritionList();
    }

    /**
     * Methode permettant d'initialiser la liste des programmes nutrition
     */
    private void initializeProgrammeNutritionList() {
        try {
            List<ProgrammeNutrition> programmeNutritions = facadeProgrammeNutrition.getAllProgrammesNutritionsByClient(Facade.currentClient.getId());
            listView.setCellFactory(new Callback<>() {
                @Override
                public ListCell<ProgrammeNutrition> call(ListView<ProgrammeNutrition> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(ProgrammeNutrition item, boolean empty) {
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
            for (ProgrammeNutrition programmeNutrition : programmeNutritions) {
                listView.getItems().add(programmeNutrition);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }


    /**
     * Méthode appelée lors du clic sur un programme nutrition de la liste
     */
    @FXML
    private void selectItem(){
        setObjectSelected(listView.getSelectionModel().getSelectedItem());
    }



    /**
     * Methode permettant de se rendre sur la page de modification d'un programme nutrition
     */
    @FXML
    private void goToDetailProgrammeNutrition() {
        try {
            super.hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            super.goToDetailProgrammeNutrition(detailProgrammeNutritionButton);
        } catch (BadPageException | UnselectedItemException e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }


    /**
     * Methode permettant de verifier si un programme nutrition est selectionné
     * @throws UnselectedItemException si aucun programme nutrition n'est selectionné
     */
    private void checkSelected() throws UnselectedItemException {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Vous devez sélectionner un programme nutrition");
        }
    }

}
