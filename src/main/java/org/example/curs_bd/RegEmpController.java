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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegEmpController implements Initializable {
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
    public void reg(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("signUp.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    private TextField nameEmp;

    @FXML
    private Button backEmp;

    @FXML
    private Button signUpEmp;

    @FXML
    private Button loginEmployee;

    @FXML
    private TextField adrEmp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginEmployee.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EmpUtil.logInEmp(event, nameEmp.getText(), adrEmp.getText());
            }
        });
        signUpEmp.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EmpUtil.signUpEmp(event, nameEmp.getText(), adrEmp.getText());
            }
        }));
    }
}