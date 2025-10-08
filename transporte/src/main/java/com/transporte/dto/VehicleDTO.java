package com.transporte.dto;

import java.time.LocalDate;

import com.transporte.validation.ConsultParametersFirstGroupValidation;
import com.transporte.validation.ConsultParametersSecondGroupValidation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDTO {

	@NotNull(groups = ConsultParametersFirstGroupValidation.class)
	private Long id;

	@NotBlank(groups = {ConsultParametersFirstGroupValidation.class, ConsultParametersSecondGroupValidation.class})
	private String plate;
	
	@NotBlank(groups = {ConsultParametersFirstGroupValidation.class, ConsultParametersSecondGroupValidation.class})
	private String engine;
	
	@NotBlank(groups = {ConsultParametersFirstGroupValidation.class, ConsultParametersSecondGroupValidation.class})
	private String chassis;
	
	@NotBlank(groups = {ConsultParametersFirstGroupValidation.class, ConsultParametersSecondGroupValidation.class})
	private String model;
	
	private LocalDate registrationDate;
	
	@NotNull(groups = {ConsultParametersFirstGroupValidation.class, ConsultParametersSecondGroupValidation.class})
	private Short seatedPassengers;
	
	@NotNull(groups = {ConsultParametersFirstGroupValidation.class, ConsultParametersSecondGroupValidation.class})
	private Short standingPassengers;
	
	@NotNull(groups = {ConsultParametersFirstGroupValidation.class, ConsultParametersSecondGroupValidation.class})
	private Double dryWeight;
	
	@NotNull(groups = {ConsultParametersFirstGroupValidation.class, ConsultParametersSecondGroupValidation.class})
	private Double grossWeight;
	
	@NotNull(groups = {ConsultParametersFirstGroupValidation.class, ConsultParametersSecondGroupValidation.class})
	private Short doors;
	
	@NotBlank(groups = {ConsultParametersFirstGroupValidation.class, ConsultParametersSecondGroupValidation.class})
	private String brand;
	
	@NotBlank(groups = {ConsultParametersFirstGroupValidation.class, ConsultParametersSecondGroupValidation.class})
	private String line;
}
