package com.fitj.controllers.coachs;

import com.fitj.classes.*;
import com.fitj.exceptions.BadPageException;
import com.fitj.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.List;

public class ControllerCoachDetail extends ControllerCoach{
    @FXML
    private AnchorPane headerAdmin;
    @FXML
    private AnchorPane headerClient;
    @FXML
    private AnchorPane headerCoach;
    @FXML
    private Button goBackButton;
    @FXML
    private Text poidsCoach;
    @FXML
    private Text tailleCoach;
    @FXML
    private ListView<Sport> sportList;
    @FXML
    private ListView<Seance> seanceList;
    @FXML
    private ListView<ProgrammeSportif> sportifList;
    @FXML
    private ListView<ProgrammeNutrition> nutritionList;
    @FXML
    private ListView<Pack> packList;
    @FXML
    private ImageView coachImage;
    @FXML
    private Text errorText;
    @FXML
    private Text coachName;

    private Coach coach;

    @FXML
    private void initialize(){
        try {
            super.hideError(errorText);
            coach = coachFacade.getCoachById(((Coach)getObjectSelected()).getId());
            coachName.setText(coach.getPseudo());
            poidsCoach.setText(String.valueOf(coach.getPoids()));
            tailleCoach.setText(String.valueOf(coach.getTaille()));
            coachImage.setImage(new Image(coach.getPhoto()));
            initializeSportList();
            initializeSeanceList();
            initializeNutritionList();
            initializeSportifList();
            initializePackList();
            if (Facade.currentClient instanceof Admin){
                headerAdmin.setVisible(true);
            } else if (Facade.currentClient instanceof Coach){
                headerCoach.setVisible(true);
            } else {
                headerClient.setVisible(true);
            }
        } catch (Exception e){
            super.displayError(errorText, e.getMessage());
        }

    }

    private void initializePackList() {
        try {
            List<Pack> packs = coachFacade.getAllPackByCoach(coach);
            super.initializeList(packList, packs, new Callback<ListView<Pack>, ListCell<Pack>>() {
                @Override
                public ListCell<Pack> call(ListView<Pack> param) {
                    return new ListCell<Pack>() {
                        @Override
                        protected void updateItem(Pack item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                setText(item.getNom() + " - " + item.getPrix() + "€");
                            }
                        }
                    };
                }
            });
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    private void initializeNutritionList() {
        try {
            List<ProgrammeNutrition> nutritions = coachFacade.getCoachProgrammeNutrition(coach);
            super.initializeList(nutritionList, nutritions, new Callback<ListView<ProgrammeNutrition>, ListCell<ProgrammeNutrition>>(){
                @Override
                public ListCell<ProgrammeNutrition> call(ListView<ProgrammeNutrition> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(ProgrammeNutrition item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                setText(item.getNom() + " - " + item.getPrix() + "€");
                            } else {
                                setText(null);
                            }
                        }
                    };
                }
            });
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    private void initializeSportifList() {
        try {
            List<ProgrammeSportif> sportifs = coachFacade.getCoachProgrammeSportifs(coach);
            super.initializeList(sportifList, sportifs, new Callback<ListView<ProgrammeSportif>, ListCell<ProgrammeSportif>>(){
                @Override
                public ListCell<ProgrammeSportif> call(ListView<ProgrammeSportif> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(ProgrammeSportif item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                setText(item.getNom() + " - " + item.getPrix() + "€");
                            } else {
                                setText(null);
                            }
                        }
                    };
                }
            });
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    private void initializeSeanceList() {
        try {
            List<Seance> seances = coachFacade.getSeanceByCoach(coach);
            super.initializeList(seanceList, seances, new Callback<ListView<Seance>, ListCell<Seance>>(){
                @Override
                public ListCell<Seance> call(ListView<Seance> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Seance item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                setText(item.getNom() + " - " + item.getPrix() + "€");
                            } else {
                                setText(null);
                            }
                        }
                    };
                }
            });
        } catch (Exception e){
            super.displayError(errorText, e.getMessage());
        }
    }

    private void initializeSportList() {
        super.initializeList(sportList, coach.getListeSport(), new Callback<ListView<Sport>, ListCell<Sport>>() {
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
    private void goBack() {
        try {
            if (getPreviousPageName().equals("list-coachs")) {
                String role;
                if (Facade.currentClient instanceof Admin) {
                    role = "admin";
                } else if (Facade.currentClient instanceof Coach) {
                    role = "coach";
                } else {
                    role = "client";
                }
                super.goToPage(goBackButton, role + "s/coachs-" + role + ".fxml", "Coachs");
            } else if (getPreviousPageName().equals("commande")){
                super.goToPage(goBackButton, "commandes/detail-commande.fxml", "Ma Commande");
            }

        } catch (BadPageException e) {
            displayError(errorText, e.getMessage());
        }
    }
}