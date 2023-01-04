package com.fitj.controllers.paiements;

import com.fitj.classes.Admin;
import com.fitj.classes.Coach;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.Optional;

/**
 * Controller de la page pour faire un paiement
 * @see ControllerPaiement
 * @author Paco Munarriz
 */
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
    private AnchorPane headerCoach;
    @FXML
    private AnchorPane headerClient;
    @FXML
    private AnchorPane headerAdmin;
    @FXML
    private Button goBackButton;

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        if (Facade.currentClient instanceof Admin) {
            headerAdmin.setVisible(true);
        } else if (Facade.currentClient instanceof Coach) {
            headerCoach.setVisible(true);
        } else {
            headerClient.setVisible(true);
        }
        if (getPreviousPageName().equals("monCompte")){
            pageTitle.setText("Retirer de l'argent");
            payerButton.setText("Retirer");
        } else {
            pageTitle.setText("Procéder au paiement");
        }
    }

    /**
     * Méthode pour retourner à la page précédente
     */
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

    /**
     * Méthode pour payer
     */
    @FXML
    private void handlePayerButton() {
        if (getPreviousPageName().equals("monCompte")) {
            showConfirmationRetirer();
        } else {
            displayError(errorText, "Fonctionnalité non implémentée");
        }
    }

    /**
     * Méthode pour afficher la fenêtre de confirmation de retrait
     */
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
}
