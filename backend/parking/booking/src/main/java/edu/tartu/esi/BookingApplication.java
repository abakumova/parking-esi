package edu.tartu.esi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableDiscoveryClient
@SpringBootApplication
public class BookingApplication {

    public static void main(String[] args) {
        DatabaseInitializer.initialize();
        SpringApplication.run(BookingApplication.class, args);
    }
}
