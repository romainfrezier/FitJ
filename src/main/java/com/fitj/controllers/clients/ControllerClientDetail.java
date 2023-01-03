package com.fitj.controllers.clients;

import com.fitj.classes.Client;
import com.fitj.classes.Materiel;
import com.fitj.classes.Sport;
import com.fitj.exceptions.BadPageException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class ControllerClientDetail extends ControllerClient {
    @FXML
    private Button goBackButton;
    @FXML
    private Text poidsClient;
    @FXML
    private Text tailleClient;
    @FXML
    private ListView<Sport> sportList;
    @FXML
    private ListView<Materiel> materielList;
    @FXML
    private ImageView clientImage;
    @FXML
    private Text errorText;
    @FXML
    private Text clientName;

    private Client client;

    @FXML
    private void initialize(){
        try {
            super.hideError(errorText);
            client = clientFacade.getClientById(((Client)getObjectSelected()).getId());
            clientName.setText(client.getPseudo());
            poidsClient.setText(String.valueOf(client.getPoids()));
            tailleClient.setText(String.valueOf(client.getTaille()));
            clientImage.setImage(new Image(client.getPhoto()));
            sportList.getItems().clear();
            materielList.getItems().clear();
            initializeClientSportList();
            initializeClientMaterielList();
        } catch (Exception e){
            super.displayError(errorText, e.getMessage());
        }

    }

    private void initializeClientMaterielList() {
        super.initializeList(materielList, client.getListeMateriel(), new Callback<ListView<Materiel>, ListCell<Materiel>>(){
            @Override
            public ListCell<Materiel> call(ListView<Materiel> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Materiel item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getNom());
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
    }

    private void initializeClientSportList() {
        super.initializeList(sportList, client.getListeSport(), new Callback<ListView<Sport>, ListCell<Sport>>() {
            @Override
            public ListCell<Sport> call(ListView<Sport> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Sport item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getNom());
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
    }

    @FXML
    private void goBack() {
        try {
            if (getPreviousPageName().equals("list-mesClient")) {
                super.goToPage(goBackButton, "coachs/mesClients-coach.fxml", "Mes clients");
            } else if (getPreviousPageName().equals("commande")){
                super.goToPage(goBackButton, "commandes/detail-commande.fxml", "Ma Commande");
            }

        } catch (BadPageException e) {
            displayError(errorText, e.getMessage());
        }
    }
}
