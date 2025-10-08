package com.transporte.dto;

import java.time.LocalDateTime;

import com.transporte.validation.ConsultParametersSecondGroupValidation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDriverDTO {

	private Long id;

	private LocalDateTime affiliationDate;
	
	private LocalDateTime disaffiliationDate;
	
	@NotNull(groups = ConsultParametersSecondGroupValidation.class)
	private Long vehicleId;
	
	@NotNull(groups = ConsultParametersSecondGroupValidation.class)
	private Long driverId;
}
