package com.salon.backend_salon_management.repository;

import com.salon.backend_salon_management.entity.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalonRepository extends JpaRepository<Salon, Long> {
}
