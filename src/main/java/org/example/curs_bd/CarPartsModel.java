package org.example.curs_bd;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class CarPartsModel {

    public static void changeScene(ActionEvent event, String fxmlFile) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(RegModel.class.getResource(fxmlFile));
            root = loader.load();
            CarPartsBuyController carPartsBuyController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 700, 400));
        stage.show();
    }
    public static void changeSceneBack(ActionEvent event, String fxmlFile) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(RegModel.class.getResource(fxmlFile));
            root = loader.load();
            LoggedClController loggedClController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 700, 400));
        stage.show();
    }

    public static void initializePartsTable(TableColumn<CarParts, String> service_nameCol,
                                          TableColumn<CarParts, String> partsCol,
                                          TableColumn<CarParts, Integer> carMileageCol,
                                          TableColumn<CarParts, String> modelCol,
                                          TableColumn<CarParts, Integer> priceCol) {
        Connection connection = null;
        PreparedStatement psCheckUser = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
            psCheckUser = connection.prepareStatement("SELECT s.category AS service_name, GROUP_CONCAT(p.category SEPARATOR ', ') AS parts, c.mileage AS car_mileage, c.model AS car_model, SUM(p.price) AS total_parts_cost FROM services s JOIN cars c ON s.car_id = c.id JOIN owners o ON c.owner_id = o.owner_id LEFT JOIN parts p ON s.service_id = p.service_id WHERE o.owner_id = ? GROUP BY s.service_id, s.category, c.mileage, c.model;");
            psCheckUser.setInt(1, Singleton.getInstance().getId());
            service_nameCol.setCellValueFactory(new PropertyValueFactory<>("service_name"));
            partsCol.setCellValueFactory(new PropertyValueFactory<>("parts"));
            carMileageCol.setCellValueFactory(new PropertyValueFactory<>("car_mileage"));
            modelCol.setCellValueFactory(new PropertyValueFactory<>("car_model"));
            priceCol.setCellValueFactory(new PropertyValueFactory<>("total_parts_cost"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObservableList<CarParts> getCarParts(ObservableList<CarParts> carPartsList) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
        PreparedStatement pst = connection.prepareStatement("SELECT s.category AS service_name, GROUP_CONCAT(p.category SEPARATOR ', ') AS parts, c.mileage AS car_mileage, c.model AS car_model, SUM(p.price) AS total_parts_cost FROM services s JOIN cars c ON s.car_id = c.id JOIN owners o ON c.owner_id = o.owner_id LEFT JOIN parts p ON s.service_id = p.service_id WHERE o.owner_id = ? GROUP BY s.service_id, s.category, c.mileage, c.model;");
        pst.setInt(1, Singleton.getInstance().getId());
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            carPartsList.add(new CarParts(rs.getString("service_name"), rs.getString("parts"), rs.getInt("car_mileage"), rs.getString("car_model"), rs.getInt("total_parts_cost")));
        }
        rs.close();
        pst.close();
        connection.close();
        return carPartsList;
    }
    public static void goBack(ActionEvent event){{
        changeSceneBack(event, "loggedCl.fxml");
    }}
}
