package org.example.curs_bd;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class RegClController {
    private Stage stage;
    private Scene scene;

    public Scene getScene() {
        return scene;
    }

    private Parent root;
    public void switchBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("signUp.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
/*
    public void signUpCl(ActionEvent event, String username, String )
*/

    @FXML
    private Button backCl;

    @FXML
    private Button loginClient;

    @FXML
    private Button signUpCl;


}
