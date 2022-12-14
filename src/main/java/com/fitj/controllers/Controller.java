package com.fitj.controllers;

import com.fitj.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Controller {
    public void goToPage(Control controlEl, String viewName, String pageName) throws IOException {
        Stage stage = (Stage) controlEl.getScene().getWindow();
        Scene scene = getScene(viewName);
        stage.setResizable(false);
        stage.setTitle(pageName);
        stage.setScene(scene);
        stage.show();
    }

    public static void startAppFX(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("visitor-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("Welcome to FitJ");
        stage.setScene(scene);
        stage.show();
    }

    private Scene getScene(String viewName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(viewName));
        return new Scene(fxmlLoader.load());
    }

}