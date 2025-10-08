package com.transporte.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.transporte.dto.TransportCompanyDTO;
import com.transporte.model.LegalRepresentative;
import com.transporte.model.TransportCompany;
import com.transporte.model.User;

@Mapper(componentModel = "spring")
public interface TransportCompanyMapper {
	
	TransportCompanyMapper INSTANCE = Mappers.getMapper(TransportCompanyMapper.class);
	
	@Mapping(target = "affiliations", ignore = true)
	@Mapping(target = "legalRepresentative", ignore = true)
	@Mapping(target = "user", ignore = true)
	TransportCompany toEntity(TransportCompanyDTO dto);
	
	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "legalRepresentative.id", target = "legalRepresentativeId")
	TransportCompanyDTO toDTO(TransportCompany entity);
	
	default User mapUserIdToUser (Long id) {
		if(id == null) return null;
		User user = new User();
		user.setId(id);
		return user;
	}
	
	default LegalRepresentative mapLegalRepresentativeIdToLegalRepresentative (Long id) {
		if(id == null) return null;
		LegalRepresentative legalRepresentative = new LegalRepresentative();
		legalRepresentative.setId(id);
		return legalRepresentative;
	}

}
