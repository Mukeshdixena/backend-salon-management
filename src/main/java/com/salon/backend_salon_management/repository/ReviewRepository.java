// src/main/java/com/salon/backend_salon_management/repository/ReviewRepository.java
package com.salon.backend_salon_management.repository;

import com.salon.backend_salon_management.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findBySalonId(Long salonId);
}