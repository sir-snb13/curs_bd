package org.example.curs_bd;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangeEmpNameController implements Initializable {

    @FXML
    private TextField nameEmp;
    @FXML
    private Button changeEmpName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        changeEmpName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ChangeEmpNameModel.changeEmpName(event, nameEmp.getText());
            }

        });


    }

}
