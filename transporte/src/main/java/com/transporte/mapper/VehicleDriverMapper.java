package com.transporte.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.transporte.dto.VehicleDriverDTO;
import com.transporte.model.Driver;
import com.transporte.model.Vehicle;
import com.transporte.model.VehicleDriver;

@Mapper(componentModel = "spring")
public interface VehicleDriverMapper {

	VehicleDriverMapper INSTANCE = Mappers.getMapper(VehicleDriverMapper.class);
	
	@Mapping(source = "vehicleId", target = "vehicle.id")
	@Mapping(source = "driverId", target = "driver.id")
	VehicleDriver toEntity(VehicleDriverDTO dto);
	
	VehicleDriverDTO toDTO(VehicleDriver entity);
	
	default Vehicle mapVehicleIdToVehicle(Long vehicleId) {
		if(vehicleId == null) return null;
		Vehicle vehicle = new Vehicle();
		vehicle.setId(vehicleId);
		return vehicle;
	}
	
	default Driver mapTransportCompanyIdToCompany(Long driverId) {
		if(driverId == null) return null;
		Driver driver = new Driver();
		driver.setId(driverId);
		return driver;
	}
}
