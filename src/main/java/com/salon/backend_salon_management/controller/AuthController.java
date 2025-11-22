// src/main/java/com/salon/backend_salon_management/controller/AuthController.java
package com.salon.backend_salon_management.controller;

import com.salon.backend_salon_management.dto.*;
import com.salon.backend_salon_management.entity.User;
import com.salon.backend_salon_management.entity.User.Role;
import com.salon.backend_salon_management.repository.UserRepository;
import com.salon.backend_salon_management.security.JwtService;
import com.salon.backend_salon_management.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthService authService;

    @PostMapping("/admin/login")
    public ResponseEntity<?> adminLogin(@RequestBody LoginRequest req) {
        var userOpt = userRepository.findByEmail(req.email());
        if (userOpt.isEmpty() || !passwordEncoder.matches(req.password(), userOpt.get().getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        User user = userOpt.get();
        if (user.getRole() != Role.ADMIN) {
            return ResponseEntity.status(403).body("Access denied");
        }

        String token = jwtService.generateToken(user.getEmail(), "ADMIN", user.getId());
        return ResponseEntity.ok(new LoginResponse(token, user.getId(), user.getName(), user.getEmail(), "ADMIN"));
    }

    @PostMapping("/provider/register")
    public ResponseEntity<String> providerRegister(@RequestBody RegisterRequest req) {
        authService.registerProviderOrCustomer(req.name(), req.email(), req.password(), req.phone(), true);
        return ResponseEntity.ok("Salon registered! Waiting for admin approval.");
    }

    @PostMapping("/customer/register")
    public ResponseEntity<String> customerRegister(@RequestBody RegisterRequest req) {
        authService.registerProviderOrCustomer(req.name(), req.email(), req.password(), req.phone(), false);
        return ResponseEntity.ok("Account created! You can now login.");
    }

    @PostMapping("/provider/login")
    public ResponseEntity<LoginResponse> providerLogin(@RequestBody LoginRequest req) {
        LoginResponse response = authService.login(req.email(), req.password());
        if (!"PROVIDER".equals(response.role())) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/customer/login")
    public ResponseEntity<LoginResponse> customerLogin(@RequestBody LoginRequest req) {
        LoginResponse response = authService.login(req.email(), req.password());
        if (!"CUSTOMER".equals(response.role())) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(response);
    }
}