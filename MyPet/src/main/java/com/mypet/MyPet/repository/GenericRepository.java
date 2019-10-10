package com.mypet.MyPet.repository;

import com.mypet.MyPet.domain.Domain;
import com.mypet.MyPet.persistence.ConectionMySql;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class GenericRepository {

    private static ConectionMySql conectionMySql = new ConectionMySql("127.0.0.1", "3306", "root", "toor", "MyPet");

    private Connection con;
    protected String tabela;
    private String insertSQL;
    private String updateSQL;

    public GenericRepository(String tabela) {
        this.tabela = tabela;
    }

    protected abstract void setStatementValues(PreparedStatement stmt, Object object) throws SQLException;
    protected abstract Object createObject(ResultSet rs) throws SQLException;

    protected void setInsertSQL(String sql) {
        this.insertSQL = sql;
    }

    protected void setUpdateSQL(String sql) {
        this.updateSQL = sql;
    }

    public void insert(Object object) {
        try {
            PreparedStatement preparedStatement = (PreparedStatement) conectionMySql.getConection().prepareStatement(this.insertSQL);
            this.setStatementValues(preparedStatement, object);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            this.treatException(ex);
        } finally {
            conectionMySql.closeConection();
        }
    }

    public void update(Object object) {
        try (PreparedStatement stmt = (PreparedStatement) this.con.prepareStatement(this.updateSQL)) {
            this.setStatementValues(stmt, object);
            stmt.execute();
        } catch (SQLException ex) {
            this.treatException(ex);
        }
    }

    public void delete(Domain object) {
        String sql = String.format("DELETE FROM %s WHERE id = ?", this.tabela);
        try (PreparedStatement stmt = (PreparedStatement) this.con.prepareStatement(sql)) {
            stmt.setLong(1, object.getId());
            stmt.execute();
        } catch (SQLException ex) {
            this.treatException(ex);
        }
    }

    public Object find(int id) {
        String sql = String.format("SELECT * FROM %s WHERE id = ?", this.tabela);
        try (PreparedStatement stmt = (PreparedStatement) this.con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
            return executeResultSet(stmt);
        } catch (SQLException ex) {
            this.treatException(ex);
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    public ArrayList findAll() {
        String sql = String.format("SELECT * FROM %s", this.tabela);
        try (Statement stmt = (Statement) this.con.createStatement()) {
            stmt.execute(sql);
            ArrayList list = new ArrayList<>();
            return executeResultSet(stmt, list);
        } catch (SQLException ex) {
            this.treatException(ex);
            return null;
        }
    }

    private Object executeResultSet(PreparedStatement stmt) {
        try (ResultSet rs = stmt.getResultSet()) {
            rs.next();
            return this.createObject(rs);
        } catch (Exception ex) {
            this.treatException(ex);
            return null;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private ArrayList executeResultSet(Statement stmt, ArrayList list) {
        try (ResultSet rs = stmt.getResultSet()) {
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
