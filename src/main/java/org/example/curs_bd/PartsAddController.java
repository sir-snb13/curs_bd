package org.example.curs_bd;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class PartsAddController implements Initializable {

    @FXML
    private Button add;

    @FXML
    private Button back;

    @FXML
    private Button delete;

    @FXML
    private Button update;

    @FXML
    private TextField category;

    @FXML
    private TableColumn<Parts, String> categoryCol;

    @FXML
    private TextField model;

    @FXML
    private TableView<Parts> parts;

    @FXML
    private TableColumn<Parts, String> modelCol;

    @FXML
    private TextField price;

    @FXML
    private TableColumn<Parts, Double> priceCol;

    @FXML
    private TextField serial_num;

    @FXML
    private TableColumn<Parts, String> serialNumberCol;

    @FXML
    private TextField service_id;

    @FXML
    private TableColumn<Parts, Integer> serviceIdCol;

    private ObservableList<Parts> partsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Инициализация колонок таблицы
            PartsAddModel.initializeParts(categoryCol, priceCol, modelCol, serialNumberCol, serviceIdCol);

            // Загрузка данных из базы данных
            PartsAddModel.getParts(partsList);
            parts.setItems(partsList);

            // Обработка нажатия на кнопку "Добавить"
            add.setOnAction(event -> {
                try {
                    PartsAddModel.partAdd(event, category.getText(), Double.parseDouble(price.getText()), model.getText(), serial_num.getText(), Integer.parseInt(service_id.getText()));
                    // Обновление таблицы после добавления новой запчасти
                    partsList.clear();
                    PartsAddModel.getParts(partsList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            // Обработка нажатия на кнопку "Назад"
            back.setOnAction(event -> PartsAddModel.goBack(event));

            // Обработка нажатия на кнопку "Обновить"
            update.setOnAction(event -> PartsAddModel.changeScene(event, "partsAdd.fxml"));

            // Обработка нажатия на кнопку "Удалить"
            delete.setOnAction(event -> {
                try {
                    PartsAddModel.partDel(event, category.getText(), Double.parseDouble(price.getText()), model.getText(), serial_num.getText(), Integer.parseInt(service_id.getText()));
                    // Обновление таблицы после удаления запчасти
                    partsList.clear();
                    PartsAddModel.getParts(partsList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
