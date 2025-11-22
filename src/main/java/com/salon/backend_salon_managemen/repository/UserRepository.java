package com.salon.backend_salon_managemen.repository;

import com.salon.backend_salon_managemen.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}