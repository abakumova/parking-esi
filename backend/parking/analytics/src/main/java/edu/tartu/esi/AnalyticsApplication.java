package edu.tartu.esi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EntityScan(basePackages = "edu.tartu.esi")
public class AnalyticsApplication {

    public static void main(String[] args) {
        DatabaseInitializer.initialize();
        SpringApplication.run(AnalyticsApplication.class, args);
    }
}
