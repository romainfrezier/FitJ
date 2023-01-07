package com.fitj.controllers.materiels;

import com.fitj.classes.Materiel;
import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.FacadeMateriel;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.List;

/**
 * Controller générique des pages materiel
 */
public abstract class ControllerMateriel extends Controller {

    /**
     * Facade pour les materiels
     */
    final FacadeMateriel materielFacade = FacadeMateriel.getInstance();

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages accessibles aux admins
     */
    private final String admin = "admins/";

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages relatives aux admins
     */
    private final String materiel = "materiels/";

    /**
     * Methode permettant de se rendre sur la page mon espace
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToMonEspace(Control controlEl) throws BadPageException {
        goToPage(controlEl, admin + "monEspace-admin.fxml", "MonEspace");
    }

    /**
     * Methode permettant de se rendre sur la page d'ajout d'un materiel
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToAddMateriel(Control controlEl) throws BadPageException {
        goToPage(controlEl, materiel + "create-materiel.fxml", "Création d'un materiel");
    }

    /**
     * Methode permettant de se rendre sur la page d'ajout d'un materiel dans la liste du materiels d'un client
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToAddMyMateriel(Control controlEl) throws BadPageException {
        goToPage(controlEl, materiel + "add-mymateriel.fxml", "Ajout de materiel");
    }

    /**
     * Methode permettant de se rendre sur la page de modification d'un materiel
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToUpdateMateriel(Control controlEl) throws BadPageException {
        goToPage(controlEl, materiel + "update-materiel.fxml", "Modification d'un materiel");
    }

    /**
     * Methode qui initialise la liste des materiels
     * @param listView ListView, liste des materiels
     */
    void initializeMaterielList(ListView<Materiel> listView, List<Materiel> items) {
        super.initializeList(listView, items, new Callback<>() {
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
}

