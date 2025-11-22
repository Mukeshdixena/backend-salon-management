package com.salon.backend_salon_managemen.controller;

import com.salon.backend_salon_managemen.dto.*;
import com.salon.backend_salon_managemen.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest req) {
        return authService.register(req);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest req) {
        return authService.login(req);
    }
}