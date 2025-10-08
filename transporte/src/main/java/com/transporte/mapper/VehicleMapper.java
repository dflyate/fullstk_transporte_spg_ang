package com.transporte.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.transporte.dto.VehicleDTO;
import com.transporte.model.Vehicle;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

	VehicleMapper INSTANCE = Mappers.getMapper(VehicleMapper.class);
	
	Vehicle toEntity(VehicleDTO dto);
	
	VehicleDTO toDTO(Vehicle entity);
}
