package com.mypet.MyPet.repository;

import com.mypet.MyPet.domain.User;
import com.mypet.MyPet.persistence.ConectionMySql;
import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserRepository2 extends GenericArrayListRepository{

    private static ConectionMySql conectionMySql = new ConectionMySql("127.0.0.1", "3306", "root", "toor", "MyPet");

    public UserRepository2() {
        super( "user");
        super.setInsertSQL(String.format("INSERT INTO user (id, name, nickname, email, password, photo, active) VALUES (NULL, ?, ?, ?, ?, ?, ?)", this.tabela ));
        super.setUpdateSQL(String.format("UPDATE user SET name = ?, nickname = ?, email = ?, photo = ?, active = ? WHERE id = ?", this.tabela));
    }

    @Override
    public User find(int id) {
        return this.convertObject(super.find(id));
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Object> findAll(){
        return this.convertListObject(super.findAll());
    }

    @Override
    protected void setStatementValuesToInsert(PreparedStatement preparedStatement, Object object) throws SQLException {
        User user = (User) object;
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getNickname());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setString(5, user.getPhoto());
        preparedStatement.setBoolean(6, user.isActive());
    }

    @Override
    protected void setStatementValuesToUpdate(PreparedStatement preparedStatement, Object object) throws SQLException {
        User user = (User) object;
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getNickname());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPhoto());
        preparedStatement.setBoolean(5, user.isActive());
        preparedStatement.setLong(6, user.getId());
    }

    @Override
    protected Object createObject(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setNickname(resultSet.getString("nickname"));
        user.setEmail(resultSet.getString("email"));
        user.setPhoto(resultSet.getString("photo"));
        user.setPassword(resultSet.getString("password"));
        user.setActive(resultSet.getBoolean("active"));
        return user;
    }

    @Override
    protected User convertObject(Object object) {
        return (User) object;
    }

    @Override
    protected ArrayList<Object> convertListObject(ArrayList<Object> object2) {
        return object2;
    }
}
