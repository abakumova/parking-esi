package edu.tartu.esi;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class UserManagementApplication {

    public static void main(String[] args) {
        DatabaseInitializer.initialize();
        SpringApplication.run(UserManagementApplication.class, args);
    }

    //TODO : update hardcoded values by reading from application.properties
//    @Bean
//    public OpenAPI springShopOpenAPI() {
//        return new OpenAPI()
//                .info(new Info().title("User Management API")
//                        .description("User Management API")
//                        .version("v0.0.1")
//                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
//                .externalDocs(new ExternalDocumentation()
//                        .description("Products - Orders Documentation")
//                        .url("http://localhost:8083/swagger-ui/4.15.5/index.html"));
//    }
}
