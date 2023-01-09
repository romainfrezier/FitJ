package com.fitj.controllers.packs;

import com.fitj.classes.*;
import com.fitj.dao.factory.FactoryDAO;
import com.fitj.enums.ProgrammeType;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.ArrayList;

/**
 * Controller de la page add-pack-view.fxml
 * @see ControllerPack
 * @author Etienne Tillier
 */
public class ControllerAddPack extends ControllerPack{


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
    private Button addPack;


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



    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        listeProduitPack = new ArrayList<>();
        setObjectSelected(null);
        initializeProduitPack();
        initializeProduitList();
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
     * Ajoute un produit à la liste des produits du pack pour sa création
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
     * Supprime un produit du pack pour sa création
     */
    @FXML
    private void supprimerProduit() {
        listeProduitPack.remove(this.produitSelectedForDelete);
        this.produitSelectedForDelete = null;
        initializeProduitPack();
    }


    /**
     * Méthode appelée lors du clic sur le bouton "Ajouter". Ajoute le pack
     */
    @FXML
    private void addPack() {
        try {
            if (verifChamps()){
                hideError(errorText);
                Pack monPack = facadePack.createPack(nomPack.getText(),
                        descriptionPack.getText(),
                        (int) prixPack.getValue(),
                        (Coach) Facade.currentClient);
                for (Produit produit : listeProduitPack){
                    facadePack.addProduitToPack(monPack.getId(), produit);
                }
                super.goToMonEspace(addPack);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
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
