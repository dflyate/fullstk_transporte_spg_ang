package com.transporte.controller;


import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transporte.dto.MessageResponseDTO;
import com.transporte.dto.VehicleAffiliationDTO;
import com.transporte.dto.VehicleDTO;
import com.transporte.service.VehicleService;
import com.transporte.validation.ConsultParametersFirstGroupValidation;
import com.transporte.validation.ConsultParametersSecondGroupValidation;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vehicle")
@RequiredArgsConstructor
public class VehicleController {
	
	private final VehicleService service;
	
	@PostMapping
	public ResponseEntity<MessageResponseDTO> create(@Validated(ConsultParametersSecondGroupValidation.class) @RequestBody VehicleDTO dto){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
	}
	
	@PostMapping("/affiliate")
	public ResponseEntity<MessageResponseDTO> affiliate(@Validated(ConsultParametersSecondGroupValidation.class) @RequestBody VehicleAffiliationDTO dto){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.affiliate(dto));
	}
	
	@GetMapping
	public ResponseEntity<Page<VehicleDTO>> getAll(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(service.findAll(page,size));
	}
	
	@GetMapping("/by_transport_company")
	public ResponseEntity<Page<VehicleDTO>> getAllByTransportCompany(@RequestParam Long transportCompanyId,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(service.findAllByTransportCompany(transportCompanyId,page,size));
	}
	
	@GetMapping("/available-vehicles")
	public ResponseEntity<Page<VehicleDTO>> getAvailableVehicles(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(service.findAvailableVehicles(page,size));
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<VehicleDTO> getById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PutMapping
	public ResponseEntity<MessageResponseDTO> update(@Validated(ConsultParametersFirstGroupValidation.class) @RequestBody VehicleDTO dto ) {
		return ResponseEntity.ok(service.update(dto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponseDTO> delete(@PathVariable Long id) {
		return ResponseEntity.ok(service.delete(id));
	}
	
	@PutMapping("/disaffiliate")
	public ResponseEntity<MessageResponseDTO> disaffiliate(@Validated(ConsultParametersFirstGroupValidation.class) @RequestBody VehicleAffiliationDTO dto) {
		return ResponseEntity.ok(service.disaffiliate(dto));
	}

}
