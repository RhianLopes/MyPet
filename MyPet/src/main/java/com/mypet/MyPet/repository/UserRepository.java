package com.mypet.MyPet.repository;

import com.mypet.MyPet.domain.User;
import com.mypet.MyPet.persistence.ConectionMySql;
import com.mysql.jdbc.PreparedStatement;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Setter
public class UserRepository extends GenericRepository {

    private static final String TABLE = "user";
    private static final String INSERT_SQL = "INSERT INTO %s (id, name, nickname, email, password, photo, active) VALUES (NULL, ?, ?, ?, ?, ?, 1)";
    private static final String UPDATE_SQL = "UPDATE %s SET name = ?, nickname = ?, email = ?, photo = ?, active = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM %s WHERE id = ?";
    private static final String INACTIVATE_SQL = "UPDATE %s SET active = 0  WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM %s";
    private static final String SELECT_ONE_SQL = "SELECT * FROM %s WHERE id = ?";
    private static final String SELECT_BY_EMAIL_SQL = "SELECT * FROM %s WHERE email = ?";

    private String selectByEmailSql;

    public UserRepository() {
        super(TABLE);
        super.setInsertSQL(String.format(INSERT_SQL, TABLE));
        super.setUpdateSQL(String.format(UPDATE_SQL, TABLE));
        super.setDeleteSQL(String.format(DELETE_SQL, TABLE));
        super.setInactivateSQL(String.format(INACTIVATE_SQL, TABLE));
        super.setSelectAllSQL(String.format(SELECT_ALL_SQL, TABLE));
        super.setSelectOneSQL(String.format(SELECT_ONE_SQL, TABLE));
        this.setSelectByEmailSql(String.format(SELECT_BY_EMAIL_SQL, TABLE));
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

    public Object findByEmail(String email) {
        ConectionMySql.openConection();
        Object objectResult = null;
        try {
            PreparedStatement preparedStatement = getPreparedStatement(selectByEmailSql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                objectResult = this.createObject(resultSet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConectionMySql.closeConection();
        }
        return objectResult;
    }
}
