package dev.diego.repositories.funkos;

import dev.diego.enunms.Modelo;
import dev.diego.models.Funkos;
import dev.diego.services.database.DataBaseManager;
import dev.diego.utilities.IdGenerator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implementacion de CrudFunkosRepository.
 * @author Diego
 * @version 1.0
 * @see CrudFunkosRepository
 */

public class CrudFunkosRepositoryImpl implements CrudFunkosRepository {

    /**
     * Instancia de DataBaseManager.
     */
    private final DataBaseManager dataBaseManager;

    /**
     * Constructor de la clase.
     * @param db DataBaseManager
     */
    public CrudFunkosRepositoryImpl(DataBaseManager db){
        dataBaseManager =db;
    }

    /**
     * Metodo que devuelve una lista de todos los funkos de la base de datos.
     * @return lista de funkos
     * @throws SQLException
     */
    @Override
    public List<Funkos> findAll() throws SQLException {
        dataBaseManager.openConnection();
        String sql="SELECT * FROM FUNKOS";
        var result = dataBaseManager.select(sql).orElseThrow(() -> new SQLException("Error al obtener todos los funkos"));

        List<Funkos> funkosList = new ArrayList<>();

        while (result.next()){
            Funkos funko = new Funkos(
                    UUID.fromString(result.getString("cod")),
                    result.getString("nombre"),
                    Modelo.valueOf(result.getString("modelo")),
                    result.getDouble("precio"),
                    LocalDate.parse(result.getString("fecha_lanzamiento"))
            );
            funkosList.add(funko);
        }

        dataBaseManager.closeConnection();
        return funkosList;
    }

    /**
     * Metodo que devuelve un funko por su id.
     * @param id Id de la entidad
     * @return Funkos con el id dado
     * @throws SQLException
     */
    @Override
    public Funkos findById(Integer id) throws SQLException {
        dataBaseManager.openConnection();
        String sql="SELECT * FROM FUNKOS WHERE ID=?";
        var result = dataBaseManager.select(sql).orElseThrow(() -> new SQLException("Error al obtener todos los funkos"));
        result.next();
        Funkos funko = new Funkos(
                UUID.fromString(result.getString("cod")),
                result.getString("nombre"),
                Modelo.valueOf(result.getString("modelo")),
                result.getDouble("precio"),
                LocalDate.parse(result.getString("fecha_lanzamiento"))
        );
        dataBaseManager.closeConnection();
        return funko;
    }

    /**
     * Metodo que inserta un funko en la base de datos.
     * @param entity Entidad
     * @return null
     * @throws SQLException
     */
    @Override
    public Funkos insert(Funkos entity) throws SQLException {
        String sql = "INSERT INTO mediciones VALUES(null, ?, ?, ?, ?, ?, ?, ?, ?)";
        dataBaseManager.openConnection();
        dataBaseManager.insert(sql,
                null,
                entity.cod(),
                IdGenerator.getAndIncrement(),
                entity.nombre(),
                entity.modelo().toString(),
                entity.precio(),
                entity.FechaLanzamiento(),
                LocalDate.now(),
                LocalDate.now()
        );

        dataBaseManager.closeConnection();
        return null;
    }
    @Override
    public Funkos update(Funkos entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Funkos delete(Funkos entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Metodo que devuelve un funko por su nombre.
     * @return Funkos con el nombre dado
     * @throws SQLException
     */
    @Override
    public Funkos findByNombre() throws SQLException {
        dataBaseManager.openConnection();
        String sql="SELECT * FROM FUNKOS WHERE nombre=?";
        var result = dataBaseManager.select(sql).orElseThrow(() -> new SQLException("Error al obtener todos los funkos"));
        result.next();
        Funkos funko = new Funkos(
                UUID.fromString(result.getString("cod")),
                result.getString("nombre"),
                Modelo.valueOf(result.getString("modelo")),
                result.getDouble("precio"),
                LocalDate.parse(result.getString("fecha_lanzamiento"))
        );
        dataBaseManager.closeConnection();
        return funko;
    }

    /**
     * Metodo que inserta una lista de funkos en la base de datos.
     * @param list a insertar
     * @return null
     * @throws SQLException
     */
    @Override
    public List<Funkos> insertAll(List<Funkos> list) throws SQLException {
        dataBaseManager.openConnection();
        list.forEach(f-> {
            try {
                insert(f);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        dataBaseManager.closeConnection();
        return null;
    }
}
