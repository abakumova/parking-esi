package edu.tartu.esi;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Slf4j
@Getter
@Setter
public class DatabaseInitializer {

    public static void initialize() {
        //TODO: update hardcoded values by reading from application.properties
        String url = "jdbc:postgresql://localhost:5432/";
        String username = "postgres";
        String password = "123123";
        String databaseName = "user_management_db";

        DataSource dataSource = new DriverManagerDataSource(url, username, password);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        try {
            jdbcTemplate.execute("CREATE DATABASE " + databaseName);
        } catch (Exception e) {
            log.error("Database {} already exists", databaseName);
        }
    }
}