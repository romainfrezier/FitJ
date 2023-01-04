package com.fitj.controllers.exercices;

import com.fitj.classes.Admin;
import com.fitj.classes.Client;
import com.fitj.classes.Exercice;
import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.Facade;
import com.fitj.facades.FacadeExercice;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.List;

/**
 * Controller générique des pages exercices
 * @see Controller
 * @author Paul Merceur, Romain Frezier
 */
public abstract class ControllerExercice extends Controller {

    /**
     * Facade pour les exercices
     */
    final FacadeExercice exerciceFacade = FacadeExercice.getInstance();

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages relatives aux admins
     */
    private final String admin = "admins/";

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages relatives aux exercices
     */
    private final String exercice = "exercices/";

    /**
     * Methode permettant de se rendre sur la page mon espace
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToMonEspace(Control controlEl) throws BadPageException {
        goToPage(controlEl, admin + "monEspace-admin.fxml", "MonEspace");
    }

    /**
     * Methode permettant de se rendre sur la page d'ajout d'un exercice
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToAddExercice(Control controlEl) throws BadPageException {
        goToPage(controlEl, exercice + "create-exercice.fxml", "Création d'un exercice");
    }

    /**
     * Methode permettant de se rendre sur la page de modification d'un exercice
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToUpdateExercice(Control controlEl) throws BadPageException {
        goToPage(controlEl, exercice + "update-exercice.fxml", "Modification d'un exercice");
    }

    /**
     * Methode qui initialise la liste des exercices
     * @param listView ListView, liste des exercices
     */
    void initializeExerciceList(ListView<Exercice> listView, List<Exercice> items) {
        super.initializeList(listView, items, new Callback<>() {
            @Override
            public ListCell<Exercice> call(ListView<Exercice> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Exercice item, boolean empty) {
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
}
