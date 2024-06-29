package org.example.curs_bd;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangeEmpPassController implements Initializable {

    @FXML
    private TextField passEmp;
    @FXML
    private Button changeEmpPass;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        changeEmpPass.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ChangeEmpPassModel.changeEmpPass(event, passEmp.getText());
            }

        });

    }

}
