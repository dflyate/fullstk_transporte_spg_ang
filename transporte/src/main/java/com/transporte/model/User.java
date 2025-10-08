package com.transporte.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "username", length = 50, nullable = false, unique = true)
	private String username;
	
	@Column(name = "password", length = 200, nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false, length = 20 )
	private UserRole role;
	
	@OneToOne(optional = true)
	@JoinColumn(name = "company_id")
	private TransportCompany transportCompany;

}
