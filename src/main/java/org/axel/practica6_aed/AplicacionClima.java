package org.axel.practica6_aed;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AplicacionClima extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AplicacionClima.class.getResource("clima-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Ordenador de clima");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}