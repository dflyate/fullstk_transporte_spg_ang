package com.transporte.service;


import org.springframework.data.domain.Page;

import com.transporte.dto.MessageResponseDTO;
import com.transporte.dto.VehicleAffiliationDTO;
import com.transporte.dto.VehicleDTO;

public interface VehicleService {
	
	Page<VehicleDTO> findAllByTransportCompany(Long transportCompanyId,int pageNumber, int size);
	Page<VehicleDTO> findAvailableVehicles(int pageNumber, int size);
	VehicleDTO findById(Long id);
	Page<VehicleDTO> findAll(int pageNumber, int size);
	MessageResponseDTO create(VehicleDTO dto);
	MessageResponseDTO update(VehicleDTO dto);
	MessageResponseDTO affiliate(VehicleAffiliationDTO dto);
	MessageResponseDTO disaffiliate(VehicleAffiliationDTO dto);
	MessageResponseDTO delete(Long id);

}
