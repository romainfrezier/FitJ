package com.fitj;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class ViewSetter {
    public ViewSetter() {}

    public static Scene getScene(String viewName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(viewName));
        return new Scene(fxmlLoader.load());
    }
}
