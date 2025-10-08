package com.transporte.dto;

import com.transporte.model.UserRole;
import com.transporte.validation.ConsultParametersFirstGroupValidation;
import com.transporte.validation.ConsultParametersSecondGroupValidation;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class UserDTO {

	private Long id;
	
	@NotBlank(groups = {ConsultParametersFirstGroupValidation.class, ConsultParametersSecondGroupValidation.class})
	private String username;
	
	@NotBlank(groups = {ConsultParametersSecondGroupValidation.class})
	private String password;
	
	@NotNull(groups = {ConsultParametersFirstGroupValidation.class, ConsultParametersSecondGroupValidation.class})
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
	
	private Long transportCompanyid;
}
