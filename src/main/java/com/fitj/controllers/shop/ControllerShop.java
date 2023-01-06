package com.fitj.controllers.shop;

import com.fitj.classes.Produit;
import com.fitj.controllers.Controller;
import com.fitj.facades.*;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.List;

public abstract class ControllerShop extends Controller {

    final FacadeProgrammeNutrition facadeProgrammeNutrition = FacadeProgrammeNutrition.getInstance();
    final FacadeProgrammeSportif facadeProgrammeSportif = FacadeProgrammeSportif.getInstance();
    final FacadePack facadePack = FacadePack.getInstance();
    final FacadeSeance facadeSeance = FacadeSeance.getInstance();

    public <T> void initializeProduitList(ListView<T> listView, List<T> items) {
        super.initializeList(listView, items, new Callback<ListView<T>, ListCell<T>>() {
            @Override
            public ListCell<T> call(ListView<T> listView) {
                return new ListCell<T>() {
                    @Override
                    protected void updateItem(T item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            if (item instanceof Produit) {
                                setText(((Produit) item).getNom());
                            } else {
                                setText("");
                            }
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
    }
}
