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
    public static void changeSceneEmp(ActionEvent event, String fxmlFile, String username  ) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(RegModel.class.getResource(fxmlFile));
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
    public static void changeSceneCl(ActionEvent event, String fxmlFile, String username  ) {
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

    public static void signUpEmp(ActionEvent event, String username, String address, String password) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM employees WHERE name = ? and password = ?");
            psCheckUserExists.setString(1, username);
            psCheckUserExists.setString(2, password);
            resultSet = psCheckUserExists.executeQuery();
            if (resultSet.isBeforeFirst()) {
                System.out.println("Пользователь уже существует");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Вы не можетете использовать такое имя пользователя");
                alert.show();
            } else {
                if (username.isEmpty()) {
                    System.out.println("пустое имя пользователя");
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setContentText("Пустое имя пользователя");
                    al.show();
                } else if (address.isEmpty()) {
                    System.out.println("пустое поле с адресом");
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setContentText("Пустой адрес");
                    al.show();
                } else if (password.isEmpty()) {
                    System.out.println("пустой пароль");
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setContentText("Пустой пароль");
                    al.show();
                } else {
                    psInsert = connection.prepareStatement("INSERT INTO employees (name, address, password) VALUES (?, ?, ?)");
                    psInsert.setString(1, username);
                    psInsert.setString(2, address);
                    psInsert.setString(3, password);
                    psInsert.executeUpdate();
                    changeSceneEmp(event, "loggedEmp.fxml", username);

                    preparedStatement = connection.prepareStatement("SELECT employee_id FROM employees WHERE name = ? AND password = ?");
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        int employeeId = resultSet.getInt("employee_id");
                        Singleton.getInstance().setId(employeeId);
                        System.out.println(Singleton.getInstance().getId());
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
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }





    public static void logInEmp(ActionEvent event, String username, String address, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
            preparedStatement = connection.prepareStatement("SELECT address, password, employee_id FROM employees where name = ? AND password = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst() || username == " " || address == "" || password == "") {
                System.out.println("Пользователь не найден в базе данынх");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Данные введены неверно");
                alert.show();
            } else {
                while (resultSet.next()) {
                    int employeeId = resultSet.getInt("employee_id");
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedAddress = resultSet.getString("address");
                    if (retrievedPassword.equals(password) && retrievedAddress.equals(address)) {
                        Singleton.getInstance().setId(employeeId);
                        System.out.println(Singleton.getInstance().getId());
                        changeSceneEmp(event, "loggedEmp.fxml", username);
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



    public static void signUpCl(ActionEvent event, String username, String address, String number) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM owners WHERE name = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();
            if (resultSet.isBeforeFirst()) {
                System.out.println("Пользователь уже существует");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Вы не можетете использовать такое имя пользователя");
                alert.show();
            } else {

                psInsert = connection.prepareStatement("INSERT INTO owners (name, address, phone) VALUES (?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, address);
                psInsert.setString(3, number);
                psInsert.executeUpdate();

                changeSceneCl(event, "loggedCl.fxml", username);
                preparedStatement = connection.prepareStatement("SELECT owner_id, name FROM owners WHERE name = ? AND phone = ?");
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, number);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int clientId = resultSet.getInt("owner_id");
                    String name = resultSet.getString("name");
                    Singleton.getInstance().setName(name);
                    System.out.println(Singleton.getInstance().getName());
                    Singleton.getInstance().setId(clientId);
                    System.out.println(Singleton.getInstance().getId());
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
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void logInCl(ActionEvent event, String username, String address, String phone) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
            preparedStatement = connection.prepareStatement("SELECT address, phone, owner_id FROM owners where name = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Пользователь не найден в базе данынх");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Данные введены неверно");
                alert.show();
            } else {
                while (resultSet.next()) {
                    int clientId = resultSet.getInt("owner_id");
                    String retrievedAddress = resultSet.getString("address");
                    String retrievedPhone = resultSet.getString("phone");
                    if (retrievedAddress.equals(address) && retrievedPhone.equals(phone)) {
                        Singleton.getInstance().setName(username);
                        System.out.println(Singleton.getInstance().getName());
                        Singleton.getInstance().setId(clientId);
                        System.out.println(Singleton.getInstance().getId());
                        changeSceneCl(event, "loggedCl.fxml", username);
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
