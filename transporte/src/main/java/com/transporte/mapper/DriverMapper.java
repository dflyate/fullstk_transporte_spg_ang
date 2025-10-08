package com.transporte.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.transporte.dto.DriverDTO;
import com.transporte.model.Driver;

@Mapper(componentModel = "spring")
public interface DriverMapper {

	DriverMapper INSTANCE = Mappers.getMapper(DriverMapper.class);
	
	Driver toEntity(DriverDTO dto);
	
	DriverDTO toDTO(Driver entity);
}
