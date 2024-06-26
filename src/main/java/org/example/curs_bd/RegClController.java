package org.example.curs_bd;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegClController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    public Scene getScene() {
        return scene;
    }
    public void switchBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("signUp.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private Label welcomeCl;


    @FXML
    private TextField adrCl;

    @FXML
    private Button backCl;

    @FXML
    private Button loginClient;

    @FXML
    private TextField nameCl;

    @FXML
    private TextField phoneCl;

    @FXML
    private Button signUpCl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginClient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RegModel.logInCl(event, nameCl.getText(), adrCl.getText(), phoneCl.getText());
            }
        });
        signUpCl.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RegModel.signUpCl(event, nameCl.getText(), adrCl.getText(), phoneCl.getText());
            }
        }));
    }
}