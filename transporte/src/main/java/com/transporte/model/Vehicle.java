package com.transporte.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "plate", length = 10, nullable = false)
	private String plate;
	
	@Column(name = "engine", length = 50, nullable = false)
	private String engine;
	
	@Column(name = "chassis", length = 50, nullable = false)
	private String chassis;
	
	@Column(name = "model", length = 20, nullable = false)
	private String model;
	
	@Column(name = "registration_date", nullable = false)
	private LocalDate registrationDate;
	
	@Column(name = "seated_passengers", length = 5, nullable = false)
	private Short seatedPassengers;
	
	@Column(name = "standing_passengers", length = 5, nullable = false)
	private Short standingPassengers;
	
	@Column(name = "dry_weight", length = 5, nullable = false)
	private Double dryWeight;
	
	@Column(name = "gross_weight", length = 5, nullable = false)
	private Double grossWeight;
	
	@Column(name = "doors", length = 2, nullable = false)
	private Short doors;
	
	@Column(name = "brand", length = 50, nullable = false)
	private String brand;
	
	@Column(name = "line", length = 50, nullable = false)
	private String line;
	
	@OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
	private List<VehicleAffiliation> affiliations;
	
	@OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
	private List<VehicleDriver> driverLinks;
}
