package edu.tartu.esi;

import edu.tartu.esi.model.PaymentMethod;
import edu.tartu.esi.model.User;
import edu.tartu.esi.model.UserRole;
import edu.tartu.esi.model.UserRoleEnum;
import edu.tartu.esi.repository.UserRepository;
import edu.tartu.esi.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadData(UserRoleRepository userRoleRepository) {
        return args -> {
            UserRole userRole = new UserRole();
            userRole.setId(1);
            userRole.setRole("User");

            UserRole landlordRole = new UserRole();
            landlordRole.setId(2);
            landlordRole.setRole("Landlord");

            UserRole adminRole = new UserRole();
            adminRole.setId(3);
            adminRole.setRole("Admin");

            userRoleRepository.save(userRole);
            userRoleRepository.save(landlordRole);
            userRoleRepository.save(adminRole);
        };
    }

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository) {
        return args -> {
            PaymentMethod paymentMethod = new PaymentMethod();
            paymentMethod.setCardNumber("1234567890");
            paymentMethod.setCardHolderName("FirstName LastName");
            paymentMethod.setCvv("012");
            User user = new User();
            user.setId("1");
            user.setEmail("useremail@test.esi");
            user.setUserRole(UserRoleEnum.USER);
            user.setPassword("password");
            user.setFirstName("FirstName");
            user.setLastName("LastName");
            user.setPaymentMethod(paymentMethod);

            paymentMethod = new PaymentMethod();
            paymentMethod.setIban("QW10203050630");
            User landlord = new User();
            landlord.setId("2");
            landlord.setEmail("useremail@test.esi");
            landlord.setUserRole(UserRoleEnum.LANDLORD);
            landlord.setPassword("password");
            landlord.setFirstName("FirstName");
            landlord.setLastName("LastName");
            landlord.setPaymentMethod(paymentMethod);

            User admin = new User();
            admin.setId("3");
            admin.setUserRole(UserRoleEnum.ADMIN);

            userRepository.save(user);
            userRepository.save(landlord);
            userRepository.save(admin);
        };
    }
}
