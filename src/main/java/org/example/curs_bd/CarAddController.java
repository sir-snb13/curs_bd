package org.example.curs_bd;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CarAddController implements Initializable {

    @FXML
    private TextField brand;

    @FXML
    private TextField color;

    @FXML
    private Button logOut;

    @FXML
    private TextField mileage;

    @FXML
    private TextField model;

    @FXML
    private TextField plate;
    @FXML
    private Button add;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CarAddModel.carAdd(event, plate.getText(), brand.getText(), model.getText(), color.getText(), mileage.getText(), Singleton.getInstance().getId());
            }
        });
    }
}
