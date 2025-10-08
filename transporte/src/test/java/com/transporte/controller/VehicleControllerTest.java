package com.transporte.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.transporte.dto.MessageResponseDTO;
import com.transporte.dto.VehicleAffiliationDTO;
import com.transporte.dto.VehicleDTO;
import com.transporte.service.VehicleService;
import com.transporte.utils.Constants;

@ExtendWith(MockitoExtension.class)
class VehicleControllerTest {

	@Mock
	private VehicleService service;
	
	@InjectMocks
	private VehicleController controller;
	
	//<nombreDelMetodo>_<resultadoEsperado> o <condición>_<resultadoEsperado>
	
	@Test
	void testCreateVehicle_ReturnsCreatedResponse() {
		//Arrange - preparar: se prepara lo necesario para ejecutar la prueba
		VehicleDTO dto = getNewVehicleDTO();
		
		MessageResponseDTO expectedResponse = new MessageResponseDTO(Constants.CREATED_SPA, null);
		when(service.create(dto)).thenReturn(expectedResponse);
		
		// Act - ejecutar: ejecución del método a probar
		ResponseEntity<MessageResponseDTO> response = controller.create(dto);
		
		// Assert - verificar: verificar resultados esperados
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(expectedResponse, response.getBody());
	}
	
	@Test
	void testAffiliate_ReturnsCreatedResponse() {
		//Arrange
		VehicleAffiliationDTO dto = getNewVehicleAffiliationDTO();
		
		MessageResponseDTO expectedResponse = new MessageResponseDTO(Constants.CREATED_SPA, null);
		when(service.affiliate(dto)).thenReturn(expectedResponse);
		
		// Act
		ResponseEntity<MessageResponseDTO> response = controller.affiliate(dto);
		
		// Assert
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(expectedResponse, response.getBody());
	}
	
	@Test
	void testDisaffiliate_ReturnsUpdatedResponse() {
		//Arrange
		VehicleAffiliationDTO dto = getNewVehicleAffiliationDTO();
		
		MessageResponseDTO expectedResponse = new MessageResponseDTO(Constants.UPDATED_SPA, null);
		when(service.disaffiliate(dto)).thenReturn(expectedResponse);
		
		// Act
		ResponseEntity<MessageResponseDTO> response = controller.disaffiliate(dto);
		
		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedResponse, response.getBody());
	}
	
	@Test
	void testUpdateVehicle_ReturnsUpdatedResponse() {
		//Arrange
		VehicleDTO dto = getNewVehicleDTO();
		
		MessageResponseDTO expectedResponse = new MessageResponseDTO(Constants.UPDATED_SPA, null);
		when(service.update(dto)).thenReturn(expectedResponse);
		
		// Act
		ResponseEntity<MessageResponseDTO> response = controller.update(dto);
		
		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedResponse, response.getBody());
	}
	
	@Test
	void testGetAllVehicles_ReturnsPagedResult() {
		//Arrange
		int page = 0;
		int size = 10;
		
		List<VehicleDTO> vehicles = List.of(
			getNewVehicleDTO(), getNewVehicleDTO()
		);
		
		Page<VehicleDTO> vehiclePage = new PageImpl<>(vehicles, PageRequest.of(page, size), vehicles.size());
		when(service.findAll(page, size)).thenReturn(vehiclePage);
		
		//Act
		ResponseEntity<Page<VehicleDTO>> response = controller.getAll(page, size);
		
		//Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(2, response.getBody().getTotalElements());
		assertEquals("vehiculo1",response.getBody().getContent().get(0).getPlate());
	}
	
	@Test
	void testGetAvailableVehicles_ReturnsPagedResult() {
		//Arrange
		int page = 0;
		int size = 10;
		
		List<VehicleDTO> vehicles = List.of(
			getNewVehicleDTO(), getNewVehicleDTO()
		);
		
		Page<VehicleDTO> vehiclePage = new PageImpl<>(vehicles, PageRequest.of(page, size), vehicles.size());
		when(service.findAvailableVehicles(page, size)).thenReturn(vehiclePage);
		
		//Act
		ResponseEntity<Page<VehicleDTO>> response = controller.getAvailableVehicles(page, size);
		
		//Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(2, response.getBody().getTotalElements());
		assertEquals("vehiculo1",response.getBody().getContent().get(0).getPlate());
	}
	
	@Test
	void testGetById_ReturnsFoundResult() {
		//Arrange
		Long id = 1L;
		
		VehicleDTO dto = getNewVehicleDTO();
		
		when(service.findById(id)).thenReturn(dto);
		
		//Act
		ResponseEntity<VehicleDTO> response = controller.getById(id);
		
		//Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(dto, response.getBody());
	}
	
	@Test
	void testGetAllVehiclesByTransportCompany_ReturnsPagedResult() {
		//Arrange
		int page = 0;
		int size = 10;
		Long transportCompanyId = 1L;
		
		List<VehicleDTO> vehicles = List.of(
			getNewVehicleDTO(), getNewVehicleDTO()
		);
		
		Page<VehicleDTO> vehiclePage = new PageImpl<>(vehicles, PageRequest.of(page, size), vehicles.size());
		when(service.findAllByTransportCompany(transportCompanyId, page, size)).thenReturn(vehiclePage);
		
		//Act
		ResponseEntity<Page<VehicleDTO>> response = controller.getAllByTransportCompany(transportCompanyId, page, size);
		
		//Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(2, response.getBody().getTotalElements());
		assertEquals("vehiculo1",response.getBody().getContent().get(0).getPlate());
	}
	
	@Test
	void testDeleteById_ReturnsDeletedResponse() {
		// Arrange
		Long id = 1L;
		MessageResponseDTO expectedResponse = new MessageResponseDTO(Constants.DELETED_SPA, null);
		when(service.delete(id)).thenReturn(expectedResponse);
		
		// Act
		ResponseEntity<MessageResponseDTO> response = controller.delete(id);
		
		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedResponse, response.getBody());
		
	}
	
	private VehicleDTO getNewVehicleDTO() {
		VehicleDTO dto= new VehicleDTO();
		dto.setPlate("vehiculo1");
		dto.setEngine("motor");
		dto.setChassis("chasis");
		dto.setModel("modelo");
		dto.setRegistrationDate(LocalDate.now());
		dto.setSeatedPassengers((short) 1);
		dto.setStandingPassengers((short) 2);
		dto.setDryWeight(22.2);
		dto.setGrossWeight(22.3);
		dto.setDoors((short) 1);
		dto.setBrand("marca");
		dto.setLine("linea");
		return dto;
	}
	
	private VehicleAffiliationDTO getNewVehicleAffiliationDTO() {
		VehicleAffiliationDTO dto= new VehicleAffiliationDTO();
		dto.setAffiliationDate(LocalDateTime.now());
		dto.setDisaffiliationDate(LocalDateTime.now());
		dto.setTransportCompanyId(1L);
		dto.setVehicleId(1L);
		return dto;
	}
	
	
}
