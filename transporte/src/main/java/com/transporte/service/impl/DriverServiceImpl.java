package com.transporte.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transporte.dto.DriverDTO;
import com.transporte.dto.MessageResponseDTO;
import com.transporte.dto.VehicleDriverDTO;
import com.transporte.mapper.DriverMapper;
import com.transporte.mapper.VehicleDriverMapper;
import com.transporte.model.Driver;
import com.transporte.model.TransportCompany;
import com.transporte.model.VehicleDriver;
import com.transporte.repository.DriverRepository;
import com.transporte.repository.TransportCompanyRepository;
import com.transporte.repository.VehicleDriverRepository;
import com.transporte.service.DriverService;
import com.transporte.utils.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverServiceImpl implements DriverService{
	
	private final TransportCompanyRepository transportCompanyRepository;

	private final DriverRepository repository;
	private final VehicleDriverRepository vehicleDriverRepository;
	
	private final DriverMapper mapper;
	private final VehicleDriverMapper vehicleDriverMapper;
	
	@Override
	@Transactional(readOnly = true)
	public Page<DriverDTO> findAll(int pageNumber, int size, Long transportCompanyId) {
		log.info(Constants.FETCHING_ALL , Constants.DRIVERS);
		
		Pageable pageable = PageRequest.of(pageNumber, size);
		Page<DriverDTO> page = repository.findByTransportCompanyId(transportCompanyId, pageable).map(mapper::toDTO);
		log.info(Constants.FOUND, page.getSize(), Constants.DRIVERS);
		return page;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<DriverDTO> findAllByVehicle(Long vehicleId,int pageNumber, int size) {
		log.info(Constants.FETCHING_ALL , Constants.DRIVERS);
		
		Pageable pageable = PageRequest.of(pageNumber, size);
		Page<DriverDTO> page = repository.findByVehicleId(vehicleId,pageable).map(mapper::toDTO);
		log.info(Constants.FOUND, page.getSize(), Constants.DRIVERS);
		return page;
	}
	
	@Override
	public Page<DriverDTO> findAvailableDrivers(int pageNumber, int size, Long vehicleId, Long transportCompanyId) {
	    log.info(Constants.FETCHING_ALL, Constants.DRIVERS);

	    Pageable pageable = PageRequest.of(pageNumber, size);

	    List<DriverDTO> list1 = repository.findAvailableDrivers(Pageable.unpaged(),transportCompanyId)
	        .map(mapper::toDTO)
	        .getContent();

	    List<DriverDTO> list2 = repository.findAvailableDriversInAffiliations(Pageable.unpaged(),vehicleId,transportCompanyId)
	        .map(mapper::toDTO)
	        .getContent();

	    List<DriverDTO> mergedList = Stream.concat(list1.stream(), list2.stream())
	        .distinct()
	        .collect(Collectors.toList());

	    int start = (int) pageable.getOffset();
	    int end = Math.min(start + pageable.getPageSize(), mergedList.size());

	    List<DriverDTO> pageContent = mergedList.subList(start, end);
	    Page<DriverDTO> page = new PageImpl<>(pageContent, pageable, mergedList.size());

	    log.info(Constants.FOUND, page.getSize(), Constants.DRIVERS);
	    return page;
	}

	@Override
	@Transactional(readOnly = true)
	public DriverDTO findById(Long id) {
		log.info(Constants.FETCHING_BY_ID, Constants.DRIVERS, id);
		Driver entity = repository.findById(id).orElseThrow(() -> {
			log.warn(Constants.NOT_FOUND_BY_ID, Constants.DRIVER, id);
			return new NoSuchElementException(Constants.NOT_FOUND_SPA);
		});
		log.info(Constants.FOUND, Constants.DRIVERS, entity.getId());
		return mapper.toDTO(entity);
	}

	@Override
	@Transactional
	public MessageResponseDTO create(DriverDTO dto) {
		log.info(Constants.CREATING_NEW, Constants.DRIVERS, dto.getFullName());
		
		TransportCompany transportCompanyEntity = transportCompanyRepository.findById(dto.getTransportCompanyId()).orElseThrow(() -> {
			log.warn(Constants.NOT_FOUND_BY_ID, Constants.TRANSPORT_COMPANY, dto.getTransportCompanyId());
			return new NoSuchElementException(Constants.NOT_FOUND_SPA);
		});
		
		dto.setId(null);
		Driver entity = mapper.toEntity(dto);
		entity.setCompany(transportCompanyEntity);
		Driver saved = repository.save(entity);
		
		log.info(Constants.CREATED, Constants.DRIVERS, saved.getId());
		return new MessageResponseDTO(Constants.CREATED_SPA);
	}

	@Override
	@Transactional
	public MessageResponseDTO update(DriverDTO dto) {
		Long id = dto.getId();
		log.info(Constants.UPDATING, Constants.DRIVER, id);

		Driver existing = repository.findById(id).orElseThrow(() -> {
			log.warn(Constants.NOT_FOUND_BY_ID, Constants.DRIVER, id);
			return new NoSuchElementException(Constants.NOT_FOUND_SPA);
		});

		Driver updatedEntity = mapper.toEntity(dto);
		updatedEntity.setId(existing.getId());
		updatedEntity.setCompany(existing.getCompany());
		Driver saved = repository.save(updatedEntity);
		log.info(Constants.UPDATED, Constants.DRIVER, saved.getId());
		return new MessageResponseDTO(Constants.UPDATED_SPA);
	}

	@Override
	@Transactional
	public MessageResponseDTO delete(Long id) {
		log.info(Constants.DELETING, Constants.DRIVER, id);
		
		if(!repository.existsById(id)) {
			log.warn(Constants.NOT_FOUND_BY_ID, Constants.DRIVER, id);
			throw new NoSuchElementException(Constants.NOT_FOUND_SPA);
		}
		
		if(Boolean.TRUE.equals(repository.hasVehicles(id))) {
			log.warn("This driver is related to one or more vehicles");
			throw new NoSuchElementException("Este conductor tiene relación con uno o varios vehículos");
		}
		
		repository.deleteById(id);
		log.info(Constants.DELETING, Constants.DRIVER, id);
		return new MessageResponseDTO(Constants.DELETED_SPA);
	}

	@Override
	@Transactional
	public MessageResponseDTO affiliate(VehicleDriverDTO dto) {
		dto.setAffiliationDate(LocalDateTime.now());
		
		if(vehicleDriverRepository.findLinkByVehicleAndDriver(dto.getVehicleId(), dto.getDriverId()).isPresent()) {
			log.warn("The requested relationship already exists");
			throw new IllegalArgumentException("La relación solicitada ya existe");
		}
		
		Optional<VehicleDriver> optionalEntity = vehicleDriverRepository.findbyVehicleAndDriver(dto.getVehicleId(), dto.getDriverId());
		if(optionalEntity.isPresent()) {
			VehicleDriver entity = optionalEntity.get();
			entity.setDisaffiliationDate(null);
			vehicleDriverRepository.save(entity);
			log.info(Constants.UPDATED, Constants.VEHICLE_DRIVER, entity.getId());	
			return new MessageResponseDTO(Constants.UPDATED_SPA);
		}
		
		VehicleDriver vehicleDriver = vehicleDriverMapper.toEntity(dto);
		vehicleDriverRepository.save(vehicleDriver);
		log.info(Constants.CREATED, Constants.DRIVER_AFFILIATION, vehicleDriver.getId());
		return new MessageResponseDTO(Constants.CREATED_SPA);
	}

	@Override
	@Transactional
	public MessageResponseDTO disaffiliate(VehicleDriverDTO dto) {
		 log.info(Constants.UPDATING, Constants.DRIVER_AFFILIATION, dto.getDriverId());
			
		if(!vehicleDriverRepository.findbyVehicleAndDriver(dto.getVehicleId(), dto.getDriverId()).isPresent()) {
			log.warn(Constants.NOT_FOUND_BY_ID, Constants.DRIVER_AFFILIATION, dto.getDriverId());
			throw new NoSuchElementException(Constants.NOT_FOUND_SPA);
		}
		
		vehicleDriverRepository.updateDisaffiliationDate(LocalDateTime.now(), dto.getVehicleId(), dto.getDriverId());
		log.info(Constants.UPDATING, Constants.DRIVER_AFFILIATION, dto.getDriverId());
		return new MessageResponseDTO(Constants.UPDATED_SPA);
	}

}
