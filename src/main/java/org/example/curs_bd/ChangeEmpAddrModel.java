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

public class ChangeEmpAddrModel {

    public static void switchMain(ActionEvent event, String fxmlFile, String username  ) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(RegModel.class.getResource(fxmlFile));
            root = loader.load();
            LoggedEmpConroller loggedEmpController = loader.getController();
            loggedEmpController.setEmpInf(username);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 700, 400));
        stage.show();
    }
    public static void changeEmpAddr(ActionEvent event, String address) {

        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM employees WHERE employee_id = ?");
            psCheckUserExists.setInt(1, Singleton.getInstance().getId());
            resultSet = psCheckUserExists.executeQuery();

            psInsert = connection.prepareStatement("UPDATE employees SET address = ? WHERE employee_id = ?");
            psInsert.setString(1, address);
            psInsert.setInt(2, Singleton.getInstance().getId());
            psInsert.executeUpdate(); // Execute the insert statement
            switchMain(event, "loggedEmp.fxml", Singleton.getInstance().getName() + "\n" + "Ваш новый адрес " + address);

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

}
