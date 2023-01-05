package com.fitj.controllers.paiements;

import com.fitj.classes.Produit;
import com.fitj.classes.ProgrammePersonnalise;
import com.fitj.enums.PaiementType;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.Optional;

public class ControllerFairePaiement extends ControllerPaiement {
    @FXML
    private RadioButton radioCB;
    @FXML
    private ToggleGroup payment;
    @FXML
    private RadioButton radioPaypal;
    @FXML
    private Button payerButton;
    @FXML
    private Text pageTitle;
    @FXML
    private Text errorText;
    @FXML
    private Button goBackButton;

    @FXML
    private void initialize() {
        super.hideError(errorText);
        if (getPreviousPageName().equals("monCompte")){
            pageTitle.setText("Retirer de l'argent");
            payerButton.setText("Retirer");
        } else {
            pageTitle.setText("Procéder au paiement");
        }
    }

    @FXML
    private void goBack() {
        try {
            if (getPreviousPageName().equals("monCompte")) {
                super.goToMonCompte(goBackButton);
            } else {
                super.goToShop(goBackButton);
            }
        } catch (BadPageException e) {
            super.displayError(errorText, e.getMessage());
        }

    }

    @FXML
    private void handlePayerButton() {
        if (getPreviousPageName().equals("monCompte")) {
            showConfirmationRetirer();
        } else if (getPreviousPageName().equals("shop")) {
            Produit produit = (Produit) getObjectSelected();
            showConfirmationPayer(produit);
        }
    }

    private void showConfirmationRetirer() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Retirer");
        alert.setHeaderText("Vous êtes sûr de vouloir retirer l'argent de votre compte ?");
        alert.setContentText("Vous ne pourrez pas revenir en arrière");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK){
            try {
                Facade.currentClient = facadePaiement.retirerArgent(Facade.currentClient.getId());
                super.goToMonCompte(payerButton);
            } catch (Exception e) {
                super.displayError(errorText, e.getMessage());
            }
        }
    }

    private void showConfirmationPayer(Produit produit) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Paiement");
        alert.setHeaderText("Vous êtes sûr de vouloir acheter le produit \"" + produit.getNom() + "\" au prix de " + produit.getPrix() + " € ?");
        alert.setContentText("Vous ne pourrez pas revenir en arrière");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK){
            try {
                if (!(produit instanceof ProgrammePersonnalise)) {
                    RadioButton selectedButton = (RadioButton)payment.getSelectedToggle();
                    if (selectedButton.equals(radioCB)) {
                        facadePaiement.acheterProduit(Facade.currentClient.getId(), produit, PaiementType.CARTE_BANCAIRE);
                    } else if (selectedButton.equals(radioPaypal)) {
                        facadePaiement.acheterProduit(Facade.currentClient.getId(), produit, PaiementType.PAYPAL);
                    }
                }
                super.goToShop(payerButton);
            } catch (Exception e) {
                super.displayError(errorText, e.getMessage());
            }
        }

    }
}
