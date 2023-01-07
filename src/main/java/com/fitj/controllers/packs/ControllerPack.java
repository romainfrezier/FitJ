package com.fitj.controllers.packs;

import com.fitj.classes.*;
import com.fitj.controllers.Controller;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.Facade;
import com.fitj.facades.FacadePack;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.List;

public abstract class ControllerPack extends Controller {

    /**
     * Facade pour les packs
     */
    final FacadePack facadePack = FacadePack.getInstance();

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages accessibles aux admins
     */
    private final String admin = "admins/";

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages relatives aux coachs
     */
    private final String coach = "coachs/";

    /**
     * Chemin du dossier dans lequel se trouve les ressources pour les pages relatives aux admins
     */
    private final String pack = "produits/packs/";

    /**
     * Methode permettant de se rendre sur la page mon espace
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToMonEspace(Control controlEl) throws BadPageException {
        if (Facade.currentClient instanceof Admin) {
            goToPage(controlEl, admin + "monEspace-admin.fxml", "Mon Compte");
        } else {
            goToPage(controlEl,  coach + "monEspace-coach.fxml", "Mon Compte");
        }
    }

    /**
     * Methode permettant de se rendre sur la page d'ajout d'un pack
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToAddPack(Control controlEl) throws BadPageException {
        goToPage(controlEl, pack + "create-pack.fxml", "Création d'un pack");
    }


    /**
     * Methode permettant de se rendre sur la page de modification d'un pack
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToUpdatePack(Control controlEl) throws BadPageException {
        goToPage(controlEl, pack + "update-pack.fxml", "Modification d'un pack");
    }

    /**
     * Methode permettant de se rendre sur la page de détail d'un pack
     * @param controlEl Control, élément de contrôle de la page
     * @throws BadPageException si la vue n'existe pas
     */
    void goToDetailPack(Control controlEl) throws BadPageException {
        setPreviousPageName("coco");
        goToPage(controlEl, pack + "detail-pack.fxml", "Détail d'un pack");
    }

    /**
     * Methode permettant de se rendre sur la page de détail d'un produit
     * @param controlEl Control, élément de contrôle de la page
     * @param produit Produit, le produit à afficher
     * @throws BadPageException si la vue n'existe pas
     */
    void goToDetailProduit(Control controlEl, Produit produit) throws BadPageException {
        if (produit instanceof ProgrammeNutrition) {
            goToPage(controlEl, "produits/programmes/programmesNutritions/detail-programmeNutrition.fxml", "Détail d'un programme de nutrition");
        } else if (produit instanceof ProgrammeSportif) {
            goToPage(controlEl, "produits/programmes/programmesSportifs/detail-programmeSportif.fxml", "Détail d'un programme sportif");
        }
        else if (produit instanceof Seance){
            goToPage(controlEl, "produits/seances/detail-seance.fxml", "Détail d'une séance");
        }
        else if (produit instanceof Pack){
            goToPage(controlEl, "produits/packs/detail-pack.fxml", "Détail d'un pack");
        }
    }

    void initializeProgrammeNutritionList(ListView<ProgrammeNutrition> listView, List<ProgrammeNutrition> items) {
        super.initializeList(listView, items, new Callback<>() {
            @Override
            public ListCell<ProgrammeNutrition> call(ListView<ProgrammeNutrition> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(ProgrammeNutrition item, boolean empty) {
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


    void initializePackList(ListView<Pack> listView, List<Pack> items) {
        super.initializeList(listView, items, new Callback<>() {
            @Override
            public ListCell<Pack> call(ListView<Pack> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Pack item, boolean empty) {
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

    void initializeProduitList(ListView<Produit> listView, List<Produit> items) {
        super.initializeList(listView, items, new Callback<>() {
            @Override
            public ListCell<Produit> call(ListView<Produit> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Produit item, boolean empty) {
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
