package com.salon.backend_salon_management.dto;

public record RegisterRequest(
                String name,
                String email,
                String password,
                String phone) {
}
