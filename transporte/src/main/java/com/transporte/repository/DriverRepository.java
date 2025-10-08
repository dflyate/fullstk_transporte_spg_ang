package com.transporte.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.transporte.model.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long>{

	@Query("SELECT v "
			+ "FROM Driver v "
			+ "JOIN v.vehicleLinks a "
			+ "WHERE a.vehicle.id = :id "
			+ "AND a.disaffiliationDate IS NULL")
	Page<Driver> findByVehicleId(Long id, Pageable pageable);
	
	@Query("SELECT v "
			+ "FROM Driver v "
			+ "WHERE v.company.id = :id ")
	Page<Driver> findByTransportCompanyId(Long id, Pageable pageable);
	
	@Query("SELECT CASE WHEN COUNT(va) > 0 THEN true ELSE false END "
			+ "FROM Driver v "
			+ "LEFT JOIN v.vehicleLinks va "
			+ "WHERE v.id = :id "
			+ "AND va.disaffiliationDate IS NULL")
	Boolean hasVehicles(@Param("id") Long id);
	
	@Query("SELECT v FROM Driver v WHERE v.company.id = :transportCompanyId AND v.id NOT IN (" +
		       "SELECT a.driver.id FROM VehicleDriver a)")
	Page<Driver> findAvailableDrivers(Pageable pageable, Long transportCompanyId);
	
	@Query("SELECT d "
			+ "FROM Driver d "
			+ "WHERE NOT EXISTS ( "
			+ "SELECT vc "
			+ "FROM VehicleDriver vc "
			+ "WHERE vc.driver = d "
			+ "AND vc.vehicle.id = :vehicleId "
			+ "AND vc.disaffiliationDate IS NULL) "
			+ "AND d.company.id = :transportCompanyId")
	Page<Driver> findAvailableDriversInAffiliations(Pageable pageable, Long vehicleId, Long transportCompanyId);
}
