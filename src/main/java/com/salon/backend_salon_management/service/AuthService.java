// src/main/java/com/salon/backend_salon_management/service/AuthService.java
package com.salon.backend_salon_management.service;

import com.salon.backend_salon_management.dto.LoginResponse;
import com.salon.backend_salon_management.entity.User;
import com.salon.backend_salon_management.entity.User.Role;
import com.salon.backend_salon_management.repository.UserRepository;
import com.salon.backend_salon_management.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;

        // Provider or Customer Registration
        public String registerProviderOrCustomer(String name, String email, String password, String phone,
                        boolean isProvider) {
                if (userRepository.findByEmail(email).isPresent()) {
                        throw new RuntimeException("Email already registered");
                }

                Role role = isProvider ? Role.PROVIDER : Role.CUSTOMER;

                User user = User.builder()
                                .name(name)
                                .email(email)
                                .password(passwordEncoder.encode(password))
                                .phone(phone)
                                .role(role)
                                .build();

                userRepository.save(user);

                return isProvider
                                ? "Salon registered! Waiting for admin approval."
                                : "Account created successfully! You can now login.";
        }

        // Login (Customer / Provider / Admin)
        public LoginResponse login(String email, String password) {
                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

                if (!passwordEncoder.matches(password, user.getPassword())) {
                        throw new RuntimeException("Invalid email or password");
                }

                String token = jwtService.generateToken(
                                user.getEmail(),
                                user.getRole().name(),
                                user.getId());

                return new LoginResponse(
                                token,
                                user.getId(),
                                user.getName(),
                                user.getEmail(),
                                user.getRole().name());
        }
}