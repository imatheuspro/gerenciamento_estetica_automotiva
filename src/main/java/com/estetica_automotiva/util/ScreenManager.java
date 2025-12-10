package com.estetica_automotiva.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenManager {

    private static Stage stage;

    public static void setStage(Stage s) {
        stage = s;
    }

    public static void changeScene(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("/com/estetica_automotiva/view/" + fxml));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

