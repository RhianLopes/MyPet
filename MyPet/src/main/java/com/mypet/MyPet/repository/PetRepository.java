package com.mypet.MyPet.repository;

import com.mypet.MyPet.domain.Genre;
import com.mypet.MyPet.domain.Pet;
import com.mypet.MyPet.domain.Specie;
import com.mypet.MyPet.domain.User;
import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PetRepository extends GenericRepository {

    private static final String TABLE = "pet";
    private static final String INSERT_SQL = "INSERT INTO %s (id, user_id, name, species, description, genre, photo, active) VALUES (NULL, ?, ?, ?, ?, ?, ?, 1)";
    private static final String UPDATE_SQL = "UPDATE %s SET name = ?, description = ?, photo = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM %s WHERE id = ?";
    private static final String INACTIVATE_SQL = "UPDATE %s SET active = 0  WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT p.id, p.user_id, p.name, p.species, p.description, p.genre, p.photo, u.name as user_name, u.nickname, u.photo as user_photo FROM pet as p INNER JOIN user as u on p.user_id = u.id WHERE p.active = 1";
    private static final String SELECT_ONE_SQL = "SELECT p.id, p.user_id, p.name, p.species, p.description, p.genre, p.photo, u.name as user_name, u.nickname, u.photo as user_photo FROM pet as p INNER JOIN user as u on p.user_id = u.id WHERE p.active = 1 and p.id = ?";

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
    protected void setStatementValuesToUpdate(PreparedStatement preparedStatement, Object object) throws SQLException {
        Pet pet = (Pet) object;
        preparedStatement.setString(1, pet.getName());
        preparedStatement.setString(2, pet.getDescription());
        preparedStatement.setString(3, pet.getPhoto());
        preparedStatement.setLong(4, pet.getId());
    }

    @Override
    protected Object createObject(ResultSet resultSet) throws SQLException {
        Pet pet = new Pet();
        pet.setUser(new User());
        pet.setId(resultSet.getLong("id"));
        pet.getUser().setId(resultSet.getLong("user_id"));
        pet.setName(resultSet.getString("name"));
        pet.setSpecie(Specie.valueOf(resultSet.getString("species")));
        pet.setDescription(resultSet.getString("description"));
        pet.setGenre(Genre.valueOf(resultSet.getString("genre")));
        pet.setPhoto(resultSet.getString("photo"));
        pet.getUser().setName(resultSet.getString("user_name"));
        pet.getUser().setNickname(resultSet.getString("nickname"));
        pet.getUser().setPhoto(resultSet.getString("user_photo"));
        return pet;
    }
}
