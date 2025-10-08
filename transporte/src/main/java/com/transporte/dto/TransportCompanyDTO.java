package com.transporte.dto;

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
public class TransportCompanyDTO {
	
	@NotNull(groups = ConsultParametersFirstGroupValidation.class)
	private Long id;
	
	@NotBlank(groups = {ConsultParametersFirstGroupValidation.class, ConsultParametersSecondGroupValidation.class})
	private String documentType;
	
	@NotBlank(groups = {ConsultParametersFirstGroupValidation.class, ConsultParametersSecondGroupValidation.class})
	private String documentNumber;
	
	@NotBlank(groups = {ConsultParametersFirstGroupValidation.class, ConsultParametersSecondGroupValidation.class})
	private String fullName;
	private String address;
	private String city;
	private String department;
	private String country;
	private String phone;
	
	private Long userId;
	private Long legalRepresentativeId;

}
