package com.mypet.MyPet.dao;

import com.mypet.MyPet.domain.Enjoy;
import com.mypet.MyPet.domain.Pet;
import com.mypet.MyPet.domain.Post;
import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnjoyDAO<T> extends GenericDAO {

    private static final String TABLE = "enjoy";
    private static final String INSERT_SQL = "INSERT INTO %s (id, pet_id, post_id, active) VALUES (NULL, ?, ?, 1)";
    private static final String UPDATE_SQL = "UPDATE %s SET active = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM %s WHERE id = ?";
    private static final String INACTIVATE_SQL = "UPDATE %s SET active = 0 WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM %s WHERE active = 1";
    private static final String SELECT_ONE_SQL = "SELECT * FROM %s WHERE active = 1 AND id = ?";

    public EnjoyDAO(){
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
        Enjoy enjoy =  (Enjoy) object;
        preparedStatement.setLong(1, enjoy.getPet().getId());
        preparedStatement.setLong(2, enjoy.getPost().getId());
    }

    @Override
    protected void setStatementValuesToUpdate(PreparedStatement preparedStatement, Object object) throws SQLException {
        Enjoy enjoy = (Enjoy) object;
        preparedStatement.setBoolean(1, enjoy.isActive());
        preparedStatement.setLong(2, enjoy.getId());
    }

    @Override
    protected T createObject(ResultSet resultSet) throws SQLException {
        Enjoy enjoy = new Enjoy();
        enjoy.setPet(new Pet());
        enjoy.setPost(new Post());
        enjoy.setId(resultSet.getLong("id"));
        enjoy.getPet().setId(resultSet.getLong("pet_id"));
        enjoy.getPost().setId(resultSet.getLong("post_id"));
        enjoy.setActive(resultSet.getBoolean("active"));
        return (T) enjoy;
    }
}
