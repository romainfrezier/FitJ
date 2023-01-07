package com.fitj.controllers.packs;

import com.fitj.classes.*;
import com.fitj.dao.factory.FactoryDAO;
import com.fitj.facades.Facade;
import com.fitj.facades.FacadePack;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ControllerModifyPack extends ControllerPack{

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private TextField nomPack;

    @FXML
    private TextArea descriptionPack;

    @FXML
    private Slider prixPack;



    @FXML
    private Button ajouterProduitButton;

    @FXML
    private Button supprimerProduitButton;

    @FXML
    private Button updatePack;


    @FXML
    private Text errorText;

    @FXML
    private Text montantPack;

    private Produit produitSelected = null;

    private Produit produitSelectedForDelete = null;


    private ArrayList<Seance> listeSeance;
    private ArrayList<ProgrammeSportif> listeProgrammeSportif;
    private ArrayList<ProgrammeNutrition> listeProgrammeNutrition;
    private ArrayList<Pack> listePack;

    private ArrayList<Produit> listeProduitPack;


    @FXML
    private ListView<Seance> listViewSeance;


    @FXML
    private ListView<ProgrammeNutrition> listViewProgrammeNutrition;


    @FXML
    private ListView<ProgrammeSportif> listViewProgrammeSportif;


    @FXML
    private ListView<Pack> listViewPack;




    @FXML
    private ListView<Produit> listViewProduitPack;

    private Pack pack;



    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        try {
            listeProduitPack = new ArrayList<>();
            pack = FacadePack.getInstance().getPackById(getIdObjectSelected());
            nomPack.setText(pack.getNom());
            descriptionPack.setText(pack.getDescription());
            prixPack.setValue(pack.getPrix());
            montantPack.setText(String.valueOf(pack.getPrix()));
            listeProduitPack.addAll(pack.getListeProduit());
            setObjectSelected(null);
            initializeProduitPack();
            initializeProduitList();
        }
        catch (Exception e){
            e.printStackTrace();
            super.displayError(errorText, "Erreur lors du chargement de la page");
        }

    }

    /**
     * Initialise la liste des produits dans les listviews
     */
    private void initializeProduitList(){
        initializeProgrammeSportif();
        initializeProgrammeNutrition();
        initializeSeance();
        initializePack();
    }


    /**
     * Methode permettant d'initialiser la liste des programmes sportifs
     */
    private void initializeProgrammeSportif()  {
        listViewProgrammeSportif.getItems().clear();
        try {
            listeProgrammeSportif = (ArrayList<ProgrammeSportif>) FactoryDAO.getInstance().getDAOProgrammeSportif().getAllProgrammeSportifByCoach(Facade.currentClient.getId());
            initializeProgrammeSportifList(listViewProgrammeSportif, listeProgrammeSportif);
        } catch (Exception e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }
    /**
     * Methode permettant d'initialiser la liste des programmes nutritions
     */
    private void initializeProgrammeNutrition()  {
        listViewProgrammeNutrition.getItems().clear();
        try {
            listeProgrammeNutrition = (ArrayList<ProgrammeNutrition>) FactoryDAO.getInstance().getDAOProgrammeNutrition().getProgrammeNutritionByCoach(Facade.currentClient.getId());
            initializeProgrammeNutritionList(listViewProgrammeNutrition, listeProgrammeNutrition);
        } catch (Exception e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant d'initialiser la liste des séances
     */
    private void initializeSeance()  {
        listViewSeance.getItems().clear();
        try {
            listeSeance = (ArrayList<Seance>) FactoryDAO.getInstance().getDAOSeance().getAllSeancesFromCoach(Facade.currentClient.getId());
            initializeSeanceList(listViewSeance, listeSeance);
        } catch (Exception e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Methode permettant d'initialiser la liste des packs
     */
    private void initializePack()  {
        listViewPack.getItems().clear();
        try {
            listePack = (ArrayList<Pack>) FactoryDAO.getInstance().getDAOPack().getAllPackByCoach(Facade.currentClient.getId());
            initializePackList(listViewPack, listePack);
        } catch (Exception e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }



    /**
     * Methode permettant d'initialiser la liste des produits présents dans le pack
     */
    private void initializeProduitPack() {
        try {
            listViewProduitPack.getItems().clear();
            super.initializeProduitList(listViewProduitPack, listeProduitPack);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Ajoute un produit à la liste des produits du pack pour sa modification
     */
    @FXML
    private void addProduit() {
        if (produitSelected != null){
            listeProduitPack.add(produitSelected);
            produitSelected = null;
            initializeProduitPack();
        } else {
            super.displayError(errorText, "Aucun produit sélectionné");
        }
    }

    /**
     * Supprime un produit du pack pour sa modification
     */
    @FXML
    private void supprimerProduit() {
        listeProduitPack.remove(this.produitSelectedForDelete);
        this.produitSelectedForDelete = null;
        initializeProduitPack();
    }


    /**
     * Méthode appelée lors du clic sur le bouton "Ajouter". modifie le pack
     */
    @FXML
    private void updatePack() {
        try {
            if (verifChamps()){
                hideError(errorText);
                updateProduitPack();
                Pack monPack = facadePack.updatePack(pack.getId(),
                        nomPack.getText(),
                        descriptionPack.getText(),
                        (int) prixPack.getValue());
                super.goToMonEspace(updatePack);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Met à jour les produits du pack
     */
    public void updateProduitPack(){
        List<Produit> produitsEnMoin = pack.getListeProduit().stream().filter(produit -> !listeProduitPack.contains(produit)).toList();
        for (Produit produit : produitsEnMoin) {
            try {
                facadePack.removeProduitFromPack(pack.getId(), produit);
            } catch (Exception e) {
                e.printStackTrace();
                displayError(errorText, e.getMessage());
            }
        }
        List<Produit> produitsEnPlus = listeProduitPack.stream().filter(produit -> !pack.getListeProduit().contains(produit)).toList();
        for (Produit produit : produitsEnPlus) {
            try {
                facadePack.addProduitToPack(pack.getId(), produit);
            } catch (Exception e) {
                e.printStackTrace();
                displayError(errorText, e.getMessage());
            }
        }
    }

    public boolean verifChamps() {
        if (nomPack.getText().isEmpty()) {
            super.displayError(errorText, "Veuillez renseigner le nom du pack");
            return false;
        }
        if (listeProduitPack.isEmpty()) {
            super.displayError(errorText, "Veuillez renseigner au moins un produit pour le pack");
            return false;
        }
        if (descriptionPack.getText().isEmpty()) {
            super.displayError(errorText, "Veuillez renseigner la description du pack");
            return false;
        }
        return true;
    }

    @FXML
    private void updateMontantPack(){
        montantPack.setText((int) prixPack.getValue() + " €");
    }

    @FXML
    private void setProduitSelectedToDelete() {
        this.produitSelectedForDelete = listViewProduitPack.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void setProduitSelectedPack() {
        this.produitSelected = listViewPack.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void setProduitSelectedSeance() {
        this.produitSelected = listViewSeance.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void setProduitSelectedProgrammeSportif() {
        this.produitSelected = listViewProgrammeSportif.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void setProduitSelectedProgrammeNutrition() {
        this.produitSelected = listViewProgrammeNutrition.getSelectionModel().getSelectedItem();
    }

}
