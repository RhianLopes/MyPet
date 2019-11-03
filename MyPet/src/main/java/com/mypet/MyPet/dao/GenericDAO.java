package com.mypet.MyPet.dao;

import com.mypet.MyPet.persistence.ConectionMySql;
import com.mysql.jdbc.PreparedStatement;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@Setter
public abstract class GenericDAO<T> {

    protected abstract void setStatementValuesToInsert(PreparedStatement preparedStatement, T object) throws SQLException;
    protected abstract void setStatementValuesToUpdate(PreparedStatement preparedStatement, T object) throws SQLException;
    protected abstract <T> T createObject(ResultSet resultSet) throws SQLException;
    protected abstract <T> T prepareObjectToResponse(Long id, T object) throws SQLException;

    protected String table;
    private String insertSQL;
    private String updateSQL;
    private String deleteSQL;
    private String inactivateSQL;
    private String selectAllSQL;
    private String selectOneSQL;

    GenericDAO(String table) {
        this.table = table;
    }

    public T insert(T object) {
        ConectionMySql.openConection();
        try {
            PreparedStatement preparedStatement = (PreparedStatement) ConectionMySql.connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            this.setStatementValuesToInsert(preparedStatement, object);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                object = this.prepareObjectToResponse(resultSet.getLong(1), object);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConectionMySql.closeConection();
        }
        return object;
    }

    public T update(T object) {
        ConectionMySql.openConection();
        try {
            PreparedStatement preparedStatement = getPreparedStatement(updateSQL);
            this.setStatementValuesToUpdate(preparedStatement, object);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConectionMySql.closeConection();
        }
        return object;
    }

    public void delete(Long id) {
        ConectionMySql.openConection();
        try {
            PreparedStatement preparedStatement = getPreparedStatement(deleteSQL);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConectionMySql.closeConection();
        }
    }

    public void inactivate(Long id) {
        ConectionMySql.openConection();
        try {
            PreparedStatement preparedStatement = getPreparedStatement(inactivateSQL);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConectionMySql.closeConection();
        }
    }

    public Object findById(Long id) {
        ConectionMySql.openConection();
        Object objectResult = null;
        try {
            PreparedStatement preparedStatement = getPreparedStatement(selectOneSQL);
            preparedStatement.setLong(1, id);
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

        public ArrayList<T> findAll() {
        ConectionMySql.openConection();
        ArrayList<T> listObjectResult = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = getPreparedStatement(selectAllSQL);
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

    protected static PreparedStatement getPreparedStatement (String querySQL) throws SQLException {
        return (PreparedStatement) ConectionMySql.connection.prepareStatement(querySQL);
    }
}
