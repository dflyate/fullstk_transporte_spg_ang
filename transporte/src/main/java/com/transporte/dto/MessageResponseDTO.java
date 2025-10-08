package com.transporte.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponseDTO {
	
	private String message;
	private String optionalValue;
	
	public MessageResponseDTO(String message) {
		this.message = message;
	}

}
