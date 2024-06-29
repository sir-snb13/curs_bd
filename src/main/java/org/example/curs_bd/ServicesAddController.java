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
import java.sql.Date;
import java.util.ResourceBundle;

public class ServicesAddController implements Initializable {
    Cars car = null;
    ObservableList<Services> servicesList = FXCollections.observableArrayList();

    @FXML
    private Button add;

    @FXML
    private Button back;

    @FXML
    private TextField begin;

    @FXML
    private TableColumn<Services, Date> beginCol;

    @FXML
    private TextField category;

    @FXML
    private TableColumn<Services, String> categoryCol;

    @FXML
    private TextField color;

    @FXML
    private TextField finish;

    @FXML
    private TableColumn<Services, Date> finishCol;

    @FXML
    private TextField hours;

    @FXML
    private TableColumn<Services, Double> hoursCol;

    @FXML
    private TableColumn<Services, Integer> id_carCol;

    @FXML
    private TableView<Services> service;

    @FXML
    void idCar(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServicesAddModel.initializeServices(beginCol, finishCol, hoursCol, id_carCol, categoryCol);
        try {
            ServicesAddModel.getServices(servicesList);
            service.setItems(servicesList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ServicesAddModel.serviceAdd(event, Date.valueOf(begin.getText()), Date.valueOf(finish.getText()), Double.parseDouble(hours.getText()), Integer.parseInt(color.getText()), category.getText());
            }
        });
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ServicesAddModel.goBack(event);
                servicesList = FXCollections.observableArrayList();
            }
        });
    }

}
