package com.fitj.controllers.commandes;

import com.fitj.classes.Admin;
import com.fitj.classes.Coach;
import com.fitj.classes.Commande;
import com.fitj.comparators.CommandeComparator;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.List;

public class ControllerCommandeList extends ControllerCommande {

    @FXML
    private ListView<Commande> commandesListView;
    @FXML
    private Button seeMoreButton;
    @FXML
    private Text errorTextCommandes;

    @FXML
    private void initialize() {
        super.hideError(errorTextCommandes);
        initializeCommandes();
    }

    private void initializeCommandes() {
        try {
            List<Commande> commandes;
            if (Facade.currentClient instanceof Admin) {
                commandes = facadeCommande.getAllCommandes();
                List<Commande> commandesVendues = facadeCommande.getAllCommandesByIdCoach(Facade.currentClient.getId());
                List<Commande> commandesAchete = facadeCommande.getAllCommandesByIdClient(Facade.currentClient.getId());
                commandes.addAll(commandesVendues);
                commandes.addAll(commandesAchete);
            } else if (Facade.currentClient instanceof Coach){
                List<Commande> commandesVendues = facadeCommande.getAllCommandesByIdCoach(Facade.currentClient.getId());
                List<Commande> commandesAchete = facadeCommande.getAllCommandesByIdClient(Facade.currentClient.getId());
                commandes = commandesVendues;
                commandes.addAll(commandesAchete);
            } else {
                commandes = facadeCommande.getAllCommandesByIdClient(Facade.currentClient.getId());
            }
            commandes.sort(new CommandeComparator());
            super.initializeList(commandesListView, commandes, new Callback<ListView<Commande>, ListCell<Commande>>() {
                @Override
                public ListCell<Commande> call(ListView<Commande> param) {
                    return new ListCell<Commande>() {
                        @Override
                        protected void updateItem(Commande item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                if (Facade.currentClient instanceof Coach) {
                                    if (item.getCoach().getId() == Facade.currentClient.getId()) {
                                        setText(item.getId() + " - " + item.getProduit().getNom() + " (vendu)");
                                    } else {
                                        setText(item.getId() + " - " + item.getProduit().getNom() + " (acheté)");
                                    }
                                } else {
                                    setText(item.getId() + " - " + item.getCoach().getPseudo() + " - " + item.getProduit().getNom());
                                }

                            }
                        }
                    };
                }
            });
        } catch (Exception e) {
            super.displayError(errorTextCommandes, e.getMessage());
        }
    }

    @FXML
    private void handleClickListCommande() {
        setIdObjectSelected(commandesListView.getSelectionModel().getSelectedItem().getId());
    }

    @FXML
    private void handleSeeMoreButton() {
        try {
            checkSelectedCommande();
            goToPage(seeMoreButton, "commandes/detail-commande.fxml", "Détail de la commande");
        } catch (Exception e) {
            e.printStackTrace();
            super.displayError(errorTextCommandes, e.getMessage());
        }
    }

    private void checkSelectedCommande() throws UnselectedItemException {
        if (commandesListView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Vous devez sélectionner une commande");
        }
    }
}
