package com.mypet.MyPet.repository;

import com.mypet.MyPet.domain.User;
import com.mypet.MyPet.persistence.ConectionMySql;
import com.mysql.jdbc.PreparedStatement;

import java.sql.SQLException;

public class UserRepository {

    private static ConectionMySql conectionMySql = new ConectionMySql("127.0.0.1", "3306", "root", "toor", "MyPet");

    public static User register(User user){
        conectionMySql.openConection();
        String queryInsert = "INSERT INTO user (id, name, nickname, email, password, photo, active) VALUES (NULL, ?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement preparedStatement = (PreparedStatement) conectionMySql.getConection().prepareStatement(queryInsert);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getNickname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getPhoto());
            preparedStatement.setBoolean(6, user.isActive());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conectionMySql.closeConection();
        }
        return user;
    }

    public static void delete(Long id){

        conectionMySql.openConection();
        String queryDelete = "DELETE FROM user WHERE id=?";
        try{
            PreparedStatement preparedStatement = (PreparedStatement) conectionMySql.getConection().prepareStatement(queryDelete);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            conectionMySql.closeConection();
        }
    }
}
