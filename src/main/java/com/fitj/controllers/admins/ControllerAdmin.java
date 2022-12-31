package com.fitj.controllers.admins;

import com.fitj.classes.Client;
import com.fitj.controllers.Controller;
import com.fitj.facades.FacadeAdmin;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.List;

/**
 * Controller générique pour les pages accessibles aux admins
 * @see Controller
 * @author Paco Munnariz, Romain Frezier
 */
public abstract class ControllerAdmin extends Controller {

    /**
     * Facade pour les admins
     */
    final FacadeAdmin adminFacade = FacadeAdmin.getInstance();

    void initializeClientList(ListView<Client> listView, List<Client> items) {
        super.initializeList(listView, items, new Callback<>() {
            @Override
            public ListCell<Client> call(ListView<Client> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Client item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getId() + ". " + item.getPseudo());
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
    }

}
