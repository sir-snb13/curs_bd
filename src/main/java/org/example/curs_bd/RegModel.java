package org.example.curs_bd;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class RegModel {

    public static void changeSceneEmp(ActionEvent event, String fxmlFile, String username) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(RegModel.class.getResource(fxmlFile));
            root = loader.load();
            LoggedEmpConroller loggedEmpController = loader.getController();
            loggedEmpController.setEmpInf(username);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Scene change error: " + e.getMessage());
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 700, 400));
        stage.show();
    }

    public static void changeSceneCl(ActionEvent event, String fxmlFile, String username) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(RegModel.class.getResource(fxmlFile));
            root = loader.load();
            LoggedClController loggedClController = loader.getController();
            loggedClController.setClInf(username);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Scene change error: " + e.getMessage());
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 700, 400));
        stage.show();
    }

    public static void signUpEmp(ActionEvent event, String username, String address, String password) {
        if (username.isEmpty() || address.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled");
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
             PreparedStatement psCheckUserExists = connection.prepareStatement("SELECT * FROM employees WHERE name = ? AND password = ?");
             PreparedStatement psInsert = connection.prepareStatement("INSERT INTO employees (name, address, password) VALUES (?, ?, ?)");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT employee_id, name FROM employees WHERE name = ? AND password = ?")) {

            psCheckUserExists.setString(1, username);
            psCheckUserExists.setString(2, password);
            try (ResultSet resultSet = psCheckUserExists.executeQuery()) {
                if (resultSet.isBeforeFirst()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "User already exists");
                    return;
                }
            }

            psInsert.setString(1, username);
            psInsert.setString(2, address);
            psInsert.setString(3, password);
            psInsert.executeUpdate();

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String employeeName = resultSet.getString("name");
                    int employeeId = resultSet.getInt("employee_id");
                    Singleton.getInstance().setName(employeeName);
                    Singleton.getInstance().setId(employeeId);
                }
            }

            changeSceneEmp(event, "loggedEmp.fxml", username);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "SQL Error: " + e.getMessage());
        }
    }

    public static void logInEmp(ActionEvent event, String username, String address, String password) {
        if (username.isEmpty() || address.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled");
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT address, password, employee_id, name FROM employees WHERE name = ? AND password = ?")) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "User not found");
                    return;
                }

                while (resultSet.next()) {
                    int employeeId = resultSet.getInt("employee_id");
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedAddress = resultSet.getString("address");
                    if (retrievedPassword.equals(password) && retrievedAddress.equals(address)) {
                        Singleton.getInstance().setName(resultSet.getString("name"));
                        Singleton.getInstance().setId(employeeId);
                        changeSceneEmp(event, "loggedEmp.fxml", username);
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Incorrect credentials");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "SQL Error: " + e.getMessage());
        }
    }

    public static void signUpCl(ActionEvent event, String username, String address, String number) {
        if (username.isEmpty() || address.isEmpty() || number.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled");
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
             PreparedStatement psCheckUserExists = connection.prepareStatement("SELECT * FROM owners WHERE name = ?");
             PreparedStatement psInsert = connection.prepareStatement("INSERT INTO owners (name, address, phone) VALUES (?, ?, ?)");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT owner_id, name FROM owners WHERE name = ? AND phone = ?")) {

            psCheckUserExists.setString(1, username);
            try (ResultSet resultSet = psCheckUserExists.executeQuery()) {
                if (resultSet.isBeforeFirst()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "User already exists");
                    return;
                }
            }

            psInsert.setString(1, username);
            psInsert.setString(2, address);
            psInsert.setString(3, number);
            psInsert.executeUpdate();

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, number);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Singleton.getInstance().setName(resultSet.getString("name"));
                    Singleton.getInstance().setId(resultSet.getInt("owner_id"));
                }
            }

            changeSceneCl(event, "loggedCl.fxml", username);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "SQL Error: " + e.getMessage());
        }
    }

    public static void logInCl(ActionEvent event, String username, String address, String phone) {
        if (username.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled");
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT address, phone, owner_id FROM owners WHERE name = ?")) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "User not found");
                    return;
                }

                while (resultSet.next()) {
                    int clientId = resultSet.getInt("owner_id");
                    String retrievedAddress = resultSet.getString("address");
                    String retrievedPhone = resultSet.getString("phone");
                    if (retrievedAddress.equals(address) && retrievedPhone.equals(phone)) {
                        Singleton.getInstance().setName(username);
                        Singleton.getInstance().setId(clientId);
                        changeSceneCl(event, "loggedCl.fxml", username);
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Incorrect credentials");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "SQL Error: " + e.getMessage());
        }
    }

    private static void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }
}
