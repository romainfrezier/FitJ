package com.fitj.controllers.packs;
import com.fitj.classes.Pack;
import com.fitj.exceptions.BadPageException;
import com.fitj.exceptions.UnselectedItemException;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.util.List;

public class ControllerMesPacksAchatList extends ControllerPack{


    @FXML
    private Button detailPackButton;
    @FXML
    private ListView<Pack> listView;
    @FXML
    private Text errorText;

    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        initializePackList();
    }

    /**
     * Methode permettant d'initialiser la liste des packs
     */
    private void initializePackList() {
        try {
            List<Pack> packs = facadePack.getAllPacksByClient(Facade.currentClient.getId());
            super.initializePackList(listView, packs);
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }


    /**
     * Méthode appelée lors du clic sur un pack de la liste
     */
    @FXML
    private void selectItemPack(){
        setObjectSelected(listView.getSelectionModel().getSelectedItem());
    }



    /**
     * Methode permettant de se rendre sur la page de détail d'un pack
     */
    @FXML
    private void goToDetailPack() {
        try {
            super.hideError(errorText);
            checkSelected();
            setIdObjectSelected(listView.getSelectionModel().getSelectedItem().getId());
            super.goToDetailPack(detailPackButton);
        } catch (BadPageException | UnselectedItemException e) {
            e.printStackTrace();
            super.displayError(errorText, e.getMessage());
        }
    }



    /**
     * Methode permettant de verifier si un pack est selectionné
     * @throws UnselectedItemException si aucun pack n'est selectionné
     */
    private void checkSelected() throws UnselectedItemException {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            throw new UnselectedItemException("Vous devez sélectionner un pack");
        }
    }


}
