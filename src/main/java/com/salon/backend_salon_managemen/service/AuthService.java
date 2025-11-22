package com.salon.backend_salon_managemen.service;

import com.salon.backend_salon_managemen.dto.*;
import com.salon.backend_salon_managemen.model.*;
import com.salon.backend_salon_managemen.repository.UserRepository;
import com.salon.backend_salon_managemen.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;

    public String register(RegisterRequest req) {
        if (userRepo.findByEmail(req.email()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        UserRole role = "provider".equals(req.type()) ? UserRole.PROVIDER : UserRole.CUSTOMER;

        User user = User.builder()
                .name(req.name())
                .email(req.email())
                .password(passwordEncoder.encode(req.password()))
                .role(role)
                .status(role == UserRole.ADMIN ? UserStatus.APPROVED : UserStatus.PENDING)
                .build();

        userRepo.save(user);
        return "Registered successfully! " +
                (role == UserRole.PROVIDER ? "Awaiting admin approval." : "You can now log in.");
    }

    public AuthResponse login(AuthRequest req) {
        User user = userRepo.findByEmail(req.email())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(req.password(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        if (user.getStatus() != UserStatus.APPROVED) {
            throw new RuntimeException("Your account is not approved yet.");
        }

        String token = jwtUtils.generateToken(user.getEmail(), user.getRole().name());

        UserResponse userRes = new UserResponse(
                user.getId(), user.getName(), user.getEmail(),
                user.getRole().name(), user.getStatus().name());

        return new AuthResponse(token, userRes);
    }
}