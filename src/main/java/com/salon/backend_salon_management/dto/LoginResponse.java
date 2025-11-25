package com.salon.backend_salon_management.dto;

public record LoginResponse(
                String token,
                Long userId,
                String name,
                String email,
                String role) {
}
