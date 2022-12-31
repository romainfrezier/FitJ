package com.fitj.controllers.coachs;

import com.fitj.classes.Client;
import com.fitj.classes.Materiel;
import com.fitj.classes.Sport;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.List;
import java.util.Optional;

/**
 * Controller de la page des clients du coach
 * @see ControllerCoach
 * @author Romain Frezier
 */
public class ControllerCoachMesClients extends ControllerCoach {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private Text poidsClient;
    @FXML
    private Text tailleClient;
    @FXML
    private Pane detailClient;
    @FXML
    private Button seeLessButton;
    @FXML
    private Text clientName;
    @FXML
    private ListView<Sport> sportList;
    @FXML
    private ListView<Materiel> materielList;
    @FXML
    private ImageView clientImage;
    @FXML
    private ListView<Client> listView;
    @FXML
    private Button seeMoreButton;
    @FXML
    private Text errorText;
    // ---------------------------------------------------------------------------------------------------------------

    private List<Client> clients;

    private Client clientSelected;

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        initializeClientList();
    }

    /**
     * Methode permettant d'initialiser la liste des clients
     */
    private void initializeClientList() {
        try {
            clients = coachFacade.getAllClientsForACoach(Facade.currentClient.getId());
            super.initializeClientList(listView, clients);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    @FXML
    private void selectClient() {
        setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
    }

    @FXML
    private void handleSeeMoreButton() {
        try {
            hideError(errorText);
            checkSelected();
            initializeClientDetail();
            detailClient.setVisible(true);
            listView.setVisible(false);
            seeMoreButton.setVisible(false);
        } catch (Exception e) {
            displayError(errorText, e.getMessage());
        }
    }

    private void checkSelected() throws UnselectedItemException {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Vous devez sélectionner un client");
        }
    }

    private void initializeClientDetail() {
        Optional<Client> result = clients.stream()
                .filter(client -> client.getId() == getIdObjectSelected())
                .findFirst();
        if (result.isPresent()){
            clientSelected = result.get();
            clientName.setText(clientSelected.getPseudo());
            poidsClient.setText(String.valueOf(clientSelected.getPoids()));
            tailleClient.setText(String.valueOf(clientSelected.getTaille()));
            clientImage.setImage(new Image(clientSelected.getPhoto()));
            sportList.getItems().clear();
            materielList.getItems().clear();
            initializeClientSportList();
            initializeClientMaterielList();
        }
    }

    private void initializeClientMaterielList() {
        super.initializeList(materielList, clientSelected.getListeMateriel(), new Callback<ListView<Materiel>, ListCell<Materiel>>(){
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
        super.initializeList(sportList, clientSelected.getListeSport(), new Callback<ListView<Sport>, ListCell<Sport>>() {
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
    private void hideDetail() {
        detailClient.setVisible(false);
        listView.setVisible(true);
        seeMoreButton.setVisible(true);
    }


}
