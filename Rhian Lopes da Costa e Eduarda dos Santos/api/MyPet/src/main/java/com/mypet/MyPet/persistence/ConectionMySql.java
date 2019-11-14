package com.mypet.MyPet.persistence;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectionMySql {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/MyPet?autoReconnect=true&useSSL=false";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "toor";
    public static Connection connection;

    private ConectionMySql(){}

    public static void openConection(){
        try{
            Class.forName(DRIVER);
            ConectionMySql.connection = (Connection) DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConection(){
        try {
            if(!ConectionMySql.connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}