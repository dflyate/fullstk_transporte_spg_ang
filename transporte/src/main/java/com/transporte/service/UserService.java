package com.transporte.service;

import org.springframework.data.domain.Page;

import com.transporte.dto.UserDTO;
import com.transporte.model.TransportCompany;
import com.transporte.model.User;

public interface UserService {

	Page<UserDTO> findAll(int page, int size);
	UserDTO findById(Long id);
	User create(UserDTO dto);
	User update(UserDTO dto, TransportCompany transportCompany);
	void delete(Long id);
}
