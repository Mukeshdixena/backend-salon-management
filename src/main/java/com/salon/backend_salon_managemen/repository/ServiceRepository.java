// src/main/java/com/salon/backend_salon_managemen/repository/ServiceRepository.java
package com.salon.backend_salon_managemen.repository;

import com.salon.backend_salon_managemen.model.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
}