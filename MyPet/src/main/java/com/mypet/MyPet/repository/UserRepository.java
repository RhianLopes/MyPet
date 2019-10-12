package com.mypet.MyPet.repository;

import com.mypet.MyPet.domain.User;
import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository extends GenericRepository {

    public UserRepository() {
        super( "user");
        super.setInsertSQL(String.format("INSERT INTO %s (id, name, nickname, email, password, photo, active) VALUES (NULL, ?, ?, ?, ?, ?, 1)", this.table ));
        super.setUpdateSQL(String.format("UPDATE %s SET name = ?, nickname = ?, email = ?, photo = ?, active = ? WHERE id = ?", this.table));
    }

    @Override
    protected void setStatementValuesToInsert(PreparedStatement preparedStatement, Object object) throws SQLException {
        User user = (User) object;
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getNickname());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setString(5, user.getPhoto());
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
        user.setActive(resultSet.getBoolean("active"));
        user.setName(resultSet.getString("name"));
        user.setNickname(resultSet.getString("nickname"));
        user.setEmail(resultSet.getString("email"));
        user.setPhoto(resultSet.getString("photo"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }
}
