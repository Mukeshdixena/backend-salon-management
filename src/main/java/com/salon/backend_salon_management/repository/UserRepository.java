// src/main/java/com/salon/backend_salon_management/repository/UserRepository.java
package com.salon.backend_salon_management.repository;

import com.salon.backend_salon_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}