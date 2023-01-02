package com.fitj.controllers.programmes.programmesSportifs;

import com.fitj.classes.*;
import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.Facade;
import com.fitj.facades.FacadeProgrammeSportif;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.List;

public abstract class ControllerProgrammeSportif extends Controller {

    /**
     * Facade pour les programmes sportifs
     */
    final FacadeProgrammeSportif facadeProgrammeSportif = FacadeProgrammeSportif.getInstance();

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages accessibles aux admins
     */
    private final String admin = "admins/";

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages relatives aux coachs
     */
    private final String coach = "coachs/";

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages relatives aux clients
     */
    private final String client = "clients/";

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages relatives aux admins
     */
    private final String programmeSportif = "programmes/programmesSportifs/";

    /**
     * Methode permettant de se rendre sur la page mon espace
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToMonEspace(Control controlEl) throws BadPageException {
        if (Facade.currentClient instanceof Admin) {
            goToPage(controlEl, admin + "monEspace-admin.fxml", "Mon Compte");
        } else if (Facade.currentClient instanceof Coach) {
            goToPage(controlEl,  coach + "monEspace-coach.fxml", "Mon Compte");
        }
        else {
            goToPage(controlEl,  client + "monEspace-client.fxml", "Mon Compte");
        }
    }

    /**
     * Methode permettant de se rendre sur la page d'ajout d'un programme sportif
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToAddProgrammeSportif(Control controlEl) throws BadPageException {
        goToPage(controlEl, programmeSportif + "create-programmeSportif.fxml", "Création d'un programme sportif");
    }


    /**
     * Methode permettant de se rendre sur la page de modification d'un programme sportif
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToUpdateProgrammeSportif(Control controlEl) throws BadPageException {
        goToPage(controlEl, programmeSportif + "update-programmeSportif.fxml", "Modification d'un programme sportif");
    }

    /**
     * Methode permettant de se rendre sur la page de détail d'un programme sportif
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToDetailProgrammeSportif(Control controlEl) throws BadPageException {
        goToPage(controlEl, programmeSportif + "detail-programmeSportif.fxml", "Détail d'un programme sportif");
    }

    /**
     * Methode permettant de se rendre sur la page de détail d'une séance
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToDetailSeance(Control controlEl) throws BadPageException {
        goToPage(controlEl, "seance/" + "detail-seance.fxml", "Détail d'une séance");
    }

    void initializeProgrammeSportifList(ListView<ProgrammeSportif> listView, List<ProgrammeSportif> items) {
        super.initializeList(listView, items, new Callback<>() {
            @Override
            public ListCell<ProgrammeSportif> call(ListView<ProgrammeSportif> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(ProgrammeSportif item, boolean empty) {
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

    void initializeSeanceList(ListView<Seance> listView, List<Seance> recettes) {
        super.initializeList(listView, recettes, new Callback<ListView<Seance>, ListCell<Seance>>() {
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
    }
}
