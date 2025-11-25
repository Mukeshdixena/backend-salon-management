package com.salon.backend_salon_management.config;

import com.salon.backend_salon_management.entity.User;
import com.salon.backend_salon_management.entity.User.Role;
import com.salon.backend_salon_management.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            boolean adminExists = userRepository
                    .findByEmail("admin@system.com")
                    .isPresent();

            if (!adminExists) {
                User admin = User.builder()
                        .name("System Admin")
                        .email("admin@system.com")
                        .password(passwordEncoder.encode("Admin@123"))
                        .phone("0000000000")
                        .role(Role.ADMIN)
                        .status("ACTIVE") // FIX ADDED
                        .build();

                userRepository.save(admin);
                System.out.println("Default ADMIN user created: admin@system.com / Admin@123");
            }
        };
    }
}
