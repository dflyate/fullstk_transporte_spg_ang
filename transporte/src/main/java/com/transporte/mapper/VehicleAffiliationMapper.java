package com.transporte.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.transporte.dto.VehicleAffiliationDTO;
import com.transporte.model.TransportCompany;
import com.transporte.model.Vehicle;
import com.transporte.model.VehicleAffiliation;

@Mapper(componentModel = "spring")
public interface VehicleAffiliationMapper {

	VehicleAffiliationMapper INSTANCE = Mappers.getMapper(VehicleAffiliationMapper.class);
	
	@Mapping(source = "vehicleId", target = "vehicle.id")
	@Mapping(source = "transportCompanyId", target = "company.id")
	VehicleAffiliation toEntity(VehicleAffiliationDTO dto);
	
	VehicleAffiliationDTO toDTO(VehicleAffiliation entity);
	
	default Vehicle mapVehicleIdToVehicle(Long vehicleId) {
		if(vehicleId == null) return null;
		Vehicle vehicle = new Vehicle();
		vehicle.setId(vehicleId);
		return vehicle;
	}
	
	default TransportCompany mapTransportCompanyIdToCompany(Long companyId) {
		if(companyId == null) return null;
		TransportCompany company = new TransportCompany();
		company.setId(companyId);
		return company;
	}
}
