package edu.tartu.esi.config;

//import edu.tartu.esi.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DatabaseConfiguration {

//    @Bean
//    CommandLineRunner initDatabase(UserRoleRepository userRoleRepository) {
//        return args -> loadUserRoles(userRoleRepository);
//    }

//    private void loadUserRoles(UserRoleRepository userRoleRepository) {
//        log.info("Preloading " + userRoleRepository.save(UserRole.builder().id(1).role("User").build()));
//        log.info("Preloading " + userRoleRepository.save(UserRole.builder().id(2).role("Landlord").build()));
//        log.info("Preloading " + userRoleRepository.save(UserRole.builder().id(3).role("Admin").build()));
//    }
}
