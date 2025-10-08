package com.transporte.service.impl;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transporte.dto.UserDTO;
import com.transporte.exception.UsernameAlreadyExistsException;
import com.transporte.mapper.UserMapper;
import com.transporte.model.TransportCompany;
import com.transporte.model.User;
import com.transporte.repository.UserRepository;
import com.transporte.service.UserService;
import com.transporte.utils.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
	
	private final UserRepository repository;
	private final UserMapper mapper;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional(readOnly = true)
	public Page<UserDTO> findAll(int pageNumber, int size) {
		log.info(Constants.FETCHING_ALL , Constants.USERS);
		Pageable pageable = PageRequest.of(pageNumber, size);
		Page<UserDTO> page = repository.findAll(pageable).map(mapper::toDTO);
		log.info(Constants.FOUND, page.getSize(), Constants.USERS);
		return page;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		log.info(Constants.FETCHING_BY_ID, Constants.USER, id);
		User entity = repository.findById(id).orElseThrow(() -> {
			log.warn(Constants.NOT_FOUND_BY_ID, Constants.USER, id);
			return new NoSuchElementException(Constants.NOT_FOUND_SPA);
		});
		entity.setPassword(null);
		log.info(Constants.FOUND, Constants.USER, entity.getId());
		return mapper.toDTO(entity);
	}

	@Override
	public User create(UserDTO dto) {
		
		if (repository.existsByUsername(dto.getUsername())) {
			log.warn(Constants.EXISTING_USERNAME);
		    throw new UsernameAlreadyExistsException(dto.getUsername());
		}
		
		dto.setPassword(passwordEncoder.encode(dto.getPassword()));
		log.info(Constants.CREATING_NEW, Constants.USER, dto.getUsername());
		dto.setId(null);
		User entity = mapper.toEntity(dto);
		entity.setTransportCompany(findTransportCompanyForUser(dto.getTransportCompanyid()));
		User saved = repository.save(entity);
		log.info(Constants.CREATED, Constants.USER, saved.getId());
		return saved;
	}

	@Override
	public User update(UserDTO dto, TransportCompany transportCompany) {
		
		Long id = dto.getId();
		dto.setPassword(dto.getPassword() != null ? passwordEncoder.encode(dto.getPassword()) : null);
		log.info(Constants.UPDATING, Constants.USER, id);

		User existing = repository.findById(id).orElseThrow(() -> {
			log.warn(Constants.NOT_FOUND_BY_ID, Constants.USER, id);
			return new NoSuchElementException(Constants.NOT_FOUND_SPA);
		});
		
		if (repository.existsByUsername(dto.getUsername()) && !dto.getUsername().equals(existing.getUsername())) {
			log.warn(Constants.EXISTING_USERNAME);
		    throw new UsernameAlreadyExistsException(dto.getUsername());
		}
		

		User updatedEntity = mapper.toEntity(dto);
		updatedEntity.setId(existing.getId());
		updatedEntity.setPassword(updatedEntity.getPassword() != null ? updatedEntity.getPassword() : existing.getPassword());
		updatedEntity.setTransportCompany(transportCompany);
		
		User saved = repository.save(updatedEntity);
		log.info(Constants.UPDATED, Constants.USER, saved.getId());
		return saved;
	}

	@Override
	public void delete(Long id) {
		log.info(Constants.DELETING, Constants.USER, id);
		
		User entity = repository.findById(id).orElseThrow(() -> {
			log.warn(Constants.NOT_FOUND_BY_ID, Constants.USER, id);
			return new NoSuchElementException(Constants.NOT_FOUND_SPA);
		});
		
		repository.deleteById(entity.getId());
		log.info(Constants.DELETING, Constants.USER, id);
	}
	
	private TransportCompany findTransportCompanyForUser(Long id) {
		TransportCompany transportCompany = new TransportCompany();
		transportCompany.setId(id);
		return transportCompany;
	}

}
