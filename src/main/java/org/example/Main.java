package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/views/Dashboard.fxml"));

        try {
            Parent root = fxmlLoader.load();
            Scene scene=new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Create Item");
            primaryStage.setMaximized(true);
            primaryStage.setResizable(true);
            primaryStage.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
