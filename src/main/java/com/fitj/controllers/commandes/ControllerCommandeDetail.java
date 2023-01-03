package com.fitj.controllers.commandes;

import com.fitj.classes.*;
import com.fitj.enums.DemandeEtat;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ControllerCommandeDetail extends ControllerCommande{

    @FXML
    private Text sportLabel;
    @FXML
    private Rectangle sportCase;
    @FXML
    private Button voirDestinataire;
    @FXML
    private Button voirProduit;
    @FXML
    private Text destinataire;
    @FXML
    private Text sport;
    @FXML
    private Text demandeLabel;
    @FXML
    private VBox headerCoach;
    @FXML
    private VBox headerClient;
    @FXML
    private Text errorText;
    @FXML
    private Text titleText;
    @FXML
    private Text nomProduit;
    @FXML
    private Text prixProduit;
    @FXML
    private TextFlow descriptionProduit;
    @FXML
    private TextFlow demandeProduit;
    @FXML
    private Button retourButton;
    @FXML
    private Button repondreButton;

    private Client client;

    /**
     * Initialise la vue
     */
    @FXML
    public void initialize() {
        super.hideError(errorText);
        try {
            Commande commande = facadeCommande.getCommandeById(getIdObjectSelected());
            Produit produit = facadeCommande.getProduitById(commande.getProduit());
            client = commande.getClient();
            Coach coach = commande.getCoach();
            titleText.setText("Détail de la commande n°" + commande.getId());
            nomProduit.setText(produit.getNom());
            prixProduit.setText(produit.getPrix() + "€");
            descriptionProduit.getChildren().add(new Text(produit.getDescription()));
            if (commande.getDemande() != null) {
                demandeProduit.getChildren().add(new Text(commande.getDemande().toString()));
                sport.setText(commande.getDemande().getSport().getNom());
                if (commande.getDemande().getEtat() != DemandeEtat.EN_ATTENTE) {
                    repondreButton.setVisible(false);
                }
            } else {
                demandeProduit.setVisible(false);
                demandeLabel.setVisible(false);
                repondreButton.setVisible(false);
                sport.setVisible(false);
                sportCase.setVisible(false);
                sportLabel.setVisible(false);
            }
            if (!(Facade.currentClient instanceof Coach)) {
                headerCoach.setVisible(false);
                repondreButton.setVisible(false);
                destinataire.setText(coach.getPseudo());
            } else {
                headerClient.setVisible(false);
                destinataire.setText(client.getPseudo());
            }
        } catch (Exception e) {
            e.printStackTrace();
            displayError(errorText, e.getMessage());
        }
    }

    @FXML
    private void goBack() {
        try {
            if (Facade.currentClient instanceof Coach) {
                goToPage(retourButton, "coachs/notifications-coach.fxml", "Notifications");
            } else {
                goToPage(retourButton, "clients/notifications-client.fxml", "Notifications");
            }
        } catch (Exception e) {
            e.printStackTrace();
            displayError(errorText, e.getMessage());
        }

    }

    @FXML
    private void repondre() {
        // TODO emmener vers la page de création de programme personnalisé
        displayError(errorText, "Cette fonctionnalité n'est pas encore disponible");
    }

    @FXML
    private void voirProduit() {
        // TODO emmener vers la page de détail du produit
        displayError(errorText, "Cette fonctionnalité n'est pas encore disponible");
    }

    @FXML
    private void voirDestinaire() {
        try {
            if (Facade.currentClient instanceof Coach) {
                setPreviousPageName("commande");
                setObjectSelected(client);
                goToPage(voirDestinataire, "coachs/detailClient-coach.fxml", "Mon client");
            } else {
                displayError(errorText, "Cette fonctionnalité n'est pas encore disponible");
            }
        } catch (Exception e) {
            displayError(errorText, e.getMessage());
        }
    }
}
