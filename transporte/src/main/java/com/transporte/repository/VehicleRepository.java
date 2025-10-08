package com.transporte.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.transporte.model.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>{

	@Query("SELECT v "
			+ "FROM Vehicle v "
			+ "JOIN v.affiliations a "
			+ "WHERE a.company.id = :id "
			+ "AND a.disaffiliationDate IS NULL")
	Page<Vehicle> findByTransportCompanyId(Long id, Pageable pageable);
	
	@Query("SELECT v "
			+ "FROM Vehicle v "
			+ "WHERE NOT EXISTS ( "
			+ "SELECT vc "
			+ "FROM VehicleAffiliation vc "
			+ "WHERE vc.vehicle = v "
			+ "AND vc.disaffiliationDate IS NULL)")
	Page<Vehicle> findAvailableVehiclesInAffiliations(Pageable pageable);
	
	@Query("SELECT v FROM Vehicle v WHERE v.id NOT IN (" +
		       "SELECT a.vehicle.id FROM VehicleAffiliation a)")
	Page<Vehicle> findAvailableVehicles(Pageable pageable);
	
	@Query("SELECT CASE WHEN COUNT(va) > 0 OR COUNT(vd) > 0 THEN true ELSE false END "
			+ "FROM Vehicle v "
			+ "LEFT JOIN v.affiliations va "
			+ "LEFT JOIN v.driverLinks vd "
			+ "WHERE v.id = :id "
			+ "AND vd.disaffiliationDate IS NULL "
			+ "AND va.disaffiliationDate IS NULL")
	Boolean hasAffiliationOrDrivers(@Param("id") Long id);
}
