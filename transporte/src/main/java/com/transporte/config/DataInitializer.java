package com.transporte.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.transporte.model.User;
import com.transporte.model.UserRole;
import com.transporte.repository.UserRepository;

@Configuration
public class DataInitializer {
	
	private static final String user = "admin";

	@Bean
	CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if(userRepository.findByUsername(user).isEmpty()) {
				User admin = new User();
				admin.setUsername(user);
				admin.setPassword(passwordEncoder.encode(user));
				admin.setRole(UserRole.ADMIN);
				userRepository.save(admin);
			}
		};
	}
}
