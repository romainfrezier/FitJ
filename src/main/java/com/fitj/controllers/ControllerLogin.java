package com.fitj.controllers;

import com.fitj.Constante;
import com.fitj.ViewSetter;
import com.fitj.facades.FacadeClient;
import com.fitj.facades.factory.FactoryFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerLogin {

    private final FacadeClient clientFacade = FactoryFacade.getInstance().getFacadeClient();
    @FXML
    private Button homeButton;
    @FXML
    private Button connect;
    @FXML
    private Button nouveau;
    @FXML
    private TextField login;
    @FXML
    private Text errorMessage;
    @FXML
    private TextField password;

    public void handleButtonConnect(ActionEvent event) {
        try {
            String result = clientFacade.connexion(login.getText(), password.getText());
            if (result.equals(Constante.CONNECTED)) {
                hideError();
                connect();
            } else {
                displayError(result);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goToRegister(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) nouveau.getScene().getWindow();
        Scene scene = ViewSetter.getScene("register-view.fxml");
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.show();
    }

    //Affiche un message d'erreur
    public void displayError(String message) {
        errorMessage.setText(message);
    }

    //Cache le message d'erreur
    public void hideError() {
        errorMessage.setText("");
    }

    //Connecte l'utilisateur et ouvre la fenêtre principale ptincipal-view.fxml
    public void connect() throws IOException {
        System.out.println("Connecté, maintenant ouvrir la fenêtre principale");
        //ouvrir la fenêtre principale
        Stage stage = (Stage) connect.getScene().getWindow();
        Scene scene = ViewSetter.getScene("principal-view.fxml");
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();

    }

    public void goToHome(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) homeButton.getScene().getWindow();
        Scene scene = ViewSetter.getScene("home-view.fxml");
        stage.setTitle("Welcome to FitJ");
        stage.setScene(scene);
        stage.show();
    }
}
