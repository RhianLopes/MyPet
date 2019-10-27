package com.mypet.MyPet.repository;

import com.mypet.MyPet.domain.Comment;
import com.mypet.MyPet.domain.Pet;
import com.mypet.MyPet.domain.Post;
import com.mypet.MyPet.persistence.ConectionMySql;
import com.mysql.jdbc.PreparedStatement;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class CommentRepository extends GenericRepository {

    private static final String TABLE = "comment";
    private static final String INSERT_SQL = "INSERT INTO %s (id, pet_id, post_id, content, date, active) VALUES (NULL, ?, ?, ?, ?, 1)";
    private static final String UPDATE_SQL = "UPDATE %s SET content = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM %s WHERE id = ?";
    private static final String INACTIVATE_SQL = "UPDATE %s SET active = 0  WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM %s WHERE active = 1";
    private static final String SELECT_ONE_SQL = "SELECT * FROM %s WHERE active = 1 AND id = ?";
    private static final String SELECT_BY_POST_ID = "SELECT * FROM %s WHERE active = 1 AND post_id = ?";

    private String selectByPostId;

    public CommentRepository(){
        super(TABLE);
        super.setInsertSQL(String.format(INSERT_SQL, TABLE));
        super.setUpdateSQL(String.format(UPDATE_SQL, TABLE));
        super.setDeleteSQL(String.format(DELETE_SQL, TABLE));
        super.setInactivateSQL(String.format(INACTIVATE_SQL, TABLE));
        super.setSelectAllSQL(String.format(SELECT_ALL_SQL, TABLE));
        super.setSelectOneSQL(String.format(SELECT_ONE_SQL, TABLE));
        this.setSelectByPostId(String.format(SELECT_BY_POST_ID, TABLE));
    }

    @Override
    protected void setStatementValuesToInsert(PreparedStatement preparedStatement, Object object) throws SQLException {
        Comment comment = (Comment) object;
        preparedStatement.setLong(1, comment.getPet().getId());
        preparedStatement.setLong(2, comment.getPost().getId());
        preparedStatement.setString(3, comment.getContent());
        preparedStatement.setObject( 4, LocalDateTime.now());
    }

    @Override
    protected void setStatementValuesToUpdate(PreparedStatement preparedStatement, Object object) throws SQLException {
        Comment comment = (Comment) object;
        preparedStatement.setString(1, comment.getContent());
        preparedStatement.setLong(2, comment.getId());
    }

    @Override
    protected Object createObject(ResultSet resultSet) throws SQLException {
        Comment comment = new Comment();
        comment.setPet(new Pet());
        comment.setPost(new Post());
        comment.setId(resultSet.getLong("id"));
        comment.getPet().setId(resultSet.getLong("pet_id"));
        comment.getPost().setId(resultSet.getLong("post_id"));
        comment.setContent(resultSet.getString("content"));
        comment.setDateTime(resultSet.getObject("date", LocalDateTime.class));
        comment.setActive(resultSet.getBoolean("active"));
        return comment;
    }

    protected Comment createCommentObject(ResultSet resultSet) throws SQLException {
        Comment comment = new Comment();
        comment.setId(resultSet.getLong("id"));
        comment.setContent(resultSet.getString("content"));
        comment.setDateTime(resultSet.getObject("date", LocalDateTime.class));
        comment.setActive(resultSet.getBoolean("active"));
        return comment;
    }

    public ArrayList<Comment> findByPostId(Long postId){
        ConectionMySql.openConection();
        ArrayList<Comment> listObjectResult = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = getPreparedStatement(selectByPostId);
            preparedStatement.setLong(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                listObjectResult.add(this.createCommentObject(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConectionMySql.closeConection();
        }
        return listObjectResult;
    }
}
