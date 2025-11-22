package com.salon.backend_salon_managemen.repository;

import com.salon.backend_salon_managemen.model.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SalonRepository extends JpaRepository<Salon, Long> {
    List<Salon> findByNameContainingIgnoreCase(String name);
}