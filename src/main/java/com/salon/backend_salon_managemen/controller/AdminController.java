// src/main/java/com/salon/backend_salon_managemen/controller/AdminController.java
package com.salon.backend_salon_managemen.controller;

import com.salon.backend_salon_managemen.model.User;
import com.salon.backend_salon_managemen.model.UserStatus;
import com.salon.backend_salon_managemen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    @Autowired
    private UserRepository userRepo;

    // Get all pending providers
    @GetMapping("/pending-providers")
    public List<User> getPendingProviders() {
        return userRepo.findAll().stream()
                .filter(u -> "PROVIDER".equals(u.getRole().name()) && u.getStatus() == UserStatus.PENDING)
                .toList();
    }

    // Approve a provider
    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveProvider(@PathVariable Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole().name().equals("PROVIDER")) {
            user.setStatus(UserStatus.APPROVED);
            userRepo.save(user);
            return ResponseEntity.ok("Provider approved successfully");
        }
        return ResponseEntity.badRequest().body("Not a provider");
    }

    // Reject a provider
    @PutMapping("/reject/{id}")
    public ResponseEntity<?> rejectProvider(@PathVariable Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole().name().equals("PROVIDER")) {
            user.setStatus(UserStatus.REJECTED);
            userRepo.save(user);
            return ResponseEntity.ok("Provider rejected");
        }
        return ResponseEntity.badRequest().body("Not a provider");
    }
}