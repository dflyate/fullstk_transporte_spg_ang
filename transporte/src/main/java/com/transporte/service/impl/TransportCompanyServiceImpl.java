package com.transporte.service.impl;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transporte.dto.MessageResponseDTO;
import com.transporte.dto.TransportCompanyDTO;
import com.transporte.dto.TransportCompanyRegistrationDTO;
import com.transporte.mapper.TransportCompanyMapper;
import com.transporte.model.LegalRepresentative;
import com.transporte.model.TransportCompany;
import com.transporte.model.User;
import com.transporte.repository.TransportCompanyRepository;
import com.transporte.service.LegalRepresentativeService;
import com.transporte.service.TransportCompanyService;
import com.transporte.service.UserService;
import com.transporte.utils.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransportCompanyServiceImpl implements TransportCompanyService {

	private final TransportCompanyRepository repository;
	private final TransportCompanyMapper mapper;
	
	private final LegalRepresentativeService legalRepresentativeService;
	private final UserService userService;

	@Override
	@Transactional(readOnly = true)
	public Page<TransportCompanyDTO> findAll(int pageNumber, int size) {
		log.info(Constants.FETCHING_ALL , Constants.TRANSPORT_COMPANIES);
		Pageable pageable = PageRequest.of(pageNumber, size);
		Page<TransportCompanyDTO> page = repository.findAll(pageable).map(mapper::toDTO);
		log.info(Constants.FOUND, page.getSize(), Constants.TRANSPORT_COMPANIES);
		return page;
	}

	@Override
	@Transactional(readOnly = true)
	public TransportCompanyRegistrationDTO findById(Long id) {
		TransportCompanyRegistrationDTO dto = new TransportCompanyRegistrationDTO();
		log.info(Constants.FETCHING_BY_ID, Constants.TRANSPORT_COMPANIES, id);
		TransportCompany entity = repository.findById(id).orElseThrow(() -> {
			log.warn(Constants.NOT_FOUND_BY_ID, Constants.TRANSPORT_COMPANY, id);
			return new NoSuchElementException(Constants.NOT_FOUND_SPA);
		});
		log.info(Constants.FOUND, Constants.TRANSPORT_COMPANIES, entity.getId());
		dto.setTransportCompany(mapper.toDTO(entity));
		dto.setLegalRepresentative(legalRepresentativeService.findById(entity.getLegalRepresentative().getId()));
		dto.setUser(userService.findById(entity.getUser().getId()));
		return dto;

	}

	public TransportCompany create(TransportCompanyDTO dto) {
		log.info(Constants.CREATING_NEW, Constants.TRANSPORT_COMPANY, dto.getFullName());
		dto.setId(null);
		TransportCompany entity = mapper.toEntity(dto);
		TransportCompany saved = repository.save(entity);
		
		log.info(Constants.CREATED, Constants.TRANSPORT_COMPANY, saved.getId());
		return saved;
	}

	@Override
	@Transactional
	public MessageResponseDTO update(TransportCompanyRegistrationDTO dto) {
		Long id = dto.getTransportCompany().getId();
		log.info(Constants.UPDATING, Constants.TRANSPORT_COMPANY, id);

		TransportCompany existing = repository.findById(id).orElseThrow(() -> {
			log.warn(Constants.NOT_FOUND_BY_ID, Constants.TRANSPORT_COMPANY, id);
			return new NoSuchElementException(Constants.NOT_FOUND_SPA);
		});

		TransportCompany updatedEntity = mapper.toEntity(dto.getTransportCompany());
		updatedEntity.setId(existing.getId());
		dto.getLegalRepresentative().setId(existing.getLegalRepresentative().getId());
		dto.getUser().setId(existing.getUser().getId());
		LegalRepresentative legalRepresentative= legalRepresentativeService.update(dto.getLegalRepresentative(), updatedEntity);
		User user = userService.update(dto.getUser(), updatedEntity);
		
		updatedEntity.setLegalRepresentative(legalRepresentative);
		updatedEntity.setUser(user);
		
		TransportCompany saved = repository.save(updatedEntity);
		log.info(Constants.UPDATED, Constants.TRANSPORT_COMPANY, saved.getId());
		return new MessageResponseDTO(Constants.UPDATED_SPA);
	}

	@Override
	@Transactional
	public MessageResponseDTO delete(Long id) {
		log.info(Constants.DELETING, Constants.TRANSPORT_COMPANY, id);
		
		TransportCompany entity = repository.findById(id).orElseThrow(() -> {
			log.warn(Constants.NOT_FOUND_BY_ID, Constants.TRANSPORT_COMPANY, id);
			return new NoSuchElementException(Constants.NOT_FOUND_SPA);
		});
		
		if(Boolean.TRUE.equals(repository.hasAffiliation(id))) {
			log.warn("This company is related to vehicles");
			throw new NoSuchElementException("Esta empresa tiene relación con vehículos.");
		}
		
		Long legalRepresentativeId = entity.getLegalRepresentative().getId();
		Long userId = entity.getUser().getId();
		
		entity.setLegalRepresentative(null);
		entity.setUser(null);
		repository.save(entity);
		log.info(Constants.UPDATED, Constants.TRANSPORT_COMPANY, entity.getId());
		
		legalRepresentativeService.delete(legalRepresentativeId);
		userService.delete(userId);
		repository.deleteById(id);
		log.info(Constants.DELETING, Constants.TRANSPORT_COMPANY, id);
		return new MessageResponseDTO(Constants.DELETED_SPA);
	}

	@Override
	@Transactional
	public MessageResponseDTO createRelatedObjects(TransportCompanyRegistrationDTO dto) {
		TransportCompany transportCompany = create(dto.getTransportCompany());
		dto.getLegalRepresentative().setTransportCompanyId(transportCompany.getId());
		dto.getUser().setTransportCompanyid(transportCompany.getId());
		LegalRepresentative legalRepresentative= legalRepresentativeService.create(dto.getLegalRepresentative());
		User user = userService.create(dto.getUser());
		
		transportCompany.setLegalRepresentative(legalRepresentative);
		transportCompany.setUser(user);
		repository.save(transportCompany);
		log.info(Constants.UPDATED, Constants.TRANSPORT_COMPANY, transportCompany.getId());
		
		return new MessageResponseDTO(Constants.CREATED_SPA);
	}

}
