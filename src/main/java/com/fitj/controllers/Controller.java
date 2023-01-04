package com.fitj.controllers;

import com.fitj.App;
import com.fitj.exceptions.BadPageException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;


/**
 * Controller générique
 * @author Romain Frezier
 */
public abstract class Controller {

    /**
     * Previous page name
     */
    private static String previousPageName;

    /**
     * Attribut permettant de stocker l'identifiant d'un objet selectionné dans une liste
     */
    private static int idObjectSelected;

    /**
     * Attribut permettant de stocker l'objet selectionné dans une liste
     */
    private static Object objectSelected;

    /**
     * Methode pour changer de page à partir d'un élément de contrôle de la page
     * @param controlEl Control, élément de contrôle de la page
     * @param viewName String, nom de la vue dans les ressources
     * @param pageName String, nom de la page
     * @throws BadPageException si la vue n'existe pas
     */
    public void goToPage(Control controlEl, String viewName, String pageName) throws BadPageException {
        Stage stage = (Stage) controlEl.getScene().getWindow();
        Scene scene;
        try {
            scene = getScene(viewName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadPageException("La page " + pageName + " n'existe pas");
        }
        stage.setResizable(false);
        stage.setTitle(pageName);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Methode static pour démarrer l'application JavaFX
     * @param stage Stage, fenêtre de l'application
     * @throws IOException si la vue n'existe pas
     */
    public static void startAppFX(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/users/visitor-view.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("components/recettes/list-recette.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/recettes/create-recette.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("components/aliments/list-aliment.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Welcome to FitJ");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Methode pour récupérer une scène à partir d'un nom de vue
     * @param viewName String, nom de la vue dans les ressources
     * @return Scene, la scène correspondant à la vue
     * @throws IOException si la vue n'existe pas
     */
    private Scene getScene(String viewName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/" + viewName));
        System.out.println("views/" + viewName);
        return new Scene(fxmlLoader.load());
    }


    /**
     * Méthode pour initialiser une liste de cellules
     * @param listView ListView, la liste à initialiser
     * @param items List, la liste d'objets à afficher
     * @param <T> Type de l'objet à afficher
     */
    public <T> void initializeList(ListView<T> listView, List<T> items, Callback<ListView<T>, ListCell<T>> cellFactory) {
        listView.getItems().clear();
        listView.setCellFactory(cellFactory);
        for (T item : items) {
            listView.getItems().add(item);
        }
    }

    /**
     * Getter pour l'objet selectionné dans une liste
     * @return int, l'objet selectionné dans une liste
     */
    public Object getObjectSelected() {
        return objectSelected;
    }

    /**
     * Setter pour l'objet selectionné dans une liste
     * @param objectSelected int, l'objet selectionné dans une liste
     */
    public static void setObjectSelected(Object objectSelected) {
        Controller.objectSelected = objectSelected;
    }

    /**
     * Getter pour l'identifiant d'un objet selectionné dans une liste
     * @return int, l'identifiant de l'objet selectionné dans une liste
     */
    public int getIdObjectSelected() {
        return idObjectSelected;
    }

    /**
     * Setter pour l'identifiant d'un objet selectionné dans une liste
     * @param idObjectSelected int, l'identifiant de l'objet selectionné dans une liste
     */
    public static void setIdObjectSelected(int idObjectSelected) {
        Controller.idObjectSelected = idObjectSelected;
    }

    /**
     * Affiche un message d'erreur
     * @param textEl Text, élément de texte de la page
     * @param message String, message à afficher
     */
    public void displayError(Text textEl, String message) {
        textEl.setText(message);
    }

    /**
     * Supprime le message d'erreur
     * @param textEl Text, élément de texte de la page
     */
    public void hideError(Text textEl) {
        textEl.setText("");
    }

    /**
     * Getter pour la page précédente
     * @return String, la page précédente
     */
    public String getPreviousPageName() {
        return previousPageName;
    }

    /**
     * Setter pour la page précédente
     * @param previousPageName String, la page précédente
     */
    public static void setPreviousPageName(String previousPageName) {
        Controller.previousPageName = previousPageName;
    }
}