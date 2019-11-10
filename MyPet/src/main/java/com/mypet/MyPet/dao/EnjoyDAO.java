package com.mypet.MyPet.dao;

import com.mypet.MyPet.domain.*;
import com.mypet.MyPet.persistence.ConectionMySql;
import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EnjoyDAO<T> extends GenericDAO {

    private static final String TABLE = "enjoy";
    private static final String INSERT_SQL = "INSERT INTO %s (id, pet_id, post_id, active) VALUES (NULL, ?, ?, 1)";
    private static final String UPDATE_SQL = "UPDATE %s SET active = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM %s WHERE id = ?";
    private static final String INACTIVATE_SQL = "UPDATE %s SET active = 0 WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT e.*, p.name FROM enjoy e INNER JOIN pet p WHERE e.active = 1";
    private static final String SELECT_ONE_SQL = "SELECT e.*, p.name FROM enjoy e INNER JOIN pet p WHERE e.active = 1 AND e.id = ?";
    private static final String SELECT_ALL_BY_POST = "SELECT e.*, p.name FROM enjoy e INNER JOIN pet p WHERE e.active = 1 AND e.post_id = ?";

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
    protected T prepareObjectToResponse(Long id, Object object) {
        Enjoy enjoy = (Enjoy) object;
        enjoy.setId(id);
        enjoy.setActive(true);
        return (T) enjoy;
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
        enjoy.getPet().setName(resultSet.getString("name"));
        enjoy.getPost().setId(resultSet.getLong("post_id"));
        enjoy.setActive(resultSet.getBoolean("active"));
        return (T) enjoy;
    }

    public ArrayList<T> findAllByPost(Long postId) {
        ConectionMySql.openConection();
        ArrayList<T> listObjectResult = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = getPreparedStatement(SELECT_ALL_BY_POST);
            preparedStatement.setLong(1, postId);
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
