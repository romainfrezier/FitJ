package com.fitj.controllers.commandes;

import com.fitj.classes.Coach;
import com.fitj.classes.Commande;
import com.fitj.classes.ProgrammePersonnalise;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ControllerCommandeDetail extends ControllerCommande{

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

    /**
     * Initialise la vue
     */
    @FXML
    public void initialize() {
        super.hideError(errorText);
        try {
            Commande commande = facadeCommande.getCommandeById(getIdObjectSelected());
            titleText.setText("Détail de la commande n°" + commande.getId());
            nomProduit.setText(commande.getProduit().getNom());
            prixProduit.setText(commande.getProduit().getPrix() + "€");
            descriptionProduit.getChildren().add(new Text(commande.getProduit().getDescription()));
            if (commande.getProduit() instanceof ProgrammePersonnalise) {
                demandeProduit.getChildren().add(new Text(commande.getDemande().toString()));
            } else {
                demandeProduit.setVisible(false);
                demandeLabel.setVisible(false);
            }
            if (!(Facade.currentClient instanceof Coach)) {
                headerClient.setVisible(false);
                repondreButton.setVisible(false);
            } else {
                headerCoach.setVisible(false);
            }
            // TODO: Afficher le boutons répondre que si le client est un coach et que l'état de la commande est en attente
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
        displayError(errorText, "Cette fonctionnalité n'est pas encore disponible");
    }
}
