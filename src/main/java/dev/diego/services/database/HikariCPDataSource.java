package dev.diego.services.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariCPDataSource {
      private HikariConfig config ;
       HikariDataSource dataSource ;


    HikariCPDataSource() {
        config = new HikariConfig("hikari.propierties");
        dataSource =new HikariDataSource(config);
    }
}