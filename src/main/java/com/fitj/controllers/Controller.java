package com.fitj.controllers;

import com.fitj.App;
import com.fitj.exceptions.BadPageException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;


/**
 * Controller générique
 * @author Romain Frezier
 */
public abstract class Controller {

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
        return new Scene(fxmlLoader.load());
    }

    /**
     * Attribut permettant de stocker l'identifiant d'un objet selectionné dans une liste
     */
    private static int idObjectSelected;

    /**
     * Attribut permettant de stocker l'objet selectionné dans une liste
     */
    private static Object objectSelected;

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
}