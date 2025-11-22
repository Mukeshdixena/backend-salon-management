package com.salon.backend_salon_managemen.dto;

public record UserResponse(Long id, String name, String email, String role, String status) {
}