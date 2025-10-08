package com.transporte.service;

import org.springframework.data.domain.Page;

import com.transporte.dto.MessageResponseDTO;
import com.transporte.dto.TransportCompanyDTO;
import com.transporte.dto.TransportCompanyRegistrationDTO;

public interface TransportCompanyService {
	
	Page<TransportCompanyDTO> findAll(int pageNumber, int size);
	TransportCompanyRegistrationDTO findById(Long id);
	MessageResponseDTO createRelatedObjects(TransportCompanyRegistrationDTO dto);
	MessageResponseDTO update(TransportCompanyRegistrationDTO dto);
	MessageResponseDTO delete(Long id);

}
