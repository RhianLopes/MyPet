package com.mypet.MyPet.repository;

import com.mypet.MyPet.domain.User;
import com.mypet.MyPet.persistence.ConectionMySql;
import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static ConectionMySql conectionMySql = new ConectionMySql("127.0.0.1", "3306", "root", "passofundo", "MyPet");

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

    public static void update(User user){

        conectionMySql.openConection();
        String queryUpdate = "UPDATE user SET name=?, nickname=?, email=?, password=?, photo=? WHERE id=?";
        try{
            PreparedStatement preparedStatement = (PreparedStatement) conectionMySql.getConection().prepareStatement(queryUpdate);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getNickname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5,user.getPhoto());
            preparedStatement.setLong(6, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            conectionMySql.closeConection();
        }
    }

    public List<User> findAll() {

        conectionMySql.openConection();
        List<User> userList = new ArrayList<User>();
        User user = null;
        String  queryFindAll= "SELECT * FROM user";
        try {
       PreparedStatement preparedStatement = (PreparedStatement) conectionMySql.getConection().prepareStatement(queryFindAll);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                // EXISTE A LINHA E TEMOS QUE CONVERTER A LINHA PARA UM OBJETO USUARIO
                user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setNickname(rs.getString("login"));
                user.setPassword(rs.getString("senha"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            conectionMySql.closeConection();
        }
        return userList;
    }




}
