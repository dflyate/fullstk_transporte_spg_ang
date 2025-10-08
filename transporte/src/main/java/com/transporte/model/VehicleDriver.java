package com.transporte.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicle_driver")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDriver {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "affiliation_date", nullable = false)
	private LocalDateTime affiliationDate;
	
	@Column(name = "disaffiliation_date")
	private LocalDateTime disaffiliationDate;
	
	@ManyToOne
	@JoinColumn(name = "vehicle_id", nullable = false)
	private Vehicle vehicle;
	
	@ManyToOne
	@JoinColumn(name = "driver_id", nullable = false)
	private Driver driver;
}
