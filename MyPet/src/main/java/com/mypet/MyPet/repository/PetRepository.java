package com.mypet.MyPet.repository;

import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PetRepository extends GenericRepository {

    private static final String TABLE = "pet";
    private static final String INSERT_SQL = "INSERT INTO %s (id, name, nickname, email, password, photo, active) VALUES (NULL, ?, ?, ?, ?, ?, 1)";
    private static final String UPDATE_SQL = "UPDATE %s SET name = ?, nickname = ?, email = ?, photo = ?, active = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM %s WHERE id = ?";
    private static final String INACTIVATE_SQL = "UPDATE %s SET active = 0  WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM %s";
    private static final String SELECT_ONE_SQL = "SELECT * FROM %s WHERE id = ?";

    public PetRepository(){
        super(TABLE);
        super.setInsertSQL(String.format(INSERT_SQL, TABLE));
        super.setUpdateSQL(String.format(UPDATE_SQL, TABLE));
        super.setDeleteSQL(String.format(DELETE_SQL, TABLE));
        super.setInactivateSQL(String.format(INACTIVATE_SQL, TABLE));
        super.setSelectAllSQL(String.format(SELECT_ALL_SQL, TABLE));
        super.setSelectOneSQL(String.format(SELECT_ONE_SQL, TABLE));
    }

    @Override
    protected void setStatementValuesToInsert(PreparedStatement stmt, Object object) throws SQLException {

    }

    @Override
    protected void setStatementValuesToUpdate(PreparedStatement stmt, Object object) throws SQLException {

    }

    @Override
    protected Object createObject(ResultSet rs) throws SQLException {
        return null;
    }
}
