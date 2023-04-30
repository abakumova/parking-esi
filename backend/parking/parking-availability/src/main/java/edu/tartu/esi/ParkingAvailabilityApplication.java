package edu.tartu.esi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ParkingAvailabilityApplication {

    public static void main(String[] args) {
        DatabaseInitializer.initialize();
        SpringApplication.run(ParkingAvailabilityApplication.class, args);
    }
}
