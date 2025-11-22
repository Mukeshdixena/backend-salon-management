package com.salon.backend_salon_managemen.dto;

public record RegisterRequest(String name, String email, String password, String type) {
}