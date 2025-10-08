package com.transporte.service.impl;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transporte.dto.LegalRepresentativeDTO;
import com.transporte.mapper.LegalRepresentativeMapper;
import com.transporte.model.LegalRepresentative;
import com.transporte.model.TransportCompany;
import com.transporte.repository.LegalRepresentativeRepository;
import com.transporte.service.LegalRepresentativeService;
import com.transporte.utils.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LegalRepresentativeServiceImpl implements LegalRepresentativeService {

	private final LegalRepresentativeRepository repository;
	private final LegalRepresentativeMapper mapper;

	@Override
	@Transactional(readOnly = true)
	public LegalRepresentativeDTO findById(Long id) {
		log.info(Constants.FETCHING_BY_ID, Constants.LEGAL_REPRESENTATIVES, id);
		LegalRepresentative entity = repository.findById(id).orElseThrow(() -> {
			log.warn(Constants.NOT_FOUND_BY_ID, Constants.LEGAL_REPRESENTATIVE, id);
			return new NoSuchElementException(Constants.NOT_FOUND_SPA);
		});
		log.info(Constants.FOUND, Constants.LEGAL_REPRESENTATIVE, entity.getId());
		return mapper.toDTO(entity);
	}

	@Override
	public LegalRepresentative create(LegalRepresentativeDTO dto) {
		log.info(Constants.CREATING_NEW, Constants.LEGAL_REPRESENTATIVE, dto.getFullName());
		dto.setId(null);
		LegalRepresentative entity = mapper.toEntity(dto);
		LegalRepresentative saved = repository.save(entity);
		log.info(Constants.CREATED, Constants.LEGAL_REPRESENTATIVE, saved.getId());

		return saved;
	}

	@Override
	public LegalRepresentative update(LegalRepresentativeDTO dto, TransportCompany transportCompany) {
		Long id = dto.getId();
		log.info(Constants.UPDATING, Constants.LEGAL_REPRESENTATIVE, id);
		
		LegalRepresentative entity = repository.findById(id).orElseThrow(() -> {
			log.warn(Constants.NOT_FOUND_BY_ID, Constants.LEGAL_REPRESENTATIVE, id);
			return new NoSuchElementException(Constants.NOT_FOUND_SPA);
		});

		LegalRepresentative updatedEntity = mapper.toEntity(dto);
		updatedEntity.setId(entity.getId());
		LegalRepresentative saved = repository.save(updatedEntity);
		log.info(Constants.UPDATED, Constants.LEGAL_REPRESENTATIVE, saved.getId());
		return saved;
	}

	@Override
	public void delete(Long id) {
		log.info(Constants.DELETING, Constants.LEGAL_REPRESENTATIVE, id);

		LegalRepresentative entity = repository.findById(id).orElseThrow(() -> {
			log.warn(Constants.NOT_FOUND_BY_ID, Constants.LEGAL_REPRESENTATIVE, id);
			return new NoSuchElementException(Constants.NOT_FOUND_SPA);
		});
		
		repository.deleteById(entity.getId());
		log.info(Constants.DELETING, Constants.LEGAL_REPRESENTATIVE, id);
	}
}
