package org.example.curs_bd;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangeClAddrController implements Initializable {


    @FXML
    private TextField adrCl;
    @FXML
    private Button changeClAddress;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        changeClAddress.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ChangeClAddrModel.changeAddress(event, adrCl.getText());
            }

        });

    }
}
