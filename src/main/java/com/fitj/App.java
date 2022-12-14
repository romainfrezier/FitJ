package com.fitj;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import static com.fitj.controllers.Controller.startAppFX;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        startAppFX(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}