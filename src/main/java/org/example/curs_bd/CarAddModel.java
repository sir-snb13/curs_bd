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
                System.out.println("Машина с таким номером уже существует");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Вы не можете использовать такой номер машины");
                alert.show();
            } else {
                if (plate.isEmpty()) {
                    System.out.println("Пустой номер машины");
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setContentText("Пустой номер машины");
                    al.show();
                } else if (brand.isEmpty()) {
                    System.out.println("Пустое поле марки");
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setContentText("Пустая марка машины");
                    al.show();
                } else if (model.isEmpty()) {
                    System.out.println("Пустое поле модели");
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setContentText("Пустая модель машины");
                    al.show();
                } else if (color.isEmpty()) {
                    System.out.println("Пустое поле цвета");
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setContentText("Пустой цвет машины");
                    al.show();
                } else {
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
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null) {
                try {
                    psCheckUserExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
        }
    }

    public static ObservableList<Cars> getCars(ObservableList<Cars> carList) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
        PreparedStatement pst = connection.prepareStatement("SELECT * FROM cars Where owner_id = ?");
        pst.setInt(1, Singleton.getInstance().getId());
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            carList.add(new Cars(rs.getString("license_plate"), rs.getString("model"), rs.getString("brand"), rs.getString("color"), rs.getInt("mileage")));
        }
        rs.close();
        pst.close();
        connection.close();
        return carList;
    }
    public static void goBack(ActionEvent event){{
        changeSceneBack(event, "loggedCl.fxml");
    }}
}
