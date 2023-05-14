package edu.tartu.esi;

import edu.tartu.esi.model.PaymentMethod;
import edu.tartu.esi.model.Role;
import edu.tartu.esi.model.User;
import edu.tartu.esi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner loadProductsData(UserRepository userRepository) {
        return args -> {
            User webClient = new User();
            webClient.setEmail("webclient.esi.tartu@ut.ee");
            webClient.setPassword(passwordEncoder.encode("webclient.esi.tartu@ut.ee.password"));
            webClient.setRole(Role.ADMIN);
            webClient.setFirstName("WebClient");
            webClient.setLastName("WebClient");
            webClient.setPaymentMethod(new PaymentMethod());
            userRepository.save(webClient);
        };
    }
}