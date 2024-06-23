package org.example.curs_bd;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LoggedEmpConroller {
    @FXML
    private Button logOut;

    @FXML
    private Label welcomeEmp;
    public void setEmpInf(String username){
        welcomeEmp.setText("Добро пожаловать " + username + "!");
    }

}

