package dev.diego.repositories.funkos;

import dev.diego.models.Funkos;
import dev.diego.services.database.DataBaseManager;

import java.sql.SQLException;
import java.util.List;

public class CrudFunkosRepositoryImpl implements CrudFunkosRepository {

    private final DataBaseManager databaseManager;
    public CrudFunkosRepositoryImpl(DataBaseManager db){
        databaseManager=db;
    }
    @Override
    public List<Funkos> findAll() throws SQLException {
        return null;
    }

    @Override
    public Funkos findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public Funkos insert(Funkos entity) throws SQLException {
        return null;
    }

    @Override
    public Funkos update(Funkos entity) throws SQLException {
        return null;
    }

    @Override
    public Funkos delete(Funkos entity) throws SQLException {
        return null;
    }

    @Override
    public Funkos findByNombre() {
        return null;
    }
}
