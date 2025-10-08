package com.transporte.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.transporte.model.TransportCompany;

@Repository
public interface TransportCompanyRepository extends JpaRepository<TransportCompany, Long>{
	
	Page<TransportCompany> findAll(Pageable pageable);
	
	@Query("SELECT CASE WHEN COUNT(va) > 0  THEN true ELSE false END "
			+ "FROM TransportCompany v "
			+ "LEFT JOIN v.affiliations va "
			+ "WHERE v.id = :id "
			+ "AND va.disaffiliationDate IS NULL")
	Boolean hasAffiliation(@Param("id") Long id);

}
