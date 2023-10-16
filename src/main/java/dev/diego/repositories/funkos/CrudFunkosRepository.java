package dev.diego.repositories.funkos;

import dev.diego.models.Funkos;
import dev.diego.repositories.base.CrudRepository;

public interface CrudFunkosRepository extends CrudRepository <Funkos> {
    /**
     * Metodo que busca por nombres que contengan el patrón indicado.
     * @return Funkos
     */
    Funkos findByNombre();
}
