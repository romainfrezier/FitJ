package com.fitj.controllers;

import com.fitj.App;
import com.fitj.facades.Facade;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Control;
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
     * @throws IOException si la vue n'existe pas
     */
    public void goToPage(Control controlEl, String viewName, String pageName) throws IOException {
        Stage stage = (Stage) controlEl.getScene().getWindow();
        Scene scene = getScene(viewName);
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
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("visitor-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
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
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(viewName));
        return new Scene(fxmlLoader.load());
    }

}