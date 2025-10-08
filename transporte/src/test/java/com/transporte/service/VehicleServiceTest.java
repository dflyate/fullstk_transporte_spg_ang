package com.transporte.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.transporte.dto.MessageResponseDTO;
import com.transporte.dto.VehicleDTO;
import com.transporte.mapper.VehicleAffiliationMapper;
import com.transporte.mapper.VehicleMapper;
import com.transporte.model.Vehicle;
import com.transporte.repository.VehicleAffiliationRepository;
import com.transporte.repository.VehicleDriverRepository;
import com.transporte.repository.VehicleRepository;
import com.transporte.service.impl.VehicleServiceImpl;
import com.transporte.utils.Constants;

class VehicleServiceTest {
	
	private VehicleMapper mapper = Mappers.getMapper(VehicleMapper.class);
	
	private VehicleAffiliationMapper vehicleAffiliationMapper = Mappers.getMapper(VehicleAffiliationMapper.class);
	
	@Mock
	private VehicleRepository repository;
	
	@Mock
	private VehicleAffiliationRepository vehicleAffiliationRepository;
	
	@Mock
	private VehicleDriverRepository vehicleDriverRepository;
	
	private VehicleService service;
	
	@BeforeEach
	void setUp() {
		
		//reemplaza la anotación @ExtendWith
		MockitoAnnotations.openMocks(this);
		
		//linea necesaria para objetos no mockeados como los mappers
		service= new VehicleServiceImpl(repository,vehicleAffiliationRepository, vehicleDriverRepository, mapper, vehicleAffiliationMapper);
	}
	
	@Test
	void testFindAll_Success() {
		// Arrange 
		int pageNumber = 0;
		int size = 10;
		Pageable pageable = PageRequest.of(pageNumber, size);
		
		List<Vehicle> vehicles = List.of(
				getNewVehicleEntity(), getNewVehicleEntity()
			);
		
		Page<Vehicle> vehiclePage = new PageImpl<>(vehicles, PageRequest.of(pageNumber, size), vehicles.size());
		when(repository.findAll(pageable)).thenReturn(vehiclePage);
		
		// Act
		Page<VehicleDTO> resultPage = service.findAll(pageNumber, size);
		
		// Assert
		assertNotNull(resultPage.getContent());
		assertEquals(2, resultPage.getContent().size());
		assertEquals("modelo", resultPage.getContent().get(0).getModel());
	}
	
	@Test
	void testFindByTransportCompany_Success() {
		// Arrange 
		int pageNumber = 0;
		int size = 10;
		Long transportCompanyId = 1L;
		Pageable pageable = PageRequest.of(pageNumber, size);
		
		List<Vehicle> vehicles = List.of(
				getNewVehicleEntity(), getNewVehicleEntity()
			);
		
		Page<Vehicle> vehiclePage = new PageImpl<>(vehicles, PageRequest.of(pageNumber, size), vehicles.size());
		when(repository.findByTransportCompanyId(transportCompanyId, pageable)).thenReturn(vehiclePage);
		
		// Act
		Page<VehicleDTO> resultPage = service.findAllByTransportCompany(transportCompanyId, pageNumber, size);
		
		// Assert
		assertNotNull(resultPage.getContent());
		assertEquals(2, resultPage.getContent().size());
		assertEquals("modelo", resultPage.getContent().get(0).getModel());
	}
	
	@Test
	void testFindAvailableVehicles_Success() {
		// Arrange 
		int pageNumber = 0;
		int size = 10;
		
		List<Vehicle> vehicles = List.of(
				getNewVehicleEntity(), getNewVehicleEntity()
			);
		
		Page<Vehicle> vehiclePage = new PageImpl<>(vehicles, PageRequest.of(pageNumber, size), vehicles.size());
		when(repository.findAvailableVehicles(Pageable.unpaged())).thenReturn(vehiclePage);
		
		List<Vehicle> vehiclesInAffiliations = List.of(
				getNewVehicleEntity()
			);
		
		Page<Vehicle> vehiclePageInAffiliations = new PageImpl<>(vehiclesInAffiliations, PageRequest.of(pageNumber, size), vehiclesInAffiliations.size());
		when(repository.findAvailableVehiclesInAffiliations(Pageable.unpaged())).thenReturn(vehiclePageInAffiliations);
		
		// Act
		Page<VehicleDTO> resultPage = service.findAvailableVehicles(pageNumber, size);
		
		// Assert
		assertNotNull(resultPage.getContent());
		assertEquals(1, resultPage.getContent().size());
		assertEquals("modelo", resultPage.getContent().get(0).getModel());
	}
	
	@Test
	void testFindById_Success() {
		// Arrange
		Long id = 1L;
		
		Vehicle vehicle = getNewVehicleEntity();
		when(repository.findById(id)).thenReturn(Optional.of(vehicle));
		
		// Act
		VehicleDTO resultDto = service.findById(id);
		
		// Assert
		assertNotNull(resultDto);
		assertEquals(vehicle.getBrand(),resultDto.getBrand());
	}
	
	@Test
	void testCreate_Success() {
		// Arrange
		VehicleDTO dto = getNewVehicleDTO();
		
		Vehicle vehicle = getNewVehicleEntity();
		
		when(repository.save(vehicle)).thenReturn(vehicle);
		
		// Act
		MessageResponseDTO response = service.create(dto);
		
		// Assert
		assertNotNull(response);
		assertEquals(Constants.CREATED_SPA,response.getMessage());
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
	
	private Vehicle getNewVehicleEntity() {
		Vehicle entity= new Vehicle();
		entity.setPlate("vehiculo1");
		entity.setEngine("motor");
		entity.setChassis("chasis");
		entity.setModel("modelo");
		entity.setRegistrationDate(LocalDate.now());
		entity.setSeatedPassengers((short) 1);
		entity.setStandingPassengers((short) 2);
		entity.setDryWeight(22.2);
		entity.setGrossWeight(22.3);
		entity.setDoors((short) 1);
		entity.setBrand("marca");
		entity.setLine("linea");
		return entity;
	}

}
