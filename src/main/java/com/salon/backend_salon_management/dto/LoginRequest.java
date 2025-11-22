package com.salon.backend_salon_management.dto;

public record LoginRequest(
        String email,
        String password
) {}
