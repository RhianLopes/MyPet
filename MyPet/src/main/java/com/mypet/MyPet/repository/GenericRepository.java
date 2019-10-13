package com.mypet.MyPet.repository;

import com.mypet.MyPet.persistence.ConectionMySql;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Setter
public abstract class GenericRepository {

    protected abstract void setStatementValuesToInsert(PreparedStatement preparedStatement, Object object) throws SQLException;
    protected abstract void setStatementValuesToUpdate(PreparedStatement preparedStatement, Object object) throws SQLException;
    protected abstract Object createObject(ResultSet resultSet) throws SQLException;

    protected String table;
    private String insertSQL;
    private String updateSQL;
    private String deleteSQL;
    private String inactivateSQL;
    private String selectAllSQL;
    private String selectOneSQL;

    GenericRepository(String table) {
        this.table = table;
    }

    public Object insert(Object object) {
        ConectionMySql.openConection();
        try {
            PreparedStatement preparedStatement = getPreparedStatement(insertSQL);
            this.setStatementValuesToInsert(preparedStatement, object);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConectionMySql.closeConection();
        }
        return object;
    }

    public Object update(Object object) {
        ConectionMySql.openConection();
        try {
            PreparedStatement preparedStatement = getPreparedStatement(this.updateSQL);
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

    public Object findById( Long id) {
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

    public ArrayList<Object> findAll() {
        ConectionMySql.openConection();
        ArrayList<Object> listObjectResult = new ArrayList<>();
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
