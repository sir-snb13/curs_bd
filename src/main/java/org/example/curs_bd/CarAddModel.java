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

public class CarAddModel {

    public static void changeScene(ActionEvent event, String fxmlFile) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(RegModel.class.getResource(fxmlFile));
            root = loader.load();
            CarAddController carAddController = loader.getController();
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

    public static void carAdd(ActionEvent event, String plate, String brand, String model, String color, String mileage, int owner_id) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM cars WHERE license_plate = ?");
            psCheckUserExists.setString(1, plate);
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                showAlert(Alert.AlertType.ERROR, "Машина с таким номером уже существует", "Вы не можете использовать такой номер машины");
            } else {
                if (validateCarFields(plate, brand, model, color)) {
                    psInsert = connection.prepareStatement("INSERT INTO cars (owner_id, model, brand, color, mileage, license_plate) VALUES (?, ?, ?, ?, ?, ?)");
                    psInsert.setInt(1, owner_id);
                    psInsert.setString(2, model);
                    psInsert.setString(3, brand);
                    psInsert.setString(4, color);
                    psInsert.setInt(5, Integer.parseInt(mileage));
                    psInsert.setString(6, plate);

                    psInsert.executeUpdate(); // Execute the insert statement

                    changeScene(event, "carAdd.fxml");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(resultSet, psCheckUserExists, psInsert, connection);
        }
    }

    public static void carDel(ActionEvent event, String plate, String brand, String model, String color, String mileage, int owner_id) {
        Connection connection = null;
        PreparedStatement psDelete = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");

            if (validateCarFields(plate, brand, model, color)) {
                psDelete = connection.prepareStatement("DELETE FROM cars WHERE owner_id = ? AND model = ? AND brand = ? AND color = ? AND mileage = ? AND license_plate = ?");
                psDelete.setInt(1, owner_id);
                psDelete.setString(2, model);
                psDelete.setString(3, brand);
                psDelete.setString(4, color);
                psDelete.setInt(5, Integer.parseInt(mileage));
                psDelete.setString(6, plate);

                psDelete.executeUpdate(); // Execute the delete statement

                changeScene(event, "carAdd.fxml");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, null, psDelete, connection);
        }
    }

    public static void initializeCarTable(TableColumn<Cars, String> plateCol,
                                          TableColumn<Cars, String> modelCol,
                                          TableColumn<Cars, String> brandCol,
                                          TableColumn<Cars, String> columnCol,
                                          TableColumn<Cars, Integer> mileageCol) {
        Connection connection = null;
        PreparedStatement psCheckUser = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
            psCheckUser = connection.prepareStatement("SELECT * FROM cars WHERE owner_id = ?");
            psCheckUser.setInt(1, Singleton.getInstance().getId());
            plateCol.setCellValueFactory(new PropertyValueFactory<>("license_plate"));
            modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
            brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
            columnCol.setCellValueFactory(new PropertyValueFactory<>("color"));
            mileageCol.setCellValueFactory(new PropertyValueFactory<>("mileage"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeResources(null, psCheckUser, null, connection);
        }
    }

    public static ObservableList<Cars> getCars(ObservableList<Cars> carList) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
            pst = connection.prepareStatement("SELECT * FROM cars WHERE owner_id = ?");
            pst.setInt(1, Singleton.getInstance().getId());
            rs = pst.executeQuery();
            while (rs.next()) {
                carList.add(new Cars(rs.getString("license_plate"), rs.getString("model"), rs.getString("brand"), rs.getString("color"), rs.getInt("mileage")));
            }
        } finally {
            closeResources(rs, pst, null, connection);
        }
        return carList;
    }

    public static void goBack(ActionEvent event) {
        changeSceneBack(event, "loggedCl.fxml");
    }

    private static boolean validateCarFields(String plate, String brand, String model, String color) {
        if (plate.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Пустой номер машины", "Пожалуйста, введите номер машины.");
            return false;
        } else if (brand.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Пустое поле марки", "Пожалуйста, введите марку машины.");
            return false;
        } else if (model.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Пустое поле модели", "Пожалуйста, введите модель машины.");
            return false;
        } else if (color.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Пустое поле цвета", "Пожалуйста, введите цвет машины.");
            return false;
        }
        return true;
    }

    private static void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    private static void closeResources(ResultSet rs, PreparedStatement ps1, PreparedStatement ps2, Connection connection) {
        try {
            if (rs != null) rs.close();
            if (ps1 != null) ps1.close();
            if (ps2 != null) ps2.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
