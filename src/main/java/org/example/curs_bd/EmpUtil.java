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

public class EmpUtil {
    public static void changeScene(ActionEvent event, String fxmlFile, String username  ) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(EmpUtil.class.getResource(fxmlFile));
            root = loader.load();
            LoggedEmpConroller loggedEmpConroller = loader.getController();
            loggedEmpConroller.setEmpInf(username);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 700, 400));
        stage.show();
    }

    public static void signUpEmp(ActionEvent event, String username, String address) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM employees WHERE name = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();
            if (resultSet.isBeforeFirst()) {
                System.out.println("Пользователь уже существует");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Вы не можетете использовать такое имя пользователя");
                alert.show();
            } else {

                psInsert = connection.prepareStatement("INSERT INTO employees (name, address) VALUES (?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, address);
                psInsert.executeUpdate();

                changeScene(event, "loggedEmp.fxml", username);
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




    public static void logInEmp(ActionEvent event, String username, String address) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
            preparedStatement = connection.prepareStatement("SELECT address FROM employees where name = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Пользователь не найден в базе данынх");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Данные введены неверно");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedAddress = resultSet.getString("address");
                    if (retrievedAddress.equals(address)) {
                        changeScene(event, "loggedEmp.fxml", username);
                    } else {
                        System.out.println("Неправильные данные");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Некорректные данные");
                        alert.show();
                    }

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
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
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
}
