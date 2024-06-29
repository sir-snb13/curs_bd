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
        try {
            // Ensure the correct path to the FXML file
            FXMLLoader loader = new FXMLLoader(ServicesAddModel.class.getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 700, 400));
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Scene change error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void changeSceneBack(ActionEvent event, String fxmlFile) {
        changeScene(event, fxmlFile);
    }

    public static void serviceAdd(ActionEvent event, Date begin, Date end, Double hours, Integer id, String category) {
        if (category.isEmpty()) {
            showAlert("Error", "Пустой номер машины");
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
             PreparedStatement psInsert = connection.prepareStatement("INSERT INTO services (start_date, end_date, hours_worked, car_id, employee_id, category) VALUES (?, ?, ?, ?, ?, ?)")) {

            psInsert.setDate(1, begin);
            psInsert.setDate(2, end);
            psInsert.setDouble(3, hours);
            psInsert.setInt(4, id);
            psInsert.setInt(5, Singleton.getInstance().getId());
            psInsert.setString(6, category);

            psInsert.executeUpdate();
            changeScene(event, "/org/example/curs_bd/servicesAdd.fxml");

        } catch (SQLException e) {
            showAlert("Error", "SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void initializeServices(TableColumn<Services, Integer> idSCol,
                                          TableColumn<Services, Date> beginCol,
                                          TableColumn<Services, Date> finishCol,
                                          TableColumn<Services, Double> hoursCol,
                                          TableColumn<Services, Integer> idCarCol,
                                          TableColumn<Services, String> categoryCol) {
        idSCol.setCellValueFactory(new PropertyValueFactory<>("service_id"));
        beginCol.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        finishCol.setCellValueFactory(new PropertyValueFactory<>("end_date"));
        hoursCol.setCellValueFactory(new PropertyValueFactory<>("hours_worked"));
        idCarCol.setCellValueFactory(new PropertyValueFactory<>("car_id"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
    }

    public static ObservableList<Services> getServices(ObservableList<Services> servicesList) throws SQLException {
        String query = "SELECT * FROM services WHERE employee_id = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
             PreparedStatement pst = connection.prepareStatement(query)) {

            pst.setInt(1, Singleton.getInstance().getId());
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int serviceId = rs.getInt("service_id");
                    Date dateBegin = rs.getDate("start_date");
                    Date dateEnd = rs.getDate("end_date");
                    double hoursWorked = rs.getDouble("hours_worked");
                    int carId = rs.getInt("car_id");
                    String category = rs.getString("category");

                    servicesList.add(new Services(serviceId, dateBegin, dateEnd, hoursWorked, carId, category));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return servicesList;
    }

    public static void goBack(ActionEvent event) {
        changeSceneBack(event, "/org/example/curs_bd/loggedEmp.fxml");
    }

    private static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }
}
