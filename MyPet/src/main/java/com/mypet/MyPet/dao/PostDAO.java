package com.mypet.MyPet.dao;

import com.mypet.MyPet.domain.*;
import com.mypet.MyPet.persistence.ConectionMySql;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PostDAO<T> extends GenericDAO {

    private static final String TABLE = "post";
    private static final String INSERT_SQL = "INSERT INTO %s (id, pet_id, photos, description, DATE, active) VALUES (NULL, ?, ?, ?, ?, 1)";
    private static final String UPDATE_SQL = "UPDATE %s SET description = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM %s WHERE id = ? ";
    private static final String INACTIVATE_SQL = "UPDATE %s SET active = 0  WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT po.id as post_id, pe.id as pet_id, po.photos as post_photo, po.description as post_description, po.DATE, po.active as post_active, pe.user_id, pe.name, pe.species, pe.description as pet_description, pe.genre, pe.photo as pet_photo, pe.active as pet_active, (SELECT COUNT(en.id) FROM enjoy en WHERE en.post_id = po.id) as enjoys FROM post po INNER JOIN pet pe ON po.pet_id = pe.id WHERE po.active = 1";
    private static final String SELECT_ONE_SQL = "SELECT po.id as post_id, pe.id as pet_id, po.photos as post_photo, po.description as post_description, po.DATE, po.active as post_active, pe.user_id, pe.name, pe.species, pe.description as pet_description, pe.genre, pe.photo as pet_photo, pe.active as pet_active, (SELECT COUNT(en.id) FROM enjoy en WHERE en.post_id = po.id) as enjoys FROM post po INNER JOIN pet pe ON po.pet_id = pe.id WHERE po.id = ? AND po.active = 1";
    private static final String SELECT_ALL_BY_PET = "SELECT po.id as post_id, pe.id as pet_id, po.photos as post_photo, po.description as post_description, po.DATE, po.active as post_active, pe.user_id, pe.name, pe.species, pe.description as pet_description, pe.genre, pe.photo as pet_photo, pe.active as pet_active, (SELECT COUNT(en.id) FROM enjoy en WHERE en.post_id = po.id) as enjoys FROM post po INNER JOIN pet pe ON po.pet_id = pe.id WHERE po.active = 1 AND pe.id = ? ";

    private CommentDAO commentDAO = new CommentDAO();
    private EnjoyDAO enjoyDAO = new EnjoyDAO();

    public PostDAO(){
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
        Post post = (Post) object;
        preparedStatement.setLong(1, post.getPet().getId());
        preparedStatement.setString( 2, post.getPhotos());
        preparedStatement.setString( 3, post.getDescription());
        preparedStatement.setObject( 4, LocalDateTime.now());
    }

    @Override
    protected T prepareObjectToResponse(Long id, Object object) {
        Post post = (Post) object;
        post.setId(id);
        post.setActive(true);
        return (T) post;
    }

    @Override
    protected void setStatementValuesToUpdate(PreparedStatement preparedStatement, Object object) throws SQLException {
        Post post = (Post) object;
        preparedStatement.setString(1, post.getDescription());
        preparedStatement.setLong(2, post.getId());
    }

    @Override
    protected T createObject(ResultSet resultSet) throws SQLException {
        Post post = new Post();
        post.setPet(new Pet());
        post.setId(resultSet.getLong("post_id"));
        post.getPet().setId(resultSet.getLong("pet_id"));
        post.setPhotos(resultSet.getString("post_photo"));
        post.setDescription(resultSet.getString("post_description"));
        post.setDateTime(resultSet.getObject("DATE", LocalDateTime.class));
        post.setActive(resultSet.getBoolean("post_active"));
        post.setComment(commentDAO.findByPostId(post.getId()));
        post.setEnjoys(enjoyDAO.findAllByPost(post.getId()));
        post.getPet().setName(resultSet.getString("name"));
        post.getPet().setSpecie(resultSet.getString("species"));
        post.getPet().setDescription(resultSet.getString("pet_description"));
        post.getPet().setGenre(resultSet.getString("genre"));
        post.getPet().setPhoto(resultSet.getString("pet_photo"));
        post.getPet().setActive(resultSet.getBoolean("pet_active"));
        post.setAmountEnjoy(resultSet.getInt("enjoys"));
        return (T) post;
    }

    public ArrayList<T> findAllByPet(Long petId) {
        ConectionMySql.openConection();
        ArrayList<T> listObjectResult = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = getPreparedStatement(SELECT_ALL_BY_PET);
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
