package com.estetica_automotiva;

import com.estetica_automotiva.util.ScreenManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        ScreenManager.setStage(stage);
        ScreenManager.changeScene("menu.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}
