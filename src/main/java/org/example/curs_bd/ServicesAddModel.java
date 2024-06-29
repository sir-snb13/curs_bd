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

public class ServicesAddModel {

    public static void changeScene(ActionEvent event, String fxmlFile) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(ServicesAddModel.class.getResource(fxmlFile));
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

    public static void changeSceneBack(ActionEvent event, String fxmlFile) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(ServicesAddModel.class.getResource(fxmlFile));
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

    public static void serviceAdd(ActionEvent event, Date begin, Date end, Double hours, Integer id, String category) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
            if (!category.isEmpty()) {
                psInsert = connection.prepareStatement("INSERT INTO services (start_date, end_date, hours_worked, car_id, employee_id, category) VALUES (?, ?, ?, ?, ?, ?)");
                psInsert.setDate(1, begin);
                psInsert.setDate(2, end);
                psInsert.setDouble(3, hours);
                psInsert.setInt(4, id);
                psInsert.setInt(5, Singleton.getInstance().getId());
                psInsert.setString(6, category);

                psInsert.executeUpdate(); // Execute the insert statement

                changeScene(event, "carAdd.fxml");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Пустой номер машины");
                alert.show();
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

    public static void initializeServices(TableColumn<Services, Date> beginCol,
                                          TableColumn<Services, Date> finishCol,
                                          TableColumn<Services, Double> hoursCol,
                                          TableColumn<Services, Integer> id_carCol,
                                          TableColumn<Services, String> categoryCol) {
        beginCol.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        finishCol.setCellValueFactory(new PropertyValueFactory<>("end_date"));
        hoursCol.setCellValueFactory(new PropertyValueFactory<>("hours_worked"));
        id_carCol.setCellValueFactory(new PropertyValueFactory<>("car_id"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
    }

    public static ObservableList<Services> getServices(ObservableList<Services> servicesList) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
            pst = connection.prepareStatement("SELECT * FROM services WHERE employee_id = ?");
            pst.setInt(1, Singleton.getInstance().getId());
            rs = pst.executeQuery();

            while (rs.next()) {
                Date dateBegin = rs.getDate("start_date");
                Date dateEnd = rs.getDate("end_date");
                double hoursWorked = rs.getDouble("hours_worked");
                int carId = rs.getInt("car_id");
                String category = rs.getString("category");

                servicesList.add(new Services(dateBegin, dateEnd, hoursWorked, carId, category));
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

        return servicesList;
    }

    public static void goBack(ActionEvent event) {
        changeSceneBack(event, "loggedEmp.fxml");
    }
}
