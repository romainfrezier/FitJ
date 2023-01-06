package com.fitj.controllers.commandes;

import com.fitj.classes.*;
import com.fitj.enums.DemandeEtat;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private Coach coach;
    private Produit produit;

    /**
     * Initialise la vue
     */
    @FXML
    public void initialize() {
        super.hideError(errorText);
        try {
            Commande commande = facadeCommande.getCommandeById(getIdObjectSelected());
            produit = facadeCommande.getProduitById(commande.getProduit());
            client = commande.getClient();
            coach = commande.getCoach();
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
                repondreButton.setVisible(false);
                destinataire.setText(coach.getPseudo());
            } else {
                destinataire.setText(client.getPseudo());
            }
            if (Facade.currentClient.getId() == client.getId()) {
                voirDestinataire.setText("Mon profil");
                destinataire.setText(destinataire.getText() + " (moi)");
            }
        } catch (Exception e) {
            e.printStackTrace();
            displayError(errorText, e.getMessage());
        }
    }

    @FXML
    private void goBack() {
        try {
            goToPage(retourButton, "notifications/notifications-commandes-list.fxml", "Notifications & Commandes");
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
    private void voirProduit() throws BadPageException {
        setPreviousPageName("commandes");
        setIdObjectSelected(produit.getId());
        if (produit instanceof ProgrammeNutrition){
            goToPage(voirProduit, "produits/programmes/programmesNutrition/detail-programmeNutrition.fxml", "Détail du programme nutrition");
        } else if (produit instanceof ProgrammeSportif){
            goToPage(voirProduit, "produits/programmes/programmesSportifs/detail-programmeSportif.fxml", "Détail du programme sportif");
        } else if (produit instanceof Seance){
            goToPage(voirProduit, "produits/seances/detail-seance.fxml", "Détail de la séance");
        }
    }

    @FXML
    private void voirDestinaire() {
        try {
            setPreviousPageName("commande");
            setObjectSelected(client);
            if (client instanceof Coach) {
                goToPage(voirDestinataire, "coachs/detailCoach-coach.fxml", "Mon coach");
            } else {
                goToPage(voirDestinataire, "coachs/detailClient-coach.fxml", "Mon client");
            }
        } catch (Exception e) {
            displayError(errorText, e.getMessage());
        }
    }
}
