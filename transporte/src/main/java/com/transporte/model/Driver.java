package com.transporte.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "driver")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "document_type", length = 20, nullable = false)
	private String documentType;
	
	@Column(name = "document_number", length = 50, nullable = false)
	private String documentNumber;
	
	@Column(name = "full_name", length = 255, nullable = false)
	private String fullName;
	
	@Column(name = "address", length = 255)
	private String address;
	
	@Column(name = "city", length = 100)
	private String city;
	
	@Column(name = "department", length = 100)
	private String department;
	
	@Column(name = "country", length = 100)
	private String country;
	
	@Column(name = "phone", length = 30)
	private String phone;
	
	@OneToMany(mappedBy = "driver",cascade = CascadeType.ALL)
	private List<VehicleDriver> vehicleLinks;
	
	@ManyToOne
	@JoinColumn(name = "company_id", nullable = false)
	private TransportCompany company;
}
