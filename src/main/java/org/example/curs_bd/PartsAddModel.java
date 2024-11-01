package org.example.curs_bd;

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

public class PartsAddModel {

    public static void changeScene(ActionEvent event, String fxmlFile) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(PartsAddModel.class.getResource(fxmlFile));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Scene change error: " + e.getMessage());
            alert.show();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 700, 400));
        stage.show();
    }

    public static void partAdd(ActionEvent event, String category, double price, String model, String serialNumber, int serviceId) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");

            if (category.isEmpty() || model.isEmpty() || serialNumber.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Все поля должны быть заполнены");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO parts (category, price, model, serial_number, service_id) VALUES (?, ?, ?, ?, ?)");
                psInsert.setString(1, category);
                psInsert.setDouble(2, price);
                psInsert.setString(3, model);
                psInsert.setString(4, serialNumber);
                psInsert.setInt(5, serviceId);

                psInsert.executeUpdate();
                changeScene(event, "partsAdd.fxml");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("SQL Error: " + e.getMessage());
            alert.show();
        } finally {
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

    public static void partDel(ActionEvent event, String category, double price, String model, String serialNumber, int serviceId) {
        Connection connection = null;
        PreparedStatement psDelete = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");

            psDelete = connection.prepareStatement("DELETE FROM parts WHERE category = ? AND price = ? AND model = ? AND serial_number = ? AND service_id = ?");
            psDelete.setString(1, category);
            psDelete.setDouble(2, price);
            psDelete.setString(3, model);
            psDelete.setString(4, serialNumber);
            psDelete.setInt(5, serviceId);

            psDelete.executeUpdate();
            changeScene(event, "partsAdd.fxml");
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("SQL Error: " + e.getMessage());
            alert.show();
        } finally {
            if (psDelete != null) {
                try {
                    psDelete.close();
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

    public static void partUpdate(ActionEvent event, String category, double price, String model, String serialNumber, int serviceId) {
        Connection connection = null;
        PreparedStatement psUpdate = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");

            psUpdate = connection.prepareStatement("UPDATE parts SET category = ?, price = ?, model = ?, serial_number = ?, service_id = ? WHERE serial_number = ?");
            psUpdate.setString(1, category);
            psUpdate.setDouble(2, price);
            psUpdate.setString(3, model);
            psUpdate.setString(4, serialNumber);
            psUpdate.setInt(5, serviceId);
            psUpdate.setString(6, serialNumber);

            psUpdate.executeUpdate();
            changeScene(event, "partsAdd.fxml");
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("SQL Error: " + e.getMessage());
            alert.show();
        } finally {
            if (psUpdate != null) {
                try {
                    psUpdate.close();
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

    public static void initializeParts(TableColumn<Parts, String> categoryCol,
                                       TableColumn<Parts, Double> priceCol,
                                       TableColumn<Parts, String> modelCol,
                                       TableColumn<Parts, String> serialNumberCol,
                                       TableColumn<Parts, Integer> serviceIdCol) {
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        serialNumberCol.setCellValueFactory(new PropertyValueFactory<>("serial_number"));
        serviceIdCol.setCellValueFactory(new PropertyValueFactory<>("service_id"));
    }

    public static ObservableList<Parts> getParts(ObservableList<Parts> partsList) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
            pst = connection.prepareStatement("SELECT * FROM parts");
            rs = pst.executeQuery();

            while (rs.next()) {
                String category = rs.getString("category");
                double price = rs.getDouble("price");
                String model = rs.getString("model");
                String serialNumber = rs.getString("serial_number");
                int serviceId = rs.getInt("service_id");

                partsList.add(new Parts(category, price, model, serialNumber, serviceId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Пробросить исключение для дальнейшей обработки
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
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

        return partsList;
    }

    public static void goBack(ActionEvent event) {
        changeScene(event, "loggedEmp.fxml");
    }
}
