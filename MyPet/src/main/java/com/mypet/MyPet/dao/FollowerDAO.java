package com.mypet.MyPet.dao;

import com.mypet.MyPet.domain.Follower;
import com.mypet.MyPet.domain.Pet;
import com.mypet.MyPet.persistence.ConectionMySql;
import com.mysql.jdbc.PreparedStatement;

import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FollowerDAO<T> extends GenericDAO {

    private static final String TABLE = "follower";
    private static final String INSERT_SQL = "INSERT INTO %s (id, pet_follower, pet_followed, active) VALUES (NULL, ?, ?, 1)";
    private static final String UPDATE_SQL = "UPDATE %s SET active = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM %s WHERE id = ?";
    private static final String INACTIVATE_SQL = "UPDATE %s SET active = 0 WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT f.*, pr.name as pr_name, pd.name as pd_name FROM follower f INNER JOIN pet pr ON f.pet_follower = pr.id INNER JOIN pet pd ON f.pet_followed = pd.id WHERE f.active = 1";
    private static final String SELECT_ONE_SQL = "SELECT f.*, pr.name as pr_name, pd.name as pd_name FROM follower f INNER JOIN pet pr ON f.pet_follower = pr.id INNER JOIN pet pd ON f.pet_followed = pd.id WHERE f.active = 1 AND f.id = ?;";
    private static final String SELECT_ALL_BY_FOLLOWER = "SELECT f.*, pr.name as pr_name, pd.name as pd_name FROM follower f INNER JOIN pet pr ON f.pet_follower = pr.id INNER JOIN pet pd ON f.pet_followed = pd.id WHERE f.active = 1 AND f.pet_follower = ?";
    private static final String SELECT_ALL_BY_FOLLOWED = "SELECT f.*, pr.name as pr_name, pd.name as pd_name FROM follower f INNER JOIN pet pr ON f.pet_follower = pr.id INNER JOIN pet pd ON f.pet_followed = pd.id WHERE f.active = 1 AND f.pet_followed = ?";

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
        follower.setId(resultSet.getLong("id"));
        follower.getPetFollowed().setId(resultSet.getLong("pet_followed"));
        follower.getPetFollowed().setName(resultSet.getString("pd_name"));
        follower.getPetFollower().setId(resultSet.getLong("pet_follower"));
        follower.getPetFollower().setName(resultSet.getString("pr_name"));
        return (T) follower;
    }

    public ArrayList<T> findAllByFollower(Long petId) {
        ConectionMySql.openConection();
        ArrayList<T> listObjectResult = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = getPreparedStatement(SELECT_ALL_BY_FOLLOWER);
            preparedStatement.setLong(1, petId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                listObjectResult.add(this.createObject(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConectionMySql.closeConection();
        }
        return listObjectResult;
    }

    public ArrayList<T> findAllByFollowed(Long petId) {
        ConectionMySql.openConection();
        ArrayList<T> listObjectResult = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = getPreparedStatement(SELECT_ALL_BY_FOLLOWED);
            preparedStatement.setLong(1, petId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                listObjectResult.add(this.createObject(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConectionMySql.closeConection();
        }
        return listObjectResult;
    }
}
