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
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class CarAddController implements Initializable {
    Cars car = null;
    ObservableList<Cars> carList = FXCollections.observableArrayList();

    @FXML
    private Button add;

    @FXML
    private TextField brand;

    @FXML
    private TableColumn<Cars, String> brandCol;

    @FXML
    private TableView<Cars> cars;

    @FXML
    private TextField color;

    @FXML
    private TableColumn<Cars, String> colorCol;

    @FXML
    private Button back;

    @FXML
    private TextField mileage;

    @FXML
    private TableColumn<Cars, Integer> mileageCol;

    @FXML
    private TextField model;

    @FXML
    private TableColumn<Cars, String> modelCol;

    @FXML
    private TextField plate;

    @FXML
    private TableColumn<Cars, String> plateCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CarAddModel.initializeCarTable(plateCol, brandCol, modelCol, colorCol, mileageCol);
        try {
            CarAddModel.getCars(carList);
            cars.setItems(carList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CarAddModel.carAdd(event, plate.getText(), brand.getText(), model.getText(), color.getText(), mileage.getText(), Singleton.getInstance().getId());
            }
        });
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CarAddModel.goBack(event);
                carList = FXCollections.observableArrayList();
            }
        });

    }
}
