package com.transporte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.transporte.model.LegalRepresentative;

@Repository
public interface LegalRepresentativeRepository extends JpaRepository<LegalRepresentative, Long>{
	
}
