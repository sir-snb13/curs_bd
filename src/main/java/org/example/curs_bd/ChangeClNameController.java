package org.example.curs_bd;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangeClNameController implements Initializable {


    @FXML
    private TextField nameCl;
    @FXML
    private Button changeClName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        changeClName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ChangeClNameModel.changeName(event, nameCl.getText());
            }

        });


    }
}
