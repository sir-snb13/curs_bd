package org.example.curs_bd;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class LoggedEmpConroller {
    private Stage stage;
    private Scene scene;

    @FXML
    private Button changeEmpData;

    @FXML
    private Button logOut;

    @FXML
    private Button partsButton;

    @FXML
    private Button serviceButton;

    @FXML
    private Label welcomeEmp;

    public void logOut(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("signUp.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Singleton.getInstance().setId(null);
        Singleton.getInstance().setName(null);
    }

    public void setEmpInf(String username){
        welcomeEmp.setText("Добро пожаловать " + username + "!");
    }

}

