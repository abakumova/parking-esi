package edu.tartu.esi;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.datasource")
public class DatabaseInitializer {

    private static String url;
    private static String username;
    private static String password;

    public static void initialize() {
        DataSource dataSource = new DriverManagerDataSource(url, username, password);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Pattern pattern = Pattern.compile("([^\\/]+$)");
        Matcher matcher = pattern.matcher(url);

        try {
            jdbcTemplate.execute("CREATE DATABASE " + matcher);
        } catch (Exception e) {
            log.error("Database already exists");
        }
    }
}