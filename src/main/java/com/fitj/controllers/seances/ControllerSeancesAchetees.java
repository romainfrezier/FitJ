package com.fitj.controllers.seances;

import com.fitj.classes.Client;
import com.fitj.classes.Seance;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.List;

/**
 * Controller de la page list-seancesAchetees.fxml
 * @see ControllerSeance
 * @author Paul Merceur
 */
public class ControllerSeancesAchetees extends ControllerSeance {

    // Composants FXML -----------------------------------------------------------------------------------------------
    @FXML
    private ListView<Seance> listView;

    @FXML
    private Text errorText;
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Méthode appelée lors du chargement de la page
     */
    @FXML
    private void initialize() {
        super.hideError(errorText);
        initializeSeanceList();
    }

    /**
     * Methode permettant d'initialiser la liste des seances
     */
    private void initializeSeanceList() {
        try {
            Client currentClient = Facade.currentClient;
            List<Seance> seances = seanceFacade.getAllSeancesFromClient(currentClient.getId());
            listView.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Seance> call(ListView<Seance> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Seance item, boolean empty) {
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
            for (Seance seance : seances) {
                listView.getItems().add(seance);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }


}
