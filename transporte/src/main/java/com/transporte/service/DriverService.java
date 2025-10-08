package com.transporte.service;

import org.springframework.data.domain.Page;

import com.transporte.dto.DriverDTO;
import com.transporte.dto.MessageResponseDTO;
import com.transporte.dto.VehicleDriverDTO;

public interface DriverService {

	Page<DriverDTO> findAll(int pageNumber,int size, Long transportCompanyId);
	Page<DriverDTO> findAllByVehicle(Long vehicleId,int pageNumber,int size);
	Page<DriverDTO> findAvailableDrivers(int pageNumber, int size, Long vehicleId, Long transportCompanyId);
	DriverDTO findById(Long id);
	MessageResponseDTO create(DriverDTO dto);
	MessageResponseDTO affiliate(VehicleDriverDTO dto);
	MessageResponseDTO disaffiliate(VehicleDriverDTO dto);
	MessageResponseDTO update(DriverDTO dto);
	MessageResponseDTO delete(Long id);
}
