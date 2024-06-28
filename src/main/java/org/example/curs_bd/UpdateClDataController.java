package org.example.curs_bd;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class UpdateClDataController implements Initializable {

    private Stage stage;
    private Scene scene;
    //private Parent root;



    public void switchBack(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loggedCl.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchReturn(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("signUp.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeName(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("changeName.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeAddress(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("changeAddress.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeMobNum(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("changeMobNum.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private Button deleteAccount;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deleteAccount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    switchReturn(event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Connection connection = null;
                PreparedStatement psInsert = null;
                PreparedStatement psCheckUserExists = null;

                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_repair_shop", "root", "");
                    psCheckUserExists = connection.prepareStatement("DELETE FROM cars WHERE owner_id = ?");
                    psCheckUserExists.setInt(1, Singleton.getInstance().getId());
                    psCheckUserExists.executeUpdate();

                    psInsert = connection.prepareStatement("DELETE FROM owners WHERE owner_id = ?");
                    psInsert.setInt(1, Singleton.getInstance().getId());
                    psInsert.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {

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

        });



    }
}
