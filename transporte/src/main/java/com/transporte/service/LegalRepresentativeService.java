package com.transporte.service;

import com.transporte.dto.LegalRepresentativeDTO;
import com.transporte.model.LegalRepresentative;
import com.transporte.model.TransportCompany;

public interface LegalRepresentativeService {
	
	LegalRepresentativeDTO findById(Long id);
	LegalRepresentative create(LegalRepresentativeDTO dto);
	LegalRepresentative update(LegalRepresentativeDTO dto, TransportCompany saved);
	void delete(Long id);
}
