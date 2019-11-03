package com.mypet.MyPet.dao;

import com.mypet.MyPet.domain.Follower;
import com.mypet.MyPet.domain.Pet;
import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FollowerDAO<T> extends GenericDAO {

    private static final String TABLE = "follower";
    private static final String INSERT_SQL = "INSERT INTO %s (id, pet_follower, pet_followed, active) VALUES (NULL, ?, ?, 1)";
    private static final String UPDATE_SQL = "UPDATE %s SET active = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM %s WHERE id = ?";
    private static final String INACTIVATE_SQL = "UPDATE %s SET active = 0 WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT f.*, pr.name as pr_name, pd.name as pd_name FROM follower f INNER JOIN pet pr ON f.pet_follower = pr.id INNER JOIN pet pd ON f.pet_followed = pd.id WHERE f.active = 1";
    private static final String SELECT_ONE_SQL = "SELECT f.*, pr.name as pr_name, pd.name as pd_name FROM follower f INNER JOIN pet pr ON f.pet_follower = pr.id INNER JOIN pet pd ON f.pet_followed = pd.id WHERE f.active = 1 AND f.id = ?;";

    public FollowerDAO() {
        super(TABLE);
        super.setInsertSQL(String.format(INSERT_SQL, TABLE));
        super.setUpdateSQL(String.format(UPDATE_SQL, TABLE));
        super.setDeleteSQL(String.format(DELETE_SQL, TABLE));
        super.setInactivateSQL(String.format(INACTIVATE_SQL, TABLE));
        super.setSelectAllSQL(String.format(SELECT_ALL_SQL, TABLE));
        super.setSelectOneSQL(String.format(SELECT_ONE_SQL, TABLE));
    }

    @Override
    protected void setStatementValuesToInsert(PreparedStatement preparedStatement, Object object) throws SQLException {
        Follower follower = (Follower) object;
        preparedStatement.setLong(1, follower.getPetFollower().getId());
        preparedStatement.setLong(2, follower.getPetFollowed().getId());
    }

    @Override
    protected T prepareObjectToResponse(Long id, Object object) {
        Follower follower = (Follower) object;
        follower.setId(id);
        follower.setActive(true);
        return (T) follower;
    }

    @Override
    protected void setStatementValuesToUpdate(PreparedStatement preparedStatement, Object object) throws SQLException {
        Follower follower = (Follower) object;
        preparedStatement.setBoolean(1, follower.isActive());
        preparedStatement.setLong(2, follower.getId());
    }

    @Override
    protected T createObject(ResultSet resultSet) throws SQLException {
        Follower follower = new Follower();
        follower.setPetFollowed(new Pet());
        follower.setPetFollower(new Pet());
        follower.getPetFollowed().setId(resultSet.getLong("pet_followed"));
        follower.getPetFollowed().setName(resultSet.getString("pd_name"));
        follower.getPetFollower().setId(resultSet.getLong("pet_follower"));
        follower.getPetFollower().setName(resultSet.getString("pr_name"));
        return (T) follower;
    }
}
