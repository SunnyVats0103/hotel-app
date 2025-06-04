package dev.nishtha.core.config;

import dev.nishtha.core.entities.UserEntity;
import dev.nishtha.core.repositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(name = "app.init.data", havingValue = "true")
@Slf4j
public class DataInitializer {

    @Value("${boot.admin.username}")
    private String bootUserName;
    @Value("${boot.admin.password}")
    private String bootUserPassword;
    private final PasswordEncoderProvider passwordEncoderProvider;

    @Bean
    public CommandLineRunner initBootUser(UserEntityRepository ownerRepo) {
        return args -> {
            if (ownerRepo.findByUserName(bootUserName).isEmpty()) {
                // Create a new admin user
                UserEntity admin = UserEntity.builder()
                        .name(bootUserName)
                        .userName(bootUserName)
                        .password(passwordEncoderProvider.passwordEncoder().encode(bootUserPassword))
                        .email(bootUserName + "@no-mail.com")
                        .role(Set.of("ADMIN", "CUSTOMER", "SUPER"))
                        .isDeleted(false)
                        .enabled(true)
                        .registered(true)
                        .phone("1213497798")
                        .build();

                ownerRepo.save(admin);
            }
            log.info("Boot admin user: bootUserName={}, bootUserPassword=[PROTECTED]", bootUserName);
        };
    }

}
