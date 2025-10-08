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
import com.transporte.dto.TransportCompanyDTO;
import com.transporte.dto.TransportCompanyRegistrationDTO;
import com.transporte.service.TransportCompanyService;
import com.transporte.validation.ConsultParametersFirstGroupValidation;
import com.transporte.validation.ConsultParametersSecondGroupValidation;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transport-company")
@RequiredArgsConstructor
public class TransportCompanyController {

	private final TransportCompanyService service;
	
	@PostMapping
	public ResponseEntity<MessageResponseDTO> create(@Validated(ConsultParametersSecondGroupValidation.class) @RequestBody TransportCompanyRegistrationDTO dto){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.createRelatedObjects(dto));
	}
	
	@GetMapping
	public ResponseEntity<Page<TransportCompanyDTO>> getAll(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(service.findAll(page, size));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TransportCompanyRegistrationDTO> getById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PutMapping
	public ResponseEntity<MessageResponseDTO> update(@Validated(ConsultParametersFirstGroupValidation.class) @RequestBody TransportCompanyRegistrationDTO dto ) {
		return ResponseEntity.ok(service.update(dto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponseDTO> delete(@PathVariable Long id) {
		return ResponseEntity.ok(service.delete(id));
	}
}
