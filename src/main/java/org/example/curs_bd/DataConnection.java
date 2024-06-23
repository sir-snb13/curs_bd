package org.example.curs_bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static javafx.application.Application.launch;

public class DataConnection {
    public static void main(String[] args){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
//
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/auto_repair_shop",
                    "root", "");
//
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM employees";
            ResultSet result = statement.executeQuery(query);
//
            while(result.next()){
                int id = result.getInt("Ind_num");
                String name = result.getString("Name");
                String short_name = result.getString("Address");
//
                System.out.print("Vacant post: ");
                System.out.print("id = " + id);
                System.out.print(", name = \"" + name + "\"");
                System.out.println(", short name = \"" + short_name + "\".");
            }
//
            connection.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

}
