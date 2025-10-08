package com.transporte.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transporte.dto.MessageResponseDTO;
import com.transporte.dto.VehicleAffiliationDTO;
import com.transporte.dto.VehicleDTO;
import com.transporte.mapper.VehicleAffiliationMapper;
import com.transporte.mapper.VehicleMapper;
import com.transporte.model.Vehicle;
import com.transporte.model.VehicleAffiliation;
import com.transporte.repository.VehicleAffiliationRepository;
import com.transporte.repository.VehicleDriverRepository;
import com.transporte.repository.VehicleRepository;
import com.transporte.service.VehicleService;
import com.transporte.utils.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleServiceImpl implements VehicleService{
	
	private final VehicleRepository repository;
	private final VehicleAffiliationRepository vehicleAffiliationRepository;
	private final VehicleDriverRepository vehicleDriverRepository;
	
	private final VehicleMapper mapper;
	private final VehicleAffiliationMapper vehicleAffiliationMapper;
	
	@Override
	@Transactional(readOnly = true)
	public Page<VehicleDTO> findAll(int pageNumber,int size) {
		log.info(Constants.FETCHING_ALL , Constants.VEHICLES);
		Pageable pageable = PageRequest.of(pageNumber,size);
		Page<VehicleDTO> page = repository.findAll(pageable).map(mapper::toDTO);
		log.info(Constants.FOUND, page.getSize(), Constants.VEHICLES);
		return page;
	}


	@Override
	@Transactional(readOnly = true)
	public Page<VehicleDTO> findAllByTransportCompany(Long transportCompanyId,int pageNumber,int size) {
		log.info(Constants.FETCHING_ALL , Constants.VEHICLES);
		Pageable pageable = PageRequest.of(pageNumber,size);
		Page<VehicleDTO> page = repository.findByTransportCompanyId(transportCompanyId,pageable).map(mapper::toDTO);
		log.info(Constants.FOUND, page.getSize(), Constants.VEHICLES);
		return page;
	}
	
	@Override
	public Page<VehicleDTO> findAvailableVehicles(int pageNumber, int size) {
	    log.info(Constants.FETCHING_ALL, Constants.VEHICLES);

	    Pageable pageable = PageRequest.of(pageNumber, size);

	    List<VehicleDTO> list1 = repository.findAvailableVehicles(Pageable.unpaged())
	        .map(mapper::toDTO)
	        .getContent();

	    List<VehicleDTO> list2 = repository.findAvailableVehiclesInAffiliations(Pageable.unpaged())
	        .map(mapper::toDTO)
	        .getContent();

	    List<VehicleDTO> mergedList = Stream.concat(list1.stream(), list2.stream())
	        .distinct()
	        .collect(Collectors.toList());

	    int start = (int) pageable.getOffset();
	    int end = Math.min(start + pageable.getPageSize(), mergedList.size());

	    List<VehicleDTO> pageContent = mergedList.subList(start, end);
	    Page<VehicleDTO> page = new PageImpl<>(pageContent, pageable, mergedList.size());

	    log.info(Constants.FOUND, page.getSize(), Constants.VEHICLES);
	    return page;
	}

	@Override
	@Transactional(readOnly = true)
	public VehicleDTO findById(Long id) {
		log.info(Constants.FETCHING_BY_ID, Constants.VEHICLES, id);
		Vehicle entity = repository.findById(id).orElseThrow(() -> {
			log.warn(Constants.NOT_FOUND_BY_ID, Constants.VEHICLE, id);
			return new NoSuchElementException(Constants.NOT_FOUND_SPA);
		});
		log.info(Constants.FOUND, Constants.VEHICLES, entity.getId());
		return mapper.toDTO(entity);
	}

	@Override
	@Transactional
	public MessageResponseDTO create(VehicleDTO dto) {
		log.info(Constants.CREATING_NEW, Constants.VEHICLES, dto.getBrand());
		dto.setId(null);
		dto.setRegistrationDate(LocalDate.now());
		Vehicle entity = mapper.toEntity(dto);
		Vehicle saved = repository.save(entity);
		
		log.info(Constants.CREATED, Constants.VEHICLES, saved.getId());
		return new MessageResponseDTO(Constants.CREATED_SPA);
	}

	@Override
	@Transactional
	public MessageResponseDTO update(VehicleDTO dto) {
		Long id = dto.getId();
		log.info(Constants.UPDATING, Constants.VEHICLE, id);

		Vehicle existing = repository.findById(id).orElseThrow(() -> {
			log.warn(Constants.NOT_FOUND_BY_ID, Constants.VEHICLE, id);
			return new NoSuchElementException(Constants.NOT_FOUND_SPA);
		});

		Vehicle updatedEntity = mapper.toEntity(dto);
		updatedEntity.setId(existing.getId());
		updatedEntity.setRegistrationDate(existing.getRegistrationDate());
		Vehicle saved = repository.save(updatedEntity);
		log.info(Constants.UPDATED, Constants.VEHICLE, saved.getId());
		return new MessageResponseDTO(Constants.UPDATED_SPA);
	}

	@Override
	@Transactional
	public MessageResponseDTO delete(Long id) {
        log.info(Constants.DELETING, Constants.VEHICLE, id);
		
		if(!repository.existsById(id)) {
			log.warn(Constants.NOT_FOUND_BY_ID, Constants.VEHICLE, id);
			throw new NoSuchElementException(Constants.NOT_FOUND_SPA);
		}
		
		if(Boolean.TRUE.equals(repository.hasAffiliationOrDrivers(id))) {
			log.warn("This vehicle is related to transportation companies and/or drivers");
			throw new NoSuchElementException("Este vehículo tiene relación con empresas de transporte y/o conductores.");
		}
		
		repository.deleteById(id);
		log.info(Constants.DELETING, Constants.VEHICLE, id);
		return new MessageResponseDTO(Constants.DELETED_SPA);
	}

	@Override
	@Transactional
	public MessageResponseDTO affiliate(VehicleAffiliationDTO dto) {
		dto.setAffiliationDate(LocalDateTime.now());
		
		if(vehicleAffiliationRepository.findLinkByCompanyAndVehicle(dto.getTransportCompanyId(), dto.getVehicleId()).isPresent()) {
			log.warn("The requested relationship already exists");
			throw new IllegalArgumentException("La relación solicitada ya existe");
		}
		
		if(!vehicleAffiliationRepository.findByOtherCompaniesAndVehicle(dto.getTransportCompanyId(), dto.getVehicleId()).isEmpty()) {
			log.warn("The vehicle is already linked to another company");
			throw new NoSuchElementException("El vehículo ya se encuentra vinculado a otra empresa");
		}
		
		Optional<VehicleAffiliation> optionalEntity = vehicleAffiliationRepository.findByCompanyAndVehicle(dto.getTransportCompanyId(), dto.getVehicleId());
		if(optionalEntity.isPresent()) {
			VehicleAffiliation entity = optionalEntity.get();
			entity.setDisaffiliationDate(null);
			vehicleAffiliationRepository.save(entity);
			log.info(Constants.UPDATED, Constants.VEHICLE_AFFILIATION, entity.getId());	
			return new MessageResponseDTO(Constants.UPDATED_SPA);
		}
		VehicleAffiliation vehicleAffiliation = vehicleAffiliationMapper.toEntity(dto);
		vehicleAffiliationRepository.save(vehicleAffiliation);
		log.info(Constants.CREATED, Constants.VEHICLE_AFFILIATION, vehicleAffiliation.getId());	
		return new MessageResponseDTO(Constants.CREATED_SPA);
	}
	
	@Override
	@Transactional
	public MessageResponseDTO disaffiliate(VehicleAffiliationDTO dto) {
        log.info(Constants.UPDATING, Constants.VEHICLE_AFFILIATION, dto.getTransportCompanyId());
		
		if(!vehicleAffiliationRepository.findByCompanyAndVehicle(dto.getTransportCompanyId(), dto.getVehicleId()).isPresent()) {
			log.warn(Constants.NOT_FOUND_BY_ID, Constants.VEHICLE_AFFILIATION, dto.getTransportCompanyId());
			throw new NoSuchElementException(Constants.NOT_FOUND_SPA);
		}
		
		if(!vehicleDriverRepository.findByVehicleId(dto.getVehicleId()).isEmpty()) {
			log.warn("The selected vehicle contains some drivers");
			throw new NoSuchElementException("El vehículo contiene conductores afiliados");
		}
		
		vehicleAffiliationRepository.updateDisaffiliationDate(LocalDateTime.now(), dto.getTransportCompanyId(), dto.getVehicleId());
		log.info(Constants.UPDATING, Constants.VEHICLE_AFFILIATION, dto.getTransportCompanyId());
		return new MessageResponseDTO(Constants.UPDATED_SPA);
	}
	

}
