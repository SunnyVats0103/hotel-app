package dev.nishtha.config;

import dev.nishtha.entities.Owner;
import dev.nishtha.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Value("${boot.admin.username}")
    private String bootUserName;
    @Value("${boot.admin.password}")
    private String bootUserPassword;

    @Bean
    public CommandLineRunner initBootUser(OwnerRepository ownerRepo, PasswordEncoder passEncoder) {
        return args -> {
            if (ownerRepo.findByName(bootUserName).isEmpty()) {
                Owner admin = new Owner();
                admin.setName(bootUserName);
                admin.setPassword(passEncoder.encode(bootUserPassword));
                admin.setRole(Set.of("ROLE_ADMIN"));
                admin.setEmail(bootUserName + "@no-email.com");

                ownerRepo.save(admin);
            }
        };
    }

}
