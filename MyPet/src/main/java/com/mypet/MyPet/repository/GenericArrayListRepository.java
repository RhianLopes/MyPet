package com.mypet.MyPet.repository;

import com.mypet.MyPet.domain.Domain;
import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class GenericArrayListRepository extends GenericRepository {

    public GenericArrayListRepository(String tabela) {
        super(tabela);
    }

    protected abstract void setStatementValuesToInsert(PreparedStatement stmt, Object object) throws SQLException;
    protected abstract void setStatementValuesToUpdate(PreparedStatement stmt, Object object) throws SQLException;
    protected abstract Object createObject(ResultSet rs) throws SQLException;
    protected abstract Object convertObject(Object object);
    protected abstract ArrayList<Object> convertListObject(ArrayList<Object> object);

    @SuppressWarnings("rawtypes")
    protected ArrayList getObjects() {
        return super.findAll();
    }

    @Override
    public Object update(Object object) {
        return super.update(object);
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    public Object insert(Object object) {
        return super.insert(object);
    }
}
