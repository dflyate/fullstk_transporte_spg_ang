package com.transporte.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.transporte.dto.UserDTO;
import com.transporte.model.TransportCompany;
import com.transporte.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	@Mapping(target = "transportCompany", ignore = true)
	User toEntity(UserDTO dto);
	
	@Mapping(source = "transportCompany.id", target = "transportCompanyid")
	UserDTO toDTO(User entity);
	
	default TransportCompany mapTransportCompanyidToTransportCompany (Long id) {
		if(id == null) return null;
		TransportCompany company = new TransportCompany();
		company.setId(id);
		return company;
	}
	
}
