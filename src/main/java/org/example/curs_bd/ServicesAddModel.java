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

public class ServicesAddModel{

    public static void changeScene(ActionEvent event, String fxmlFile) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(RegModel.class.getResource(fxmlFile));
            root = loader.load();
            ServicesAddController servicesAddController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IOException occurred while loading the FXML file.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception occurred while changing scene.");
        }
        if (root != null) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 700, 400));
            stage.show();
        } else {
            System.err.println("Root is null, unable to change scene.");
        }
    }

    public static void changeSceneBack(ActionEvent event, String fxmlFile) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(RegModel.class.getResource(fxmlFile));
            root = loader.load();
            LoggedEmpConroller loggedEmpConroller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 700, 400));
        stage.show();
    }


    public static void serviceAdd(ActionEvent event, Date begin, Date end, Double hours, Integer id, String category) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;
        try {
            System.out.println("Параметры метода:");
            System.out.println("Begin Date: " + begin);
            System.out.println("End Date: " + end);
            System.out.println("Hours Worked: " + hours);
            System.out.println("Car ID: " + id);
            System.out.println("Category: " + category);

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");

            if (category.isEmpty()) {
                System.out.println("Пустой номер машины");
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Error");
                al.setHeaderText("Error");
                al.setContentText("Пустой номер машины");
                al.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO services (start_date, end_date, hours_worked, car_id, employee_id, category) VALUES (?, ?, ?, ?, ?, ?)");
                psInsert.setDate(1, begin);
                psInsert.setDate(2, end);
                psInsert.setDouble(3, hours);
                psInsert.setInt(4, id);
                psInsert.setInt(5, Singleton.getInstance().getId());
                psInsert.setString(6, category);

                psInsert.executeUpdate(); // Execute the insert statement

                changeScene(event, "servicesAdd.fxml");
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
        Connection connection = null;
        PreparedStatement psCheckUser = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
            psCheckUser = connection.prepareStatement("SELECT * FROM cars WHERE owner_id = ?");
            psCheckUser.setInt(1, Singleton.getInstance().getId());
            beginCol.setCellValueFactory(new PropertyValueFactory<>("date_begin"));
            finishCol.setCellValueFactory(new PropertyValueFactory<>("date_end"));
            hoursCol.setCellValueFactory(new PropertyValueFactory<>("hours_worked"));
            id_carCol.setCellValueFactory(new PropertyValueFactory<>("car_id"));
            categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

                // Логирование для отладки
                System.out.println("Fetched service: ");
                System.out.println("Start Date: " + dateBegin);
                System.out.println("End Date: " + dateEnd);
                System.out.println("Hours Worked: " + hoursWorked);
                System.out.println("Car ID: " + carId);
                System.out.println("Category: " + category);

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


    public static void goBack(ActionEvent event){{
        changeSceneBack(event, "loggedEmp.fxml");
    }}
}
