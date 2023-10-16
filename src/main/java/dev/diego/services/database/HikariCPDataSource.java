package dev.diego.services.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Clase que gestiona la conexión con la base de datos y las operaciones CRUD
 * @author Diego
 * @version 1.0
 * @see Connection
 */
public class HikariCPDataSource {
      private HikariConfig config ;
      HikariDataSource dataSource ;

    /**
     * Constructor de la clase
     * @see HikariCPDataSource#HikariCPDataSource()
     */
    HikariCPDataSource() {
        config = new HikariConfig("hikari.properties");
        dataSource =new HikariDataSource(config);
    }
}