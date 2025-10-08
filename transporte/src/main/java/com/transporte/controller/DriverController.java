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

import com.transporte.dto.DriverDTO;
import com.transporte.dto.MessageResponseDTO;
import com.transporte.dto.VehicleDriverDTO;
import com.transporte.service.DriverService;
import com.transporte.validation.ConsultParametersFirstGroupValidation;
import com.transporte.validation.ConsultParametersSecondGroupValidation;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
public class DriverController {

private final DriverService service;
	
	@PostMapping
	public ResponseEntity<MessageResponseDTO> create(@Validated(ConsultParametersSecondGroupValidation.class) @RequestBody DriverDTO dto){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
	}
	
	@GetMapping
	public ResponseEntity<Page<DriverDTO>> getAll(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size, @RequestParam Long transportCompanyId) {
		return ResponseEntity.ok(service.findAll(page,size,transportCompanyId));
	}
	
	@GetMapping("/by_vehicle")
	public ResponseEntity<Page<DriverDTO>> getAllByVehicle(@RequestParam Long vehicleId,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(service.findAllByVehicle(vehicleId,page,size));
	}
	
	@GetMapping("/available-drivers")
	public ResponseEntity<Page<DriverDTO>> getAvailableVehicles(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size, @RequestParam Long vehicleId, @RequestParam Long transportCompanyId) {
		return ResponseEntity.ok(service.findAvailableDrivers(page,size,vehicleId,transportCompanyId));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DriverDTO> getById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PostMapping("/affiliate")
	public ResponseEntity<MessageResponseDTO> affiliate(@Validated(ConsultParametersSecondGroupValidation.class) @RequestBody VehicleDriverDTO dto){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.affiliate(dto));
	}
	
	@PutMapping
	public ResponseEntity<MessageResponseDTO> update(@Validated(ConsultParametersFirstGroupValidation.class) @RequestBody DriverDTO dto ) {
		return ResponseEntity.ok(service.update(dto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponseDTO> delete(@PathVariable Long id) {
		return ResponseEntity.ok(service.delete(id));
	}
	
	@PutMapping("/disaffiliate")
	public ResponseEntity<MessageResponseDTO> disaffiliate(@Validated(ConsultParametersFirstGroupValidation.class) @RequestBody VehicleDriverDTO dto) {
		return ResponseEntity.ok(service.disaffiliate(dto));
	}
}
