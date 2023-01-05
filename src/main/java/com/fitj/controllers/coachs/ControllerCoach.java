package com.fitj.controllers.coachs;

import com.fitj.classes.Client;
import com.fitj.classes.Coach;
import com.fitj.controllers.Controller;
import com.fitj.facades.Facade;
import com.fitj.facades.FacadeCoach;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.List;

/**
 * Controller générique pour les pages accessibles aux coachs
 * @see Controller
 * @author Paco Munnariz, Romain Frezier
 */
public abstract class ControllerCoach extends Controller {

    /**
     * Facade pour les coachs
     */
    final FacadeCoach coachFacade = FacadeCoach.getInstance();

    void initializeCoachList(ListView<Coach> listView, List<Coach> items) {
        super.initializeList(listView, items, new Callback<>() {
            @Override
            public ListCell<Coach> call(ListView<Coach> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Coach item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            if (item.getId() == Facade.currentClient.getId()){
                                setText(item.getPseudo() + " (moi)");
                            } else {
                                setText(item.getPseudo());
                            }
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
    }

    void initializeClientList(ListView<Client> listView, List<Client> items) {
        super.initializeList(listView, items, new Callback<>() {
            @Override
            public ListCell<Client> call(ListView<Client> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Client item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getPseudo());
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
    }
}
