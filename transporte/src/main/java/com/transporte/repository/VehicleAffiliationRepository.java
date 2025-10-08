package com.transporte.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.transporte.model.VehicleAffiliation;

@Repository
public interface VehicleAffiliationRepository extends JpaRepository<VehicleAffiliation, Long>{
	List<VehicleAffiliation> findByVehicleId(Long vehicleId);
	
	@Query("SELECT va FROM VehicleAffiliation va WHERE va.company.id = :transportCompanyId AND va.vehicle.id = :vehicleId AND "
			+ "va.disaffiliationDate IS NULL")
	Optional<VehicleAffiliation> findLinkByCompanyAndVehicle(Long transportCompanyId, Long vehicleId);
	
	@Query("SELECT va FROM VehicleAffiliation va WHERE va.company.id = :transportCompanyId AND va.vehicle.id = :vehicleId AND "
			+ "va.disaffiliationDate IS NOT NULL and va.affiliationDate IS NOT NULL")
	Optional<VehicleAffiliation> findUnlinkByCompanyAndVehicle(Long transportCompanyId, Long vehicleId);
	
	@Query("SELECT va FROM VehicleAffiliation va WHERE va.company.id = :transportCompanyId AND va.vehicle.id = :vehicleId ")
	Optional<VehicleAffiliation> findByCompanyAndVehicle(Long transportCompanyId, Long vehicleId); 
	
	@Query("SELECT va FROM VehicleAffiliation va WHERE va.company.id != :transportCompanyId AND va.vehicle.id = :vehicleId AND va.disaffiliationDate IS NULL")
	List<VehicleAffiliation> findByOtherCompaniesAndVehicle(Long transportCompanyId, Long vehicleId); 
	
	@Modifying
	@Query("UPDATE VehicleAffiliation u SET u.disaffiliationDate = :disaffiliationDate WHERE u.company.id = :transportCompanyId AND u.vehicle.id = :vehicleId ")
	void updateDisaffiliationDate(LocalDateTime disaffiliationDate, Long transportCompanyId, Long vehicleId);

}
