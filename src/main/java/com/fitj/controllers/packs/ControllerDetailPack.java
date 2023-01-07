package com.fitj.controllers.packs;

import com.fitj.classes.*;
import com.fitj.enums.ProgrammeType;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControllerDetailPack extends ControllerPack{



    @FXML
    private Text prix;
    @FXML
    private Button buyButton;

    @FXML
    private Button detailProduitButton;

    @FXML
    private Button deletePackButton;

    @FXML
    private Button updatePackButton;

    @FXML
    private ListView<Produit> listViewProduitPack;


    @FXML
    private TextFlow descriptionPack;


    @FXML
    private Text nomCoach;

    @FXML
    private Text nomPack;

    @FXML
    private Text errorText;

    private List<Produit> listeProduit = new ArrayList<>();

    private Pack pack;

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        detailProduitButton.setVisible(false);
        try {
            this.pack = facadePack.getPackById(getIdObjectSelected());
            checkCurrentCoach();
            this.listeProduit.addAll(pack.getListeProduit());
            this.nomPack.setText(this.pack.getNom());
            this.nomCoach.setText(this.pack.getCoach().getPseudo());
            this.descriptionPack.getChildren().add(new Text(this.pack.getDescription()));
            this.prix.setText(this.pack.getPrix() + " €");
        }
        catch (Exception e){
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
        setObjectSelected(null);
        initializeProduit();

    }

    /**
     * Methode permettant d'initialiser la liste des produits présents dans le pack
     */
    private void initializeProduit() {
        listViewProduitPack.getItems().clear();
        try {
            super.initializeProduitList(listViewProduitPack, listeProduit);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    public void checkCurrentCoach(){
        if (!(Facade.currentClient instanceof Admin) && this.pack.getCoach().getId() != Facade.currentClient.getId()){
            updatePackButton.setVisible(false);
            deletePackButton.setVisible(false);
            if (getPreviousPageName().equals("shop")){
                buyButton.setVisible(true);
            }
        }
    }

    /**
     * Méthode permettant de supprimer le pack actuel
     */
    @FXML
    private void deletePack(){
        try {
            showConfirmationDeletePack();
        }
        catch (Exception e){
            super.displayError(errorText, e.getMessage());
        }
    }


    /**
     * Permet d'aller sur la page de modification du pack actuel
     */
    @FXML
    private void updatePack() {
        try {
            setIdObjectSelected(this.pack.getId());
            this.goToUpdatePack(updatePackButton);
        }
        catch (Exception e){
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Permet d'aller sur la page de détail du produit cliqué dans la liste des produits du pack
     */
    @FXML
    private void goToDetailProduit() {
        try {
            setIdObjectSelected(this.listViewProduitPack.getSelectionModel().getSelectedItem().getId());
            super.goToDetailProduit(detailProduitButton, listViewProduitPack.getSelectionModel().getSelectedItem());
        }
        catch (Exception e){
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Permet d'afficher le bouton détail du produit que lorsqu'il s'agit d'un produit de sélectionnée dans la liste
     */
    @FXML
    private void handlerViewProduit(){
        setIdObjectSelected(listViewProduitPack.getSelectionModel().getSelectedItem().getId());
        if (!getPreviousPageName().equals("shop")){
            this.detailProduitButton.setVisible(true);
        }
    }



    /**
     * Méthode permettant d'afficher une fenêtre de confirmation de suppression d'un pack
     */
    private void showConfirmationDeletePack() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete pack");
        alert.setHeaderText("Vous êtes sûr de vouloir supprimer ce pack " + pack.getNom() + " ?");
        alert.setContentText("Vous ne pourrez pas revenir en arrière");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK){
            try {
                facadePack.deletePack(pack.getId());
                setObjectSelected(null);
                this.goToMonEspace(deletePackButton);
            } catch (Exception e) {
                super.displayError(errorText, e.getMessage());
            }
        }

    }

    @FXML
    private void handleBuyButton() {
        try {
            setObjectSelected(this.pack);
            goToPage(buyButton, "paiements/paiement.fxml", "Acheter le pack " + this.pack.getNom());
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

}
