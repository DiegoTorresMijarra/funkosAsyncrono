package dev.diego.repositories.funkos;

import dev.diego.models.Funkos;
import dev.diego.repositories.base.CrudRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz específica para operaciones CRUD (Create, Read, Update, Delete) en un repositorio de Funkos.
 * @author Diego
 * @version 1.0
 * @see CrudRepository
 */
public interface CrudFunkosRepository extends CrudRepository <Funkos> {
    /**
     * Metodo que busca por nombres que contengan el patrón indicado.
     * @return Funkos
     */
    Funkos findByNombre() throws SQLException;

    /**
     * Metodo que inserta una lista de Funkos en la base de datos.
     * @param list a insertar
     * @return null
     * @throws SQLException
     */
    List<Funkos>insertAll(List<Funkos> list) throws SQLException;
}
