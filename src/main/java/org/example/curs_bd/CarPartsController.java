package org.example.curs_bd;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CarPartsController implements Initializable {
    ObservableList<CarParts> carPartsList = FXCollections.observableArrayList();

    @FXML
    private Button add;

    @FXML
    private Button back;

    @FXML
    private TableColumn<CarParts, Integer> carMileageCol;

    @FXML
    private TextField model;
    @FXML
    private Button delete;

    @FXML
    private Button update;

    @FXML
    private TableColumn<CarParts, String> modelCol;

    @FXML
    private TableColumn<CarParts, String> partsCol;

    @FXML
    private TableColumn<CarParts, Integer> priceCol;

    @FXML
    private TextField service;

    @FXML
    private TableColumn<CarParts, String> service_nameCol;

    @FXML
    private TableView<CarParts> servicesTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CarPartsModel.initializePartsTable(service_nameCol, partsCol, carMileageCol, modelCol, priceCol);
        try {
            CarPartsModel.getCarParts(carPartsList);
            servicesTable.setItems(carPartsList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CarPartsModel.changeScene(event,"carPartsBuy.fxml");
            }
        });
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CarPartsModel.goBack(event);
            }
        });
    }
}
