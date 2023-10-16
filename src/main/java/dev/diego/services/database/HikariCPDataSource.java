package dev.diego.services.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariCPDataSource {
    private static HikariConfig config = new HikariConfig("hikari.propierties");
    private static HikariDataSource dataSource =new HikariDataSource(config);


    HikariCPDataSource() {}

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}