package com.fitj.controllers.shop;

import com.fitj.classes.*;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.text.Text;

import java.util.List;

public class ControllerShopTabs extends ControllerShop {
    @FXML
    private TabPane tabs;
    @FXML
    private ListView<Pack> packList;
    @FXML
    private Button voirPack;
    @FXML
    private ListView<ProgrammeNutrition> programmeNutritionList;
    @FXML
    private Button voirNutrition;
    @FXML
    private ListView<ProgrammeSportif> programmeSportifList;
    @FXML
    private Button voirSportif;
    @FXML
    private ListView<Seance> seancesList;
    @FXML
    private Button voirSeance;
    @FXML
    private Text errorText;

    @FXML
    private void selectItemList() {
        String currentTabName = tabs.getSelectionModel().getSelectedItem().getText();
        switch (currentTabName){
            case "Packs":
                setIdObjectSelected(packList.getSelectionModel().getSelectedItem().getId());
                break;
            case "Programmes Nutrition":
                setIdObjectSelected(programmeNutritionList.getSelectionModel().getSelectedItem().getId());
                break;
            case "Programmes Sportifs":
                setIdObjectSelected(programmeSportifList.getSelectionModel().getSelectedItem().getId());
                break;
            case "Séances":
                setIdObjectSelected(seancesList.getSelectionModel().getSelectedItem().getId());
                break;
            default:
                break;
        }
    }

    @FXML
    private void initialize(){
        super.hideError(errorText);
        initializeAllList();
    }

    private void initializeAllList() {
        initializePackList();
        initializeProgrammeSportifList();
        initializeProgrammeNutritionList();
        initializeSeanceList();
    }

    private void initializeSeanceList() {
        try {
            List<Seance> seances = facadeSeance.getAllSeances();
            List<Seance> seancesToRemove = facadeSeance.getAllSeancesFromClient(Facade.currentClient.getId());
            if (Facade.currentClient instanceof Coach) {
                seancesToRemove.addAll(facadeSeance.getAllSeancesFromCoach(Facade.currentClient.getId()));
            }
            seances.removeAll(seancesToRemove);
            super.initializeProduitList(seancesList, seances);
        } catch (Exception e) {
            e.printStackTrace();
            displayError(errorText, e.getMessage());
        }
    }

    private void initializeProgrammeNutritionList() {
        try {
            List<ProgrammeNutrition> programmeNutritions = facadeProgrammeNutrition.getListeProgrammeNutrition();
            List<ProgrammeNutrition> programmeNutritionsToRemove = facadeProgrammeNutrition.getAllProgrammesNutritionsByClient(Facade.currentClient.getId());
            if (Facade.currentClient instanceof Coach) {
                programmeNutritionsToRemove.addAll(facadeProgrammeNutrition.getProgrammeNutritionByCoach(Facade.currentClient.getId()));
            }
            programmeNutritions.removeAll(programmeNutritionsToRemove);
            super.initializeProduitList(programmeNutritionList, programmeNutritions);
        } catch (Exception e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }

    private void initializeProgrammeSportifList() {
        try {
            List<ProgrammeSportif> programmeSportifs = facadeProgrammeSportif.getListeProgrammeSportif();
            List<ProgrammeSportif> programmeSportifsToRemove = facadeProgrammeSportif.getAllProgrammesSportifsByClient(Facade.currentClient.getId());
            if (Facade.currentClient instanceof Coach) {
                programmeSportifsToRemove.addAll(facadeProgrammeSportif.getProgrammeSportifByCoach(Facade.currentClient.getId()));
            }
            programmeSportifs.removeAll(programmeSportifsToRemove);
            super.initializeProduitList(programmeSportifList, programmeSportifs);
        } catch (Exception e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }

    private void initializePackList() {
        try {
            List<Pack> packs = facadePack.getListePack();
            List<Pack> packsToRemove = facadePack.getAllPacksByClient(Facade.currentClient.getId());
            if (Facade.currentClient instanceof Coach) {
                packsToRemove.addAll(facadePack.getPackByCoach((Coach)Facade.currentClient));
            }
            packs.removeAll(packsToRemove);
            super.initializeProduitList(packList, packs);
        } catch (Exception e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }

    @FXML
    private void handleVoirPack() {
        try {
            checkIfItemSelected(packList);
            setPreviousPageName("shop");
            goToPage(voirPack,"produits/packs/detail-pack.fxml", "Pack");
        } catch (Exception e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }

    @FXML
    private void handleVoirSeance() {
        try {
            checkIfItemSelected(seancesList);
            setPreviousPageName("shop");
            goToPage(voirNutrition,"produits/seances/detail-seance.fxml", "Séance");
        } catch (Exception e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }

    @FXML
    private void handleVoirSportif() {
        try {
            checkIfItemSelected(programmeSportifList);
            setPreviousPageName("shop");
            goToPage(voirNutrition,"produits/programmes/programmesSportifs/detail-ProgrammeSportif.fxml", "Programme sportif");
        } catch (Exception e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }

    @FXML
    private void handleVoirNutrition() {
        try {
            checkIfItemSelected(programmeNutritionList);
            setPreviousPageName("shop");
            goToPage(voirNutrition,"produits/programmes/programmesNutritions/detail-ProgrammeNutrition.fxml", "Programme nutrition");
        } catch (Exception e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }

    private <T> void checkIfItemSelected(ListView<T> listView) throws UnselectedItemException {
        if (listView.getSelectionModel().getSelectedItem() == null){
            throw new UnselectedItemException("Veuillez sélectionner un élément dans la liste");
        }
    }
}