package com.mypet.MyPet.persistence;

import com.mysql.jdbc.Connection;
import lombok.Data;

import java.sql.DriverManager;
import java.sql.SQLException;

@Data
public class ConectionMySql {

    private String ip;

    private String port;

    private String login;

    private String password;

    private String nameDB;

    private Connection conection;

    public ConectionMySql(String ip, String port, String login, String password, String nameDB) {
        this.ip = ip;
        this.port = port;
        this.login = login;
        this.password = password;
        this.nameDB = nameDB;
    }

    public void openConection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url =  "jdbc:mysql://"+ip+":"+port+"/"+nameDB+"?autoReconnect=true&useSSL=false";
            this.conection = (Connection) DriverManager.getConnection(url, login, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConection(){
        try {
            if(!this.conection.isClosed()){
                this.conection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}