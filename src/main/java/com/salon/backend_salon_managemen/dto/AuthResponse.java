package com.salon.backend_salon_managemen.dto;

public record AuthResponse(String token, UserResponse user) {
}