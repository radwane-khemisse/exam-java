package com.example.exam.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnexionDb {
    private static Connection connection;
    public SingletonConnexionDb(){}

    public static Connection getConnexion(){
        if(connection == null){
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clinique2", "redone", "redone");
                System.out.print("connextion established");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.print("error established");
            }
        }
        return connection;
    }
}

