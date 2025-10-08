package com.transporte.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.transporte.dto.LegalRepresentativeDTO;
import com.transporte.model.LegalRepresentative;

@Mapper(componentModel = "spring")
public interface LegalRepresentativeMapper {

	LegalRepresentativeMapper INSTANCE = Mappers.getMapper(LegalRepresentativeMapper.class);
	
	LegalRepresentative toEntity(LegalRepresentativeDTO dto);

	LegalRepresentativeDTO toDTO(LegalRepresentative entity);
}
