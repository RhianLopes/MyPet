package com.mypet.MyPet.dao;

import com.mypet.MyPet.domain.*;
import com.mypet.MyPet.persistence.ConectionMySql;
import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PetDAO<T> extends GenericDAO {

    private static final String TABLE = "pet";
    private static final String INSERT_SQL = "INSERT INTO %s (id, user_id, name, species, description, genre, photo, active) VALUES (NULL, ?, ?, ?, ?, ?, ?, 1)";
    private static final String UPDATE_SQL = "UPDATE %s SET name = ?, description = ?, photo = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM %s WHERE id = ?";
    private static final String INACTIVATE_SQL = "UPDATE %s SET active = 0  WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT p.id, p.user_id, p.name, p.species, p.description, p.genre, p.photo, u.name as user_name, u.nickname, u.photo as user_photo, (SELECT COUNT(fs.pet_followed) FROM follower fs WHERE p.id = fs.pet_followed AND fs.active = 1) as pet_followed, (SELECT COUNT(ff.pet_follower) FROM follower ff WHERE p.id = ff.pet_follower AND ff.active = 1) as pet_follower FROM pet as p INNER JOIN user as u on p.user_id = u.id WHERE p.active = 1";
    private static final String SELECT_ONE_SQL = "SELECT p.id, p.user_id, p.name, p.species, p.description, p.genre, p.photo, u.name as user_name, u.nickname, u.photo as user_photo, (SELECT COUNT(fs.pet_followed) FROM follower fs WHERE p.id = fs.pet_followed AND fs.active = 1) as pet_followed, (SELECT COUNT(ff.pet_follower) FROM follower ff WHERE p.id = ff.pet_follower AND ff.active = 1) as pet_follower FROM pet as p INNER JOIN user as u on p.user_id = u.id WHERE p.active = 1 and p.id = ?";
    private static final String SELECT_ALL_BY_USER_ID_SQL = "SELECT p.id, p.user_id, p.name, p.species, p.description, p.genre, p.photo, u.name as user_name, u.nickname, u.photo as user_photo, (SELECT COUNT(fs.pet_followed) FROM follower fs WHERE p.id = fs.pet_followed AND fs.active = 1) as pet_followed, (SELECT COUNT(ff.pet_follower) FROM follower ff WHERE p.id = ff.pet_follower AND ff.active = 1) as pet_follower FROM pet as p INNER JOIN user as u on p.user_id = u.id WHERE p.active = 1 and u.id = ?";

    public PetDAO(){
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
        Pet pet = (Pet) object;
        preparedStatement.setLong(1, pet.getUser().getId());
        preparedStatement.setString( 2, pet.getName());
        preparedStatement.setString(3, pet.getSpecie().toString());
        preparedStatement.setString( 4, pet.getDescription());
        preparedStatement.setString(5, pet.getGenre().toString());
        preparedStatement.setString( 6, pet.getPhoto());
    }

    @Override
    protected T prepareObjectToResponse(Long id, Object object) {
        Pet pet = (Pet) object;
        pet.setId(id);
        pet.setActive(true);
        return (T) pet;
    }

    @Override
    protected void setStatementValuesToUpdate(PreparedStatement preparedStatement, Object object) throws SQLException {
        Pet pet = (Pet) object;
        preparedStatement.setString(1, pet.getName());
        preparedStatement.setString(2, pet.getDescription());
        preparedStatement.setString(3, pet.getPhoto());
        preparedStatement.setLong(4, pet.getId());
    }

    @Override
    protected T createObject(ResultSet resultSet) throws SQLException {
        Pet pet = new Pet();
        pet.setUser(new User());
        pet.setId(resultSet.getLong("id"));
        pet.getUser().setId(resultSet.getLong("user_id"));
        pet.setName(resultSet.getString("name"));
        pet.setSpecie(resultSet.getString("species"));
        pet.setDescription(resultSet.getString("description"));
        pet.setGenre(resultSet.getString("genre"));
        pet.setPhoto(resultSet.getString("photo"));
        pet.getUser().setName(resultSet.getString("user_name"));
        pet.getUser().setNickname(resultSet.getString("nickname"));
        pet.getUser().setPhoto(resultSet.getString("user_photo"));
        pet.setFollower(resultSet.getLong("pet_follower"));
        pet.setFollowed(resultSet.getLong("pet_followed"));
        return (T) pet;
    }

    public ArrayList<T> findAllByUserId(Long userId) {
        ConectionMySql.openConection();
        ArrayList<T> listObjectResult = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = getPreparedStatement(SELECT_ALL_BY_USER_ID_SQL);
            preparedStatement.setLong(1, userId);
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
