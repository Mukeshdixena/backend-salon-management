// src/main/java/com/salon/backend_salon_managemen/BackendSalonManagemenApplication.java

package com.salon.backend_salon_managemen;

import com.salon.backend_salon_managemen.model.UserRole;
import com.salon.backend_salon_managemen.model.UserStatus;
import com.salon.backend_salon_managemen.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendSalonManagemenApplication {

	@Autowired
	private UserRepository userRepo;

	public static void main(String[] args) {
		SpringApplication.run(BackendSalonManagemenApplication.class, args);
	}

	@PostConstruct
	public void createAdmin() {
		if (userRepo.findByEmail("admin@salon.com").isEmpty()) {
			userRepo.save(com.salon.backend_salon_managemen.model.User.builder()
					.name("Super Admin")
					.email("admin@salon.com")
					.password(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("admin123"))
					.role(UserRole.ADMIN)
					.status(UserStatus.APPROVED)
					.build());
			System.out.println("Super Admin created: admin@salon.com / admin123");
		}
	}
}