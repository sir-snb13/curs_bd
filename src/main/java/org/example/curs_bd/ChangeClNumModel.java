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

public class ChangeClNumModel {
    public static void switchMain(ActionEvent event, String fxmlFile, String username  ) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(RegModel.class.getResource(fxmlFile));
            root = loader.load();
            LoggedClController loggedClController = loader.getController();
            loggedClController.setClInf(username);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 700, 400));
        stage.show();
    }
    public static void changeName(ActionEvent event, String clNum) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM owners WHERE phone = ?");
            psCheckUserExists.setString(1, clNum);
            resultSet = psCheckUserExists.executeQuery();

            psInsert = connection.prepareStatement("UPDATE owners SET phone = ? WHERE owner_id = ?");
            psInsert.setString(1, clNum);
            psInsert.setInt(2, Singleton.getInstance().getId());
            psInsert.executeUpdate(); // Execute the insert statement
            switchMain(event, "loggedCl.fxml", Singleton.getInstance().getName() + "\n" + "Ваш новый адрес " + clNum);
            if (resultSet.isBeforeFirst()) {
                System.out.println("Пустое поле имя");
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setContentText("Пустое поле имя");
                al.show();
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

}
