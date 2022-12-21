package com.fitj.controllers;

import com.fitj.App;
import com.fitj.controllers.sports.ControllerModifySport;
import com.fitj.exceptions.BadPageException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
     * @throws BadPageException si la vue n'existe pas
     */
    public void goToPage(Control controlEl, String viewName, String pageName) throws BadPageException {
        Stage stage = (Stage) controlEl.getScene().getWindow();
        Scene scene = null;
        try {
            scene = getScene(viewName);
        } catch (IOException e) {
            throw new BadPageException("La page " + pageName + " n'existe pas");
        }
        stage.setResizable(false);
        stage.setTitle(pageName);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Methode pour changer de page à partir d'un élément de contrôle de la page et changer de contrôleur
     * @param controlEl Control, élément de contrôle de la page
     * @param viewName String, nom de la vue dans les ressources
     * @param pageName String, nom de la page
     * @param controller Controller, nouveau contrôleur
     * @throws BadPageException si la vue n'existe pas
     */
    public void goToPage(Control controlEl, String viewName, String pageName, Controller controller) throws BadPageException {
        Stage stage = (Stage) controlEl.getScene().getWindow();
        Scene scene = null;
        try {
            scene = getScene(viewName, controller);
        } catch (IOException e) {
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
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/admins/monEspace-admin.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/users/visitor-view.fxml"));
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
     * Methode pour récupérer une scène à partir d'un nom de vue en changeant le contrôleur
     * @param viewName String, nom de la vue dans les ressources
     * @param controller Controller, nouveau contrôleur
     * @return Scene, la scène correspondant à la vue
     * @throws IOException si la vue n'existe pas
     */
    private Scene getScene(String viewName, Controller controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/" + viewName));
        Parent root = fxmlLoader.load();
        root.setUserData(controller);
        System.out.println("here");
        return new Scene(root);
    }

    private static int idObjectSelected;

    public int getIdObjectSelected() {
        return idObjectSelected;
    }

    public static void setIdObjectSelected(int idObjectSelected) {
        Controller.idObjectSelected = idObjectSelected;
    }
}