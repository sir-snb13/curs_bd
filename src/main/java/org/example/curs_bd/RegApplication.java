package org.example.curs_bd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("signUp.fxml"));
            stage.setTitle("Железка-авто");
            stage.setScene(new Scene(root, 700, 400));
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
