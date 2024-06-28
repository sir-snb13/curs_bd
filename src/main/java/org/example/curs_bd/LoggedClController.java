package org.example.curs_bd;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoggedClController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void switchCarAdd(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("carAdd.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void switchUpdateData(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("updateClData.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void logOut(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("signUp.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Singleton.getInstance().setId(null);
        Singleton.getInstance().setName(null);
    }
    public void switchCarParts(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("carParts.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Singleton.getInstance().setId(null);
        Singleton.getInstance().setName(null);
    }
    @FXML
    private Label welcomeCl;

    @FXML
    private Button addCar;
    @FXML
    private Button logOut;

    @FXML
    private Button change;
    private Button carParts;

    @FXML
    private Label welcomeEmp;

    public void setClInf(String username){
        welcomeCl.setText("Добро пожаловать " + username + "!");
    }

}
