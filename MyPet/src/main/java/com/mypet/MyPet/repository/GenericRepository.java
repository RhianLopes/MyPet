package com.mypet.MyPet.repository;

import com.mypet.MyPet.domain.Domain;
import com.mypet.MyPet.persistence.ConectionMySql;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericRepository {

    private ConectionMySql conectionMySql = new ConectionMySql("127.0.0.1", "3306", "root", "passofundo", "MyPet");

    private Connection con;
    protected String tabela;
    private String insertSQL;
    private String updateSQL;

    public GenericRepository(String tabela) {
        this.tabela = tabela;
    }

    protected abstract void setStatementValuesToInsert(PreparedStatement stmt, Object object) throws SQLException;
    protected abstract void setStatementValuesToUpdate(PreparedStatement stmt, Object object) throws SQLException;
    protected abstract Object createObject(ResultSet rs) throws SQLException;

    protected void setInsertSQL(String sql) {
        this.insertSQL = sql;
    }

    protected void setUpdateSQL(String sql) {
        this.updateSQL = sql;
    }

    public Object insert(Object object) {
        conectionMySql.openConection();
        try {
            PreparedStatement preparedStatement = (PreparedStatement) conectionMySql.getConection().prepareStatement(insertSQL);
            this.setStatementValuesToInsert(preparedStatement, object);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            this.treatException(ex);
        } finally {
            conectionMySql.closeConection();
        }
        return object;
    }

    public Object update(Object object) {
        conectionMySql.openConection();
        try {
            PreparedStatement preparedStatement = (PreparedStatement) conectionMySql.getConection().prepareStatement(this.updateSQL);
            this.setStatementValuesToUpdate(preparedStatement, object);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            this.treatException(ex);
        } finally {
            conectionMySql.closeConection();
        }
        return object;
    }

    public void delete(Long id) {
        conectionMySql.openConection();
        String sql = String.format("DELETE FROM %s WHERE id = ?", this.tabela);
        try {
            PreparedStatement preparedStatement = (PreparedStatement) conectionMySql.getConection().prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            this.treatException(ex);
        } finally {
            conectionMySql.closeConection();
        }
    }

    public Object find(int id) {
        conectionMySql.openConection();
        String sql = String.format("SELECT * FROM %s WHERE id = ?", this.tabela);
        try {
            PreparedStatement preparedStatement = (PreparedStatement) conectionMySql.getConection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return executeResultSet(preparedStatement);
        } catch (SQLException ex) {
            this.treatException(ex);
            return null;
        } finally {
            conectionMySql.closeConection();
        }
    }

    @SuppressWarnings("rawtypes")
    public ArrayList<Object> findAll() {
        conectionMySql.openConection();
        ArrayList<Object> listGeneric = new ArrayList<>();
        String sql = String.format("SELECT * FROM %s", this.tabela);
        try {
            Statement preparedStatement = (Statement) conectionMySql.getConection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery(sql);

            while(resultSet.next()){
                this.createObject(resultSet);
            }
        } catch (SQLException ex) {
            this.treatException(ex);
        } finally {
            conectionMySql.closeConection();
        }
        return listGeneric;
    }

    private Object executeResultSet(PreparedStatement stmt) {
        conectionMySql.openConection();
        try {
            ResultSet rs = stmt.getResultSet();
            rs.next();
            return this.createObject(rs);
        } catch (Exception ex) {
            this.treatException(ex);
            return null;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private ArrayList executeResultSet(Statement stmt, ArrayList list) {
        conectionMySql.openConection();
        try {
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                Object object = this.createObject(rs);
                list.add(object);
            }
        } catch (Exception ex) {
            this.treatException(ex);
        }
        return list;
    }

    private void treatException(Exception ex) {
        System.out.println(ex.getMessage());
        ex.printStackTrace();
    }
}
